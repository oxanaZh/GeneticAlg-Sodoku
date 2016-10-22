package logic;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

	private ArrayList<SudokuBoard> boards = new ArrayList<>();
	private ArrayList<SudokuBoard> matingpool = new ArrayList<>();
	private int generationSize = 98;
	private int generationCount =0;

	private double fitnessSum=0;

	public void initPopulation() {
		for (int i = 0; i < generationSize; i++) {
			boards.add(new SudokuBoard());
		}
		int[][] wrongBoard = {{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
		boards.add(new SudokuBoard(wrongBoard));
		int[][] winBoard = {{5,3,4,6,7,2,1,9,8}, {6,7,8,1,9,5,3,4,2},{9,1,2,3,4,8,5,6,7},{8,5,9,4,2,6,7,1,3},{7,6,1,8,5,3,9,2,4},{4,2,3,7,9,1,8,5,6},{9,6,1,2,8,7,3,4,5},{5,3,7,4,1,9,2,8,6},{2,8,4,6,3,5,1,7,9}};
		boards.add(new SudokuBoard(winBoard));

	}

	
	public void fitnessFunction(){
		double fitnessPercent;
		for (SudokuBoard b: boards){
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
		int popuation = boards.size();

		Random rand = new Random();
		int randIndex=0;
		int randChance=0;
		int boardChance=0;
		System.out.println(">>>>>>SELECTION\n");
		while (matingpool.size() < popuation/2){
			randIndex= rand.nextInt(popuation);
			SudokuBoard board = boards.get(randIndex);
			randChance= rand.nextInt(100);
			boardChance= (int)(board.getFitness()*100);
			if(randChance < boardChance){
				matingpool.add(board);
				System.out.printf("randindex: %d, getFitness: %f, randChance: %d, boardChance: %d\n", randIndex,board.getFitness(), randChance, boardChance);
			}
		}


	}



	public void printPopulation(){
		System.out.println("\n>>>Boards");
		int count = 1;
		for (SudokuBoard board: boards) {
			System.out.printf("Board %d:", count++);
			board.printBoard();
		}
		System.out.println();
	}
	
	
	
}
