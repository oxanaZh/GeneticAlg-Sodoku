package logic;

import java.util.ArrayList;


public class Main {
	
	public static void main(String[] args){

		GeneticAlgorithm ga = new GeneticAlgorithm();
		
		ga.initPopulation();
		ga.printPopulation();

		ga.fitnessFunction();
		
	}

}
