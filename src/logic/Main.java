package logic;


public class Main {
	
	public static void main(String[] args){

		GeneticAlgorithm ga = new GeneticAlgorithm();
		
		ga.initPopulation();
		//ga.printPopulation();

		ga.fitnessFunction();
		ga.selectionFunctionRW1();
		ga.createNewGeneration();
		
	}

}
