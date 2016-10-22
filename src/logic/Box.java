package logic;

import java.util.ArrayList;
import java.util.Random;

public class Box {
	
	private int[] fields = new int[9];
	
	public Box(){
		generateNewFields();
	}
	public Box(int[] numbers){
		copyFields(numbers);
	}

	public void copyFields(int[] numbers){
		for(int i=0; i < 9; i++){
			fields[i] = numbers[i];
		}
	}
	
	public int[] getFields(){
		return this.fields;
	}
	
	public int getField(int index){
		return fields[index];
	}
	
	public void setField(int index, int value){
		this.fields[index] = value;
	}
	
	public void generateNewFields(){
		Random randomizer = new Random();
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i=1; i<10;i++){
			numbers.add(i);
		}
		
		for(int i = 1; i<10; i++){
			int randomNumber = randomizer.nextInt(10-i);
			fields[i-1] = numbers.get(randomNumber);
			numbers.remove(randomNumber);
		}
		
	}
	
	public void printFields(){
		for(int i=0; i<9;i++){
			System.out.print(fields[i] + " | ");
			if(i==2 || i==5){
				System.out.println();
			}
		}
		
		System.out.println();
	}
	
	public void printSingleRow(int index){
		int secondIndex;
		int firstindex;
		switch (index){
			case 0:
				firstindex=0;
				secondIndex = 3;
				break;
			case 1:
				firstindex=3;
				secondIndex = 6;
				break;
			case 2:
				firstindex=6;
				secondIndex = 9;
				break;
			default:
				firstindex=0;
				secondIndex = 0;
		}
		System.out.print("|");
		for(int x=firstindex; x<secondIndex; x++){
			System.out.printf(" %d |", fields[x]);
		}
		
	}
	
	public ArrayList<Integer> getSingleRow(int index){
		int secondIndex;
		int firstindex;
		switch (index){
			case 0:
				firstindex=0;
				secondIndex = 3;
				break;
			case 1:
				firstindex=3;
				secondIndex = 6;
				break;
			case 2:
				firstindex=6;
				secondIndex = 9;
				break;
			default:
				firstindex=0;
				secondIndex = 0;
		}
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		for(int x=firstindex; x<secondIndex; x++){
			numbers.add(fields[x]);
		}
		return numbers;
	}
	
	public ArrayList<Integer> getSingleColumn(int index){
		ArrayList<Integer> numbers = new ArrayList<Integer>();		
		for(int x=index; x<9; x+=3){
			numbers.add(fields[x]);
		}
		return numbers;
	}
}
