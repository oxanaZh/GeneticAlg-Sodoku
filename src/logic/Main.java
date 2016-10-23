package logic;


public class Main {
	
	public static void main(String[] args){

		GeneticAlgorithm ga = new GeneticAlgorithm();
		
		ga.startSearch();
		SudokuBoard solution = ga.getSolution();
		if(solution != null){
			System.out.println("Result:");
			solution.printBoard();

		} else {
			System.out.println("solution not found");
		}

		
	}

}
