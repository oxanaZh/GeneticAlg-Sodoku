package logic;

import java.util.ArrayList;

public class GeneticAlgorithm {

	private ArrayList<SudokuBoard> boards = new ArrayList<SudokuBoard>();
	private int generationCount =0;

	public void initPopulation() {
		for (int i = 0; i < 9; i++) {
			boards.add(new SudokuBoard());
		}
		int[][] wrongBoard = {{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
		boards.add(new SudokuBoard(wrongBoard));

	}

	
	public void fitnessFunction(){
		double fitnessSum = 0;
		for (SudokuBoard b: boards){
			b.calcBoardFitness();
			fitnessSum += b.getFitness();
		}
		
		System.out.printf("FitnessSum: %f\n",fitnessSum);
		double test = 0;
		double yourFit;
		for (SudokuBoard b: boards){
			yourFit= (b.getFitness()/ fitnessSum);
			System.out.printf("getErrorSum: %f, getFitness: %f / fitnessSum: %f = yourFit: %.12f\n",b.getErrorSum(),  b.getFitness(), fitnessSum, yourFit);
			test += yourFit;
		}
		
		System.out.printf(">>>%f",test);
		
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
