package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	public int numRows;
	public int numColumns;
	private final int MAX_BOARD_SIZE = 50;
	private BoardCell [][] boardArray = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private String boardConfigFile;
	private String roomConfigFile;
	private String legendInitials = "";
	
	
	public void setConfigFiles(String layout, String legend){
		roomConfigFile = legend;
		boardConfigFile = layout;
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
		try{
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		} catch(BadConfigFormatException e){
			e.printStackTrace();
		}
		
	}
	
	public BoardCell getCellAt(int row, int col){
		BoardCell temp = boardArray[row][col];
		return temp;
	}
	
	public void loadRoomConfig() throws BadConfigFormatException{
		try{
			setConfigFiles("data/CR_ClueLayout.csv", "data/CR_ClueLegend.txt");
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
		String newRow = in.nextLine();
		while(in.hasNextLine()){
			String[] splitRows = newRow.split(",");
			
			if(numRows == 0){
				rowLength = splitRows.length;
			}
			
			if (numColumns == 0) numColumns = splitRows.length;
			
			if(splitRows.length != rowLength){
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
			newRow = in.nextLine();
		} //end of while loop
		
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void calcAdjacencies(){
		int x = boardArray[0].length;
		int y = boardArray.length;
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				Set<BoardCell> adj = new HashSet();
				if (boardArray[i][j].initial != 'W'){
					if (boardArray[i][j].isDoorway() == false){
			
					}
					else{
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
				else if(i - 1 >= 0){ 
					adj.add(boardArray[i-1][j]);
				}
				else if (i + 1 < x){
					adj.add(boardArray[i +1][j]);
				}
				else if (j - 1 >= 0){
					adj.add(boardArray[i][j-1]);
				}
				else if (j + 1 < y){
					adj.add(boardArray[i][j+1]);
				}
				adjMatrix.put(boardArray[i][j], adj);
			}
		}
		
	}
	
	public void calcTargets(int row, int col, int pathLength){
		
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
	
	public Set<BoardCell> getTargets(){ //TEST
		Set<BoardCell> test = new HashSet<BoardCell>();
		return test;
	}
}
