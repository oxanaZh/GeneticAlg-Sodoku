package logic;

import java.util.ArrayList;

public class GeneticAlgorithm {

	private ArrayList<SudokuBoard> boards = new ArrayList<SudokuBoard>();
	private int generationCount =0;

	public void initPopulation() {
		for (int i = 0; i < 998; i++) {
			boards.add(new SudokuBoard());
		}
		int[][] wrongBoard = {{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
		boards.add(new SudokuBoard(wrongBoard));
		int[][] winBoard = {{5,3,4,6,7,2,1,9,8}, {6,7,8,1,9,5,3,4,2},{9,1,2,3,4,8,5,6,7},{8,5,9,4,2,6,7,1,3},{7,6,1,8,5,3,9,2,4},{4,2,3,7,9,1,8,5,6},{9,6,1,2,8,7,3,4,5},{5,3,7,4,1,9,2,8,6},{2,8,4,6,3,5,1,7,9}};
		boards.add(new SudokuBoard(winBoard));

	}

	
	public void fitnessFunction(){
		double fitnessSum = 0;
		for (SudokuBoard b: boards){
			b.calcBoardFitness();
			fitnessSum += b.getFitness();
		}
		
		System.out.printf("FitnessSum: %f\n",fitnessSum);
		double test = 0;
		double selectionChance;
		for (SudokuBoard b: boards){
			selectionChance=( b.getFitness()/ fitnessSum)*100;
			System.out.printf("getErrorSum: %f, getFitness: %f , Selection Chance: %.5f p\n",b.getErrorSum(),  b.getFitness(), selectionChance);
			test += selectionChance;
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
