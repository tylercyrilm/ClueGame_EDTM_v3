package clueGame;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import experiment.BoardCell;

public class Board {
	private int numRows;
	private int numColumns;
	private final int MAX_BOARD_SIZE = 50;
	private BoardCell [][] board;
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
		private static Board theInstance = new Board();
		// ctor is private to ensure only one can be created
		private Board() {}
		// this method returns the only Board
		public static Board getInstance() {
			return theInstance;
		}
	
	private void initialize(){
		
	}
	
	private void loadRoomConfig(){
		
	}
	
	private void loadBoardConfig(){
		
	}
	
	private void calcAdjacencies(){
		
	}
	
	private void calcTargets(BoardCell cell, int pathLength){
		
	}
}
