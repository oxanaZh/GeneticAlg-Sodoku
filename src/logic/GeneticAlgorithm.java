package logic;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

	Random rand = new Random();
	private ArrayList<SudokuBoard> parentBoards;
	private ArrayList<SudokuBoard> childBoards;
	private ArrayList<SudokuBoard> matingpool;
	private int generationSize;
	private int generationCount;
	private final double chanceToClone = 0.4;
	private final double chanceToMutate = 0.01;

	private FitnessFunctionType fitnessFunctionType = FitnessFunctionType.SIEVE;
	private SelectionFunctionType selectionFunctionType = SelectionFunctionType.ROULETTEWHEEL_V1;
	private RecombinationFunctionType recombinationFunctionType = RecombinationFunctionType.N_POINT_CROSSOVER;
	private MutationFunctionType mutationFunctionType = MutationFunctionType.INSERT_AND_SWAP;

	private double fitnessSum;

	private long startTime;


	private long endTime;
	private long timeout;
	private long searchtime;

	private ActivationsState activState = ActivationsState.INIT;

	SudokuBoard solution;


	public GeneticAlgorithm(){
		parentBoards = new ArrayList<>();
		childBoards = new ArrayList<>();
		matingpool = new ArrayList<>();
		generationSize = 10;
		generationCount =0;
		fitnessSum=0;
		solution = null;
		timeout = 300000;
		initPopulation();

	}

	public void initPopulation() {
		for (int i = 0; i < generationSize; i++) {
			parentBoards.add(new SudokuBoard());
		}
		/*int[][] wrongBoard = {{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
		parentBoards.add(new SudokuBoard(wrongBoard));
		int[][] winBoard = {{5,3,4,6,7,2,1,9,8}, {6,7,8,1,9,5,3,4,2},{9,1,2,3,4,8,5,6,7},{8,5,9,4,2,6,7,1,3},{7,6,1,8,5,3,9,2,4},{4,2,3,7,9,1,8,5,6},{9,6,1,2,8,7,3,4,5},{5,3,7,4,1,9,2,8,6},{2,8,4,6,3,5,1,7,9}};
		parentBoards.add(new SudokuBoard(winBoard));*/

	}

	public void startSearch(){
		activState = ActivationsState.START;
		searchtime = 0;
		solution = testGoal();
		while(activState == ActivationsState.START && solution ==null && searchtime <= timeout){
			startTime = System.currentTimeMillis();
			//ga.printPopulation();
			fitnessFunction();
			selectionFunction();
			createNewGeneration();
			mutationFunction();
			parentBoards = childBoards;
			childBoards.clear();
			generationCount++;
			solution = testGoal();
			searchtime += System.currentTimeMillis()-startTime;
		}
		activState = ActivationsState.STOP;

	}


	public SudokuBoard testGoal(){
		for(SudokuBoard board : parentBoards){
			if(board.goalFunction()){
				System.out.println("Solution found!!");
				return board;
			}
		}
		return null;
	}

	public void fitnessFunction(){
		switch(fitnessFunctionType){
			case SIEVE:
				fitnessFunctionSieve();
				break;
			case STANDART:
				fitnessFunctionStandart();
				break;
			default:
				fitnessFunctionSieve();
				break;
		}
	}
	public void fitnessFunctionSieve(){
		double fitnessPercent;
		for (SudokuBoard b: parentBoards){
			b.findMisplaced();
			fitnessPercent = b.correctnessPercentage();
			if(fitnessPercent < 0.8){
				fitnessPercent *= 1+fitnessPercent/4;
			}
			if(fitnessPercent < 0.6){
				fitnessPercent *= 1-fitnessPercent*fitnessPercent;
			}
			if(fitnessPercent < 0.4){
				fitnessPercent *= 1-fitnessPercent*fitnessPercent;
			}
			if(fitnessPercent < 0.2){
				fitnessPercent *= 1-fitnessPercent*fitnessPercent;
			}
			b.setFitness(fitnessPercent);
			System.out.printf("getMisplacedSum: %f,correctness: %f, getFitness: %f\n",b.getMisplacedSum(), b.correctnessPercentage(), b.getFitness());
			fitnessSum+=b.getFitness();
		}
		System.out.printf("FitnessSum: %f, fitnessavarage: %.5f\n",fitnessSum, fitnessSum/(double)generationSize);

	}
	public void fitnessFunctionStandart(){
		double correctnessPercentageSum = calcCorrectnessPercentageSum();
		System.out.printf("correctnessPercentageSum: %f", correctnessPercentageSum );
		for(SudokuBoard board : parentBoards){
			double fitness = board.correctnessPercentage();
			board.setFitness(fitness);
			System.out.printf("getMisplacedSum: %f,correctness: %f, getFitness: %f\n",board.getMisplacedSum(), board.correctnessPercentage(), board.getFitness());
			fitnessSum+=board.getFitness();
		}
	}

	public double calcCorrectnessPercentageSum(){
		double correctnessPercentageSum = 0.0;
		for(SudokuBoard board : parentBoards){
			correctnessPercentageSum += board.correctnessPercentage();
		}
		return correctnessPercentageSum;
	}
	public void selectionFunction(){
		switch (selectionFunctionType){
			case ROULETTEWHEEL_V1:
				selectionFunctionRW1();
				break;
			default:
				selectionFunctionRW1();
				break;
		}
	}
	public void selectionFunctionRW1(){
		int popuation = parentBoards.size();

		int randIndex=0;
		double randChance=0;
		double boardChance=0;
		System.out.println(">>>>>>SELECTION\n");
		while (matingpool.size() < popuation/2){
			randIndex= rand.nextInt(popuation);
			SudokuBoard board = parentBoards.get(randIndex);
			randChance= rand.nextDouble();
			boardChance= board.getFitness();
			if(randChance < boardChance){
				matingpool.add(board);
				System.out.printf("randindex: %d, getFitness: %f, randChance: %f, boardChance: %f\n", randIndex,board.getFitness(), randChance, boardChance);
			}
		}


	}

	public void createNewGeneration(){
		System.out.println(">>>>>CREATE NEW GENERATION <<<<<<<<");
		SudokuBoard parentX=null;
		SudokuBoard parentY=null;
		SudokuBoard child = null;
		while(childBoards.size()<= generationSize){
			parentX = chooseParent();
			System.out.printf("choose parentX: fitness: %f, correctness %f\n", parentX.getFitness(), parentX.correctnessPercentage());
			while (toClone()){
				childBoards.add(parentX);
				System.out.println("clone parentX to childBoards");
				parentX = chooseParent();
				System.out.printf("choose parentY: fitness: %f, correctness %f\n", parentX.getFitness(), parentX.correctnessPercentage());
			}
			parentY = chooseParent();
			System.out.printf("choose parentY: fitness: %f, correctness %f\n", parentX.getFitness(), parentX.correctnessPercentage());
			while (toClone()){
				childBoards.add(parentY);
				System.out.println("clone parentY to childBoards");
				parentY = chooseParent();
				System.out.printf("choose parentY: fitness: %f, correctness %f\n", parentX.getFitness(), parentX.correctnessPercentage());
			}
			child= recombinationFunction(parentX, parentY);
			System.out.printf(" = new child with correctness %f:\n", child.correctnessPercentage());
			//child.printBoard();
			if(child!=null){
				childBoards.add(child);
			}
		}

	}
	public SudokuBoard recombinationFunction(SudokuBoard parentX, SudokuBoard parentY){
		SudokuBoard child = null;
		switch (recombinationFunctionType){
			case ONE_POINT_CROSSOVER:
				child = crossoverFunctionOnePoint(parentX, parentY);
				break;
			default:
				child = crossoverFunctionOnePoint(parentX, parentY);
				break;
		}
		return child;
	}

	public SudokuBoard crossoverFunctionOnePoint(SudokuBoard parentX, SudokuBoard parentY){
		System.out.println(">>>>>CROSSOVER:");
		System.out.printf("crossover: parentX: fitness: %f + parentY: fitness: %f +\n", parentX.getFitness(), parentY.getFitness());
		System.out.println("board X:");
		//parentX.printBoard();
		System.out.println("board Y:");
		//parentY.printBoard();
		SudokuBoard child=null;

		Box[] boxes = new Box[9];
		int cuteIndex = rand.nextInt(7)+1;
		System.out.printf("cute index: %d", cuteIndex);
		for(int i =0; i<= cuteIndex; i++){
			boxes[i] = parentX.getBox(i);
		}
		for(int i =cuteIndex+1; i<9; i++){
			boxes[i] = parentY.getBox(i);
		}
		child = new SudokuBoard(boxes);
		return child;
	}

	public SudokuBoard chooseParent(){return matingpool.get(rand.nextInt(matingpool.size()));}

	public boolean toClone(){return rand.nextDouble()< chanceToClone;}

	public void mutationFunction(){
		switch (mutationFunctionType){
			case INSERT_AND_SWAP:
				mutationInsertAndSwap();
				break;
			default:
				mutationInsertAndSwap();
				break;
		}
	}

	public void mutationInsertAndSwap(){
		System.out.println(">>>>>MUTATION:");
		for(SudokuBoard board : childBoards){
			//System.out.printf("to mutate board with correctness: %f \n", board.correctnessPercentage());
			//board.printBoard();
			int mutationCount=0;
			for(int i =0; i<9; i++){
				if(toMutate()){
					mutateBox(board.getBox(i));
					mutationCount++;
				}
			}
			//System.out.printf("after %d mutations correctness: %f \n",mutationCount, board.correctnessPercentage());
			//if(mutationCount >0){board.printBoard();}
		}
	}

	public boolean toMutate(){
		return (rand.nextDouble() < chanceToMutate);
	}

	public void mutateBox(Box box){
		//System.out.println("to mutate Box:");
		//box.printFields();
		int indexToMutate = rand.nextInt(9);
		int tempValue = box.getField(indexToMutate);
		int mutationValue = rand.nextInt(9)+1;
		int exchangeIndex = box.getFirstIndexOf(mutationValue);
		if(exchangeIndex > 0){
			box.setField(exchangeIndex,tempValue);
		}

		box.setField(indexToMutate, mutationValue );
		//System.out.printf("after mutations on index %d to value %d\n",indexToMutate, mutationValue);
		//box.printFields();

	}

	public void printPopulation(){
		System.out.println("\n>>>Boards");
		int count = 1;
		for (SudokuBoard board: parentBoards) {
			System.out.printf("Board %d:", count++);
			board.printBoard();
		}
		System.out.println();
	}

	public int getGenerationSize() {
		return generationSize;
	}

	public void setGenerationSize(int generationSize) {
		this.generationSize = generationSize;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public void setGenerationCount(int generationCount) {
		this.generationCount = generationCount;
	}

	public double getFitnessSum() {
		return fitnessSum;
	}

	public void setFitnessSum(double fitnessSum) {
		this.fitnessSum = fitnessSum;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getSearchtime() {
		return searchtime;
	}

	public void setSearchtime(long searchtime) {
		this.searchtime = searchtime;
	}

	public ActivationsState getActivState() {
		return activState;
	}

	public void setActivState(ActivationsState activState) {
		this.activState = activState;
	}

	public SudokuBoard getSolution() {
		return solution;
	}

	public void setSolution(SudokuBoard solution) {
		this.solution = solution;
	}



}
