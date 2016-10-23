package logic;

import java.util.ArrayList;

public class SudokuBoard {

    protected final static int maxMisplacedNumber = 108;
	
	private Box[] board = new Box[9];
	private double misplaced;
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

	public void findMisplaced(){
		int misplacedSum = 0;

		for(int firstindex =0, secondindex = 3; secondindex <= 9; firstindex +=3, secondindex +=3){
			for(int row=0; row<3; row++){
				ArrayList<Integer> numbers = new ArrayList<Integer>();
				for(int box=firstindex; box<secondindex; box++){
					if(box==firstindex){
						numbers.addAll(board[box].getSingleRow(row));
					}else{
						ArrayList<Integer> toAdd = board[box].getSingleRow(row);
						for(Integer number : toAdd){
							if(!numbers.contains(number)){
								numbers.add(number);
							} else{
								misplacedSum += 1;
							}
						}
					}
				}
			}
		}

		for(int index =0; index < 3; index++){
			for(int col=0; col<3; col++){
				ArrayList<Integer> numbers = new ArrayList<Integer>();
				for(int box=index; box<9; box+=3){
					if(box==index){
						numbers.addAll(board[box].getSingleColumn(col));
					}
					else{
						ArrayList<Integer> toAdd = board[box].getSingleColumn(col);
						for(Integer number : toAdd){
							if(!numbers.contains(number)){
								numbers.add(number);
							} else{
								misplacedSum +=1 ;
							}
						}
					}
				}
			}
		}

		misplaced = misplacedSum;

	}

	public double correctnessPercentage(){
		findMisplaced();
		return 1-(getMisplacedSum() / maxMisplacedNumber);
	}

	public boolean goalFunction(){
		return correctnessPercentage() == 1.0;
	}
	
	public double getMisplacedSum(){
		return this.misplaced;
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
