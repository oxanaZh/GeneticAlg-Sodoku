package logic;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

	Random rand = new Random();
	private ArrayList<SudokuBoard> parentBoards = new ArrayList<>();
	private ArrayList<SudokuBoard> childBoards = new ArrayList<>();
	private ArrayList<SudokuBoard> matingpool = new ArrayList<>();
	private int generationSize = 10;
	private int generationCount =0;
	private final double chanceToClone = 0.4;
	private final double chanceToMutate = 0.01;

	private double fitnessSum=0;

	public void initPopulation() {
		for (int i = 0; i < generationSize; i++) {
			parentBoards.add(new SudokuBoard());
		}
		/*int[][] wrongBoard = {{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
		parentBoards.add(new SudokuBoard(wrongBoard));
		int[][] winBoard = {{5,3,4,6,7,2,1,9,8}, {6,7,8,1,9,5,3,4,2},{9,1,2,3,4,8,5,6,7},{8,5,9,4,2,6,7,1,3},{7,6,1,8,5,3,9,2,4},{4,2,3,7,9,1,8,5,6},{9,6,1,2,8,7,3,4,5},{5,3,7,4,1,9,2,8,6},{2,8,4,6,3,5,1,7,9}};
		parentBoards.add(new SudokuBoard(winBoard));*/

	}

	
	public void fitnessFunction(){
		double fitnessPercent;
		for (SudokuBoard b: parentBoards){
			b.findMisplaced();
			fitnessPercent = b.goalFunction();
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
			System.out.printf("getMisplacedSum: %f,goaFunction: %f, getFitness: %f\n",b.getMisplacedSum(), b.goalFunction(), b.getFitness());
			fitnessSum+=b.getFitness();
		}
		System.out.printf("FitnessSum: %f, fitnessavarage: %.5f\n",fitnessSum, fitnessSum/(double)generationSize);

	}
	public void selectionFunctionRW1(){
		int popuation = parentBoards.size();

		int randIndex=0;
		int randChance=0;
		int boardChance=0;
		System.out.println(">>>>>>SELECTION\n");
		while (matingpool.size() < popuation/2){
			randIndex= rand.nextInt(popuation);
			SudokuBoard board = parentBoards.get(randIndex);
			randChance= rand.nextInt(100);
			boardChance= (int)(board.getFitness()*100);
			if(randChance < boardChance){
				matingpool.add(board);
				System.out.printf("randindex: %d, getFitness: %f, randChance: %d, boardChance: %d\n", randIndex,board.getFitness(), randChance, boardChance);
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
			System.out.printf("choose parentX: fitness: %f\n", parentX.getFitness());
			while (toClone()){
				childBoards.add(parentX);
				System.out.println("clone parentX to childBoards");
				parentX = chooseParent();
				System.out.printf("choose parentX: fitness: %f\n", parentX.getFitness());
			}
			parentY = chooseParent();
			System.out.printf("choose parentY: fitness: %f\n", parentX.getFitness());
			while (toClone()){
				childBoards.add(parentY);
				System.out.println("clone parentY to childBoards");
				parentY = chooseParent();
				System.out.printf("choose parentY: fitness: %f\n", parentX.getFitness());
			}
			child= crossoverFunctionOnePoint(parentX, parentY);
			System.out.println(" = new child:");
			child.printBoard();
			if(child!=null){
				childBoards.add(child);
			}
		}

	}

	public SudokuBoard crossoverFunctionOnePoint(SudokuBoard parentX, SudokuBoard parentY){
		System.out.println(">>>>>CROSSOVER:");
		System.out.printf("crossover: parentX: fitness: %f + parentY: fitness: %f +\n", parentX.getFitness(), parentY.getFitness());
		System.out.println("board X:");
		parentX.printBoard();
		System.out.println("board Y:");
		parentY.printBoard();
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
		for(SudokuBoard board : childBoards){

		}
	}

	public boolean toMutate(){
		return (rand.nextDouble() < chanceToMutate);
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
	
	
	
}
