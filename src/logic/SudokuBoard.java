package logic;

import java.util.ArrayList;

public class SudokuBoard {

    private final int maxErrorNumber = 324;
	
	private Box[] board = new Box[9];
	private double errors;
	private double fitness;
	private int generation;

	
	public SudokuBoard(){
		generateNewBoard();
	}
	public SudokuBoard(Box[] boxes) {copyBoxes(boxes); }
	public SudokuBoard(int[][] fields){copyFields(fields);}
	
	public void generateNewBoard(){
		for(int i=0; i<9; i++){
			board[i] = new Box();
		}
	}

	public void copyBoxes(Box[] boxes){
		for(int i=0; i<9; i++){
			board[i] = new Box(boxes[i].getFields());
		}
	}
	public void copyFields(int[][] fields){
		for(int i=0; i<9; i++){
			board[i] = new Box(fields[i]);
		}
	}
	
	public Box getBox(int index){
		return this.board[index];
	}
	
	public void setBox(int index, Box box){
		this.board[index] = new Box(box.getFields());
	}
	
	public void printBoard(){
		System.out.println("\n--------------------------------------");
		for(int firstindex = 0, secondindex=3; secondindex <= 9;secondindex +=3, firstindex +=3){
			for(int row= 0; row < 3; row ++){
				for(int box=firstindex; box<secondindex; box++){
					board[box].printSingleRow(row);
				}
				System.out.println("\n+---+---+---++---+---+---++---+---+---+");
			}
			System.out.println("+---+---+---++---+---+---++---+---+---+");
		}

		System.out.println("........................................");
	}
	
	public void testErrors(){
		int errorSum = 0;
		int errorCounter = 0;
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		for(int row=0; row<3; row++){
			for(int box=0; box<3; box++){
				if(box==0){
					numbers.addAll(board[box].getSingleRow(row));
				}
				else{
					ArrayList<Integer> toAdd = board[box].getSingleRow(row);

					for(int x=0;x<toAdd.size();x++){
						if(!numbers.contains(toAdd.get(x))){
							numbers.add(toAdd.get(x));
						}
					}
	
				}
			}
			errorCounter = 9 - numbers.size();
			numbers.clear();
			errorSum += errorCounter;
			errorCounter = 0;
		}
		
		for(int row=0; row<3; row++){
			for(int box=3; box<6; box++){
				if(box==0){
					numbers.addAll(board[box].getSingleRow(row));
				}
				else{
					ArrayList<Integer> toAdd = board[box].getSingleRow(row);
					for(int x=0;x<toAdd.size();x++){
						if(!numbers.contains(toAdd.get(x))){
							numbers.add(toAdd.get(x));
						}
					}
	
				}
			}
			errorCounter = 9 - numbers.size();
			numbers.clear();
			errorSum += errorCounter;
			errorCounter = 0;
			}
			
		
		for(int row=0; row<3; row++){
			for(int box=6; box<9; box++){
				if(box==0){
					numbers.addAll(board[box].getSingleRow(row));
				}
				else{
					ArrayList<Integer> toAdd = board[box].getSingleRow(row);
					for(int x=0;x<toAdd.size();x++){
						if(!numbers.contains(toAdd.get(x))){
							numbers.add(toAdd.get(x));
						}
					}
	
				}
			}
			errorCounter = 9 - numbers.size();
			numbers.clear();
			errorSum += errorCounter;
			errorCounter = 0;
		}
		
		//COLUMN ERRORS
		for(int row=0; row<3; row++){
			for(int box=0; box<9; box+=3){
				if(numbers.isEmpty()){
					numbers.addAll(board[box].getSingleColumn(row));
				}
				else{
					ArrayList<Integer> toAdd = board[box].getSingleColumn(row);
					for(int x=0;x<toAdd.size();x++){
						if(!numbers.contains(toAdd.get(x))){
							numbers.add(toAdd.get(x));
						}
					}
	
				}
			}
			errorCounter = 9 - numbers.size();
			numbers.clear();
			errorSum += errorCounter;
			errorCounter = 0;
		}
		
		for(int row=0; row<3; row++){
			for(int box=1; box<9; box+=3){
				if(numbers.isEmpty()){
					numbers.addAll(board[box].getSingleColumn(row));
				}
				else{
					ArrayList<Integer> toAdd = board[box].getSingleColumn(row);
					for(int x=0;x<toAdd.size();x++){
						if(!numbers.contains(toAdd.get(x))){
							numbers.add(toAdd.get(x));
						}
					}
	
				}
			}
			errorCounter = 9 - numbers.size();
			numbers.clear();
			errorSum += errorCounter;
			errorCounter = 0;
		}
		
		for(int row=0; row<3; row++){
			for(int box=2; box<9; box+=3){
				if(numbers.isEmpty()){
					numbers.addAll(board[box].getSingleColumn(row));
				}
				else{
					ArrayList<Integer> toAdd = board[box].getSingleColumn(row);
					for(int x=0;x<toAdd.size();x++){
						if(!numbers.contains(toAdd.get(x))){
							numbers.add(toAdd.get(x));
						}
					}
	
				}
			}
			errorCounter = 9 - numbers.size();
			numbers.clear();
			errorSum += errorCounter;
			errorCounter = 0;
		}
		
		
		
		
		
		//System.out.println("BOARD ERRORS>>> " + errorSum);
		
		errors = errorSum;
	}

	public void calcBoardFitness(){
        testErrors();
        setFitness(1-(this.errors/maxErrorNumber));
    }
	
	public double getErrorSum(){
		return this.errors;
	}
	
	public double getFitness(){
		return this.fitness;
	}
	
	public void setFitness(double f){
		this.fitness = f;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

}
