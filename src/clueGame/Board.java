package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.sun.prism.paint.Color;

public class Board {
	public int numRows = 0;
	public int numColumns = 0;
	private final int MAX_BOARD_SIZE = 50;
	private BoardCell [][] boardArray = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> tempTargets = new HashSet<BoardCell>();
	public Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private String boardConfigFile;
	private String roomConfigFile;
	private String weaponConfigFile;
	private String playerConfigFile;
	private String legendInitials = "";
	public HumanPlayer player;
	public ArrayList<ComputerPlayer> comp = new ArrayList<ComputerPlayer>();
	public ArrayList<ArrayList<Card>> deck = new ArrayList<ArrayList<Card>>();
	
	//This function has had "people" and "weapons" added, you'll need to update this call in previous tests
	public void setConfigFiles(String layout, String legend){
		roomConfigFile = legend;
		boardConfigFile = layout;
	}
	public void setWPConfigFiles(String people, String weapons) {
		playerConfigFile = people;
		weaponConfigFile = weapons;
	}
	
	
	// variable used for singleton pattern
		private static Board theInstance = new Board();
		// ctor is private to ensure only one can be created
		private Board() {}
		// this method returns the only Board
		public static Board getInstance() {
			return theInstance;
		}
	
	public void initialize(){
		loadConfigFiles();
		calcAdjacencies();
	}
	
	public BoardCell getCellAt(int row, int col){
		BoardCell temp = boardArray[row][col];
		return temp;
	}
	
