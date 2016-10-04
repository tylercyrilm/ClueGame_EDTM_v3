package clueGame;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import experiment.BoardCell;

public class Board {
	public int numRows;
	public int numColumns;
	private final int MAX_BOARD_SIZE = 50;
	private BoardCell [][] board;
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public void readConfigFiles(){
		//FIXME later
	}
	
	public void setConfigFiles(String legend, String layout){
		boardConfigFile = layout;
		roomConfigFile = legend;
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
		
	}
	
	public BoardCell getCellAt(int row, int col){
		BoardCell testcell = new BoardCell();
		return testcell;
	}
	
	private void loadRoomConfig(){
		
	}
	
	private void loadBoardConfig(){
		
	}
	
	private void calcAdjacencies(){
		
	}
	
	private void calcTargets(BoardCell cell, int pathLength){
		
	}
	
	public Map<Character, String> getLegend(){
		Map<Character, String> test = new HashMap<Character, String>();
		return test;
	}
}
