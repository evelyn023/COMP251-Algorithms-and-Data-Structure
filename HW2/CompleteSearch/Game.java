import java.util.*;
import java.lang.*;
import java.io.*;


public class Game {
	
	Board sudoku;
	
	public class Cell{
		private int row = 0;
		private int column = 0;
		
		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
	}
	
	public class Region{
		private Cell[] matrix;
		private int num_cells;
		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}
		public Cell[] getCells() {
			return matrix;
		}
		public void setCell(int pos, Cell element){
			matrix[pos] = element;
		}
		
	}
	
	public class Board{
		private int[][] board_values;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;
		
		public Board(int num_rows,int num_columns, int num_regions){
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}
		
		public int[][] getValues(){
			return board_values;
		}
		public int getValue(int row, int column) {
			return board_values[row][column];
		}
		public Region getRegion(int index) {
			return board_regions[index];
		}
		public Region[] getRegions(){
			return board_regions;
		}
		public void setValue(int row, int column, int value){
			board_values[row][column] = value;
		}
		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}	
		public void setValues(int[][] values) {
			board_values = values;
		}

	}
	
	private boolean valid(Region r, Cell c, int i) {
		boolean legal = true;
		// Check adjcant values
		if (c.row-1 > -1 && c.row-1 < sudoku.num_rows && c.column-1 > -1 && c.column-1 < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row-1, c.column-1)) legal = false;
		}
		if (c.row-1 > -1 && c.row-1 < sudoku.num_rows && c.column > -1 && c.column < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row-1, c.column)) legal = false;
		}
		if (c.row-1 > -1 && c.row-1 < sudoku.num_rows && c.column+1 > -1 && c.column+1 < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row-1, c.column+1)) legal = false;
		}
		if (c.row > -1 && c.row < sudoku.num_rows && c.column-1 > -1 && c.column-1 < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row, c.column-1)) legal = false;
		}
		if (c.row > -1 && c.row < sudoku.num_rows && c.column+1 > -1 && c.column+1 < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row, c.column+1)) legal = false;
		}
		if (c.row+1 > -1 && c.row+1 < sudoku.num_rows && c.column-1 > -1 && c.column-1 < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row+1, c.column-1)) legal = false;
		}
		if (c.row+1 > -1 && c.row+1 < sudoku.num_rows && c.column > -1 && c.column < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row+1, c.column)) legal = false;
		}
		if (c.row+1 > -1 && c.row+1 < sudoku.num_rows && c.column+1 > -1 && c.column+1 < sudoku.num_columns) {
			if (i == sudoku.getValue(c.row+1, c.column+1)) legal = false;
		}
		// Check whether have duplicates in the same region
		for (int j=0; j<r.num_cells;j++) {
			if (sudoku.board_values[r.matrix[j].row][r.matrix[j].column] == i) legal = false;
		}
		return legal;
	}
	
	private boolean fillBoard(int regionIndex, int cellIndex) {
		if (regionIndex > sudoku.num_regions-1) return true;
		
		Region r = sudoku.board_regions[regionIndex];
		// Check if we have filled all cells of current region
		if (cellIndex > r.num_cells-1) {
			regionIndex += 1;
			cellIndex = 0;
		}
		Cell c = r.matrix[cellIndex];
		
		// Check if the current cell already contains a value
		if (sudoku.getValue(c.row, c.column) != -1) {
			return fillBoard(regionIndex, cellIndex+1);
		}

		for (int i=1; i<=r.num_cells; i++) {
			if (valid(r,c,i)) {
				// Assigning i(1-num_cells) in the current cell and assuming 
				// our assigned i in the cell is correct 
				sudoku.setValue(c.row, c.column, i);
			    //  Checking for next possibility with next cell
				if (fillBoard(regionIndex, cellIndex+1)) { 
					return true;		
				}
			}
			// Removing the assigned i since our assumption was wrong , 
			// and go for next assumption with next i
			sudoku.setValue(c.row, c.column, -1);
		}
		return false;
	}
	
	public int[][] solver() {
		//To Do => Please start coding your solution here
		this.fillBoard(0, 0);
		return sudoku.getValues();
	}

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		//Reading the board
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				}else {
					try {
						board[i][j] = Integer.valueOf(value);
					}catch(Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}	
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
	    game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i=0; i< regions;i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j=0; j< num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1)-1,Integer.valueOf(value2)-1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i=0; i<answer.length;i++) {
			for (int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j<answer[0].length -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	


}


