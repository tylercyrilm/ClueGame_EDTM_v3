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
	private BoardCell [][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private String boardConfigFile;
	private String roomConfigFile;
	
	
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
		} catch(BadConfigFormatException e){
			e.printStackTrace();
		}
		
	}
	
	public BoardCell getCellAt(int row, int col){
		BoardCell testcell = new BoardCell();
		return testcell;
	}
	
	private void loadRoomConfig() throws BadConfigFormatException{
		try{
			FileReader input = new FileReader(roomConfigFile);
			Scanner in = new Scanner(input);
			while(in.hasNextLine()){
				String nextLine = in.nextLine();
				System.out.println(nextLine);
				String[] splitPieces = nextLine.split(", ");
				rooms.put(splitPieces[0].charAt(0), splitPieces[1]);
				//if (splitPieces[2] != "Card" || splitPieces[2] != "Other"){
					//throw new BadConfigFormatException("Incorrect room type");
				//}	
			}
			in.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	private void loadBoardConfig(){
		try{ 
		FileReader input = new FileReader(boardConfigFile);
		Scanner in = new Scanner(input);
		numRows = 0;
		while(in.hasNextLine()){
			String newRow = in.nextLine();
			String[] splitRows = newRow.split(",");
			if (numColumns == 0) numColumns = splitRows.length;
			numRows++;
			for(int i = 0; i < numColumns; i++){
				board[numRows][i] = new BoardCell();
				board[numRows][i].initial = splitRows[i];
				if (splitRows[i].length() == 2){
					switch (splitRows[0].charAt(1)){
					case 'D':
						board[numRows][i].direction = DoorDirection.DOWN;
						break;
					case 'U':
						board[numRows][i].direction = DoorDirection.UP;
						break;
					case 'L':
						board[numRows][i].direction = DoorDirection.LEFT;
						break;
					case 'R':
						board[numRows][i].direction = DoorDirection.RIGHT;
						break;
					}
				}
			}
		}
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	private void calcAdjacencies(){
		
	}
	
	private void calcTargets(BoardCell cell, int pathLength){
		
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
}