	public void loadConfigFiles() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayerConfig();
			loadWeaponConfig();
		}
		catch (BadConfigFormatException e) {
			
		}
	}
	
	public void loadRoomConfig() throws BadConfigFormatException{
		try{
			FileReader input = new FileReader(roomConfigFile);
			Scanner in = new Scanner(input);
			while(in.hasNextLine()){
				String nextLine = in.nextLine();
				String[] splitPieces = nextLine.split(", ");
				rooms.put(splitPieces[0].charAt(0), splitPieces[1]);
				legendInitials += splitPieces[0].charAt(0);
				if (!splitPieces[2].equalsIgnoreCase("Card") && !splitPieces[2].equalsIgnoreCase("Other")){
					throw new BadConfigFormatException("Incorrect room type");
				}	
			}
			in.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		try{ 
			for (int i = 0; i < MAX_BOARD_SIZE; i++){
				for (int j = 0; j < MAX_BOARD_SIZE; j++){
					boardArray[i][j] = new BoardCell();
					boardArray[i][j].setLocation(i,j);
				}
			}
		FileReader input = new FileReader(boardConfigFile);
		Scanner in = new Scanner(input);
		int rowLength = 0; //ends up being the number of columns
		numRows = 0;
		
		while(in.hasNextLine()){
			String newRow = in.nextLine();
			String[] splitRows = newRow.split(",");
			
			if(numRows == 0){
				rowLength = splitRows.length;
			}
			//CHECK THIS STATEMENT
			if (numColumns == 0) numColumns = splitRows.length;
			
			if(splitRows.length != numColumns){
				throw new BadConfigFormatException("Incorrect number of Columns");
			}
			
			for(int i = 0; i < numColumns; i++){
				String label = splitRows[i];
				boardArray[numRows][i] = new BoardCell();
				boardArray[numRows][i].initial = label.charAt(0);
				
				if(legendInitials.indexOf(boardArray[numRows][i].initial) < 0){
					throw new BadConfigFormatException("Invalid room initial");
				}
				
				if(label.length() > 1){
					Character dir = label.charAt(1);
					switch (dir){
					case 'D':
						boardArray[numRows][i].direction = DoorDirection.DOWN;
						
						break;
					case 'U':
						boardArray[numRows][i].direction = DoorDirection.UP;
						break;
					case 'L':
						boardArray[numRows][i].direction = DoorDirection.LEFT;
						break;
					case 'R':
						boardArray[numRows][i].direction = DoorDirection.RIGHT;
						break;
					}
					
				}	
				
			}//end of for loop
			
			numRows++;

		} //end of while loop
		BoardCell [][] tempArray = new BoardCell[numRows][rowLength];
		for(int i = 0; i < numRows; i++){
			for (int j = 0; j < rowLength; j++){
				tempArray[i][j] = new BoardCell();
				tempArray[i][j] = boardArray[i][j];
				boardArray[i][j].setLocation(i, j);
			}
		}
		boardArray = tempArray;
		
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException {
		try{
			FileReader input = new FileReader(playerConfigFile);
			Scanner in = new Scanner(input);
			while(in.hasNextLine()){
				String nextLine = in.nextLine();
				String[] splitPieces = nextLine.split(", ");
				//check if computer or player card
				if(splitPieces[4].equals("P")) {
					player = new HumanPlayer();
					player.setName(splitPieces[0]);
					System.out.println(splitPieces[1]);
					player.setColor(convertColor(splitPieces[1]));
					player.setRow(Integer.parseInt(splitPieces[2]));
					player.setColumn(Integer.parseInt(splitPieces[3]));
				}
				else if(splitPieces[4].equals("C")) {
					ComputerPlayer C1 = new ComputerPlayer();
					C1.setName(splitPieces[0]);
					System.out.println(splitPieces[1]);
					C1.setColor(convertColor(splitPieces[1]));
					C1.setRow(Integer.parseInt(splitPieces[2]));
					C1.setColumn(Integer.parseInt(splitPieces[3]));
					comp.add(C1);
				}
				else {
					throw new BadConfigFormatException("The player configuration file is not in the correct format. Correct it and load again.");
				}
			}
			in.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void loadWeaponConfig() throws BadConfigFormatException {
		//TODO
	}
	
	public void calcAdjacencies(){
		int x = boardArray[0].length;
		int y = boardArray.length;
		for (int i = 0; i < y; i++){
			for (int j = 0; j < x; j++){
				Set<BoardCell> adj = new HashSet();
				if (boardArray[i][j].initial != 'W'){ 
					if (boardArray[i][j].isDoorway() == false){
						//in a room and not in a doorway
						//no adjacencies added
					}
					else{
						//in a doorway
						switch(boardArray[i][j].direction){
						case UP:
							adj.add(boardArray[i-1][j]);
							break;
						case DOWN:
							adj.add(boardArray[i+1][j]);
							break;
						case LEFT:
							adj.add(boardArray[i][j-1]);
							break;
						case RIGHT:
							adj.add(boardArray[i][j+1]);
							break;
						case NONE:
							break;
						}
					}
				}
				else{
				//outside of a room and not in a doorway
				if(i - 1 >= 0){ 
					if((boardArray[i-1][j].isDoorway() && boardArray[i-1][j].direction == DoorDirection.DOWN)|| boardArray[i-1][j].initial == 'W'){
						adj.add(boardArray[i-1][j]);
					}
				}
				if (i + 1 < y){
					if((boardArray[i+1][j].isDoorway() && boardArray[i+1][j].direction == DoorDirection.UP)|| boardArray[i+1][j].initial == 'W'){
						adj.add(boardArray[i +1][j]);
					}
				}
				if (j - 1 >= 0){
					if((boardArray[i][j-1].isDoorway() && boardArray[i][j-1].direction == DoorDirection.RIGHT)|| boardArray[i][j-1].initial == 'W'){
						adj.add(boardArray[i][j-1]);
					}
				}
				if (j + 1 < x){
					if((boardArray[i][j+1].isDoorway() && boardArray[i][j+1].direction == DoorDirection.LEFT)|| boardArray[i][j+1].initial == 'W'){
						adj.add(boardArray[i][j+1]);
					}
				}
				
			}
			adjMatrix.put(boardArray[i][j], adj);
			}
		}
	}
	
	public void calcTargets(int row, int col, int pathLength){
		Set<BoardCell> adjCells = getAdjList(row, col);
		
		visited.add(boardArray[row][col]);

		for (BoardCell adjCell : adjCells) {
			if(!visited.contains(adjCell)){
				
				if (pathLength == 1 || adjCell.isDoorway()){
					tempTargets.add(adjCell);
				}
				else{
					row = adjCell.row;
					col = adjCell.column;
					calcTargets(row, col, pathLength - 1);
				}
			}
			visited.remove(boardArray[row][col]);
		}
	}
	
	public void selectAnswer() {
		//TODO
	}
	
	public Card handleSuggestion() {
		Card testCard = new Card();
		//TODO
		return testCard;
	}
	
	public boolean checkAccusation(Solution accusation) {
		//TODO
		return false;
	}
	
	public Map<Character, String> getLegend(){
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public Set<BoardCell> getAdjList(int row, int col){
		return adjMatrix.get(theInstance.getCellAt(row,col));
	}
	
	public Set<BoardCell> getTargets(){
		targets.clear();
		for(BoardCell tar : tempTargets){
			targets.add(tar);
		}
		
		tempTargets.clear();
		return targets;
	}
	
	public Color convertColor(String strColor) {
		Color color;
		System.out.println("Inside: " + strColor );
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
			System.out.println("Color:" + color);
		} catch (Exception e) {
			System.out.println("No valid color?");
			color = null;
		}
		return color;
	}
}
