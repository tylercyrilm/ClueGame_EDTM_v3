package clueGame;
import java.io.FileNotFoundException;

//import javax.swing.JFrame;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.awt.Color;

public class Board {
	public int numRows = 0;
	public int numColumns = 0;
	private final int MAX_BOARD_SIZE = 50;
	private BoardCell [][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	public Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private String boardConfigFile;
	private String roomConfigFile;
	private String weaponConfigFile = "";
	private String playerConfigFile = "";
	private String legendInitials = "";
	public HumanPlayer player;
	public ArrayList<ComputerPlayer> comp = new ArrayList<ComputerPlayer>();
	private Set<Card> dealDeck;
	public ArrayList<Card> roomCards;
	public ArrayList<Card> personCards;
	public ArrayList<Card> weaponCards;
	public Set<Card> dealtCards = new HashSet<Card>();
	private static Solution solution = new Solution();
	public Solution suggestion;
	
	public void setSolution(String person, String room, String weapon) {
		solution.person = person;
		solution.room = room;
		solution.weapon = weapon;
	}
	
	public Solution getSolution() {
		return solution;
	}
	
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
		return board[row][col];
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
		roomCards = new ArrayList<Card>();
		try{
			FileReader input = new FileReader(roomConfigFile);
			Scanner in = new Scanner(input);
			while(in.hasNextLine()){
				String nextLine = in.nextLine();
				String[] splitPieces = nextLine.split(", ");
				rooms.put(splitPieces[0].charAt(0), splitPieces[1]);
				legendInitials += splitPieces[0].charAt(0);
				if (splitPieces[2].equalsIgnoreCase("Card")) {
					Card roomCard = new Card(splitPieces[1], CardType.ROOM);
					roomCards.add(roomCard);
				}
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
					board[i][j] = new BoardCell();
					board[i][j].setLocation(i,j);
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
				board[numRows][i] = new BoardCell();
				board[numRows][i].initial = label.charAt(0);
				
				if(legendInitials.indexOf(board[numRows][i].initial) < 0){
					throw new BadConfigFormatException("Invalid room initial");
				}
				
				if(label.length() > 1){
					Character dir = label.charAt(1);
					switch (dir){
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
				
			}//end of for loop
			
			numRows++;

		} //end of while loop
		BoardCell [][] tempArray = new BoardCell[numRows][rowLength];
		for(int i = 0; i < numRows; i++){
			for (int j = 0; j < rowLength; j++){
				tempArray[i][j] = new BoardCell();
				tempArray[i][j] = board[i][j];
				board[i][j].setLocation(i, j);
			}
		}
		board = tempArray;
		
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException {
		int idCounter = 0;
		personCards = new ArrayList<Card>();
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
					player.setColor(convertColor(splitPieces[1]));
					player.setRow(Integer.parseInt(splitPieces[2]));
					player.setColumn(Integer.parseInt(splitPieces[3]));
					player.id = -1;
				}
				else if(splitPieces[4].equals("C")) {
					ComputerPlayer C1 = new ComputerPlayer();
					C1.setName(splitPieces[0]);
					C1.setColor(convertColor(splitPieces[1]));
					C1.setRow(Integer.parseInt(splitPieces[2]));
					C1.setColumn(Integer.parseInt(splitPieces[3]));
					C1.id = idCounter;
					comp.add(C1);
					idCounter++;
				}
				else {
					throw new BadConfigFormatException("The player configuration file is not in the correct format. Correct it and load again.");
				}
				Card playerCard = new Card(splitPieces[0], CardType.PERSON);
				personCards.add(playerCard);
			}
			in.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void loadWeaponConfig() {
		weaponCards = new ArrayList<Card>();
		try{
			FileReader input = new FileReader(weaponConfigFile);
			Scanner in = new Scanner(input);
			while(in.hasNextLine()){
				String nextLine = in.nextLine();
				Card weaponCard = new Card(nextLine, CardType.WEAPON);
				weaponCards.add(weaponCard);	
			}
			in.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void calcAdjacencies(){
		int x = board[0].length;
		int y = board.length;
		for (int i = 0; i < y; i++){
			for (int j = 0; j < x; j++){
				Set<BoardCell> adj = new HashSet<BoardCell>();
				if (board[i][j].initial != 'W'){ 
					if (board[i][j].isDoorway() == false){
						//in a room and not in a doorway
						//no adjacencies added
					}
					else{
						//in a doorway
						switch(board[i][j].direction){
						case UP:
							adj.add(board[i-1][j]);
							break;
						case DOWN:
							adj.add(board[i+1][j]);
							break;
						case LEFT:
							adj.add(board[i][j-1]);
							break;
						case RIGHT:
							adj.add(board[i][j+1]);
							break;
						case NONE:
							break;
						}
					}
				}
				else{
				//outside of a room and not in a doorway
				if(i - 1 >= 0){ 
					if((board[i-1][j].isDoorway() && board[i-1][j].direction == DoorDirection.DOWN)|| board[i-1][j].initial == 'W'){
						adj.add(board[i-1][j]);
					}
				}
				if (i + 1 < y){
					if((board[i+1][j].isDoorway() && board[i+1][j].direction == DoorDirection.UP)|| board[i+1][j].initial == 'W'){
						adj.add(board[i +1][j]);
					}
				}
				if (j - 1 >= 0){
					if((board[i][j-1].isDoorway() && board[i][j-1].direction == DoorDirection.RIGHT)|| board[i][j-1].initial == 'W'){
						adj.add(board[i][j-1]);
					}
				}
				if (j + 1 < x){
					if((board[i][j+1].isDoorway() && board[i][j+1].direction == DoorDirection.LEFT)|| board[i][j+1].initial == 'W'){
						adj.add(board[i][j+1]);
					}
				}
				
			}
			adjMatrix.put(board[i][j], adj);
			}
		}
	}
	
	public void calcTargets(int row, int column, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(board[row][column]);
		findAllTargets(row, column, pathLength);
	}
	
	public void findAllTargets(int row, int col, int pathLength){
		Set<BoardCell> adjCells = getAdjList(row, col);

		for (BoardCell adjCell : adjCells) {
			if(!visited.contains(adjCell)){
				if (pathLength == 1 || adjCell.isDoorway()) {
					targets.add(adjCell);
				}
				else {
					visited.add(board[adjCell.row][adjCell.column]);
					findAllTargets(adjCell.row, adjCell.column, pathLength - 1);
				}
			}
			else {
				continue;
			}
			visited.remove(board[adjCell.row][adjCell.column]);
		}
	}
	
	private void buildDeck() {
		dealDeck = new HashSet<Card>();
		//Add all room cards not in the solution to the deck
		for (Card r:roomCards) {
			if (!r.getName().equals(solution.room)) {
				dealDeck.add(r);
			}
		}
		//Add all person cards not in the solution to the deck
		for (Card p:personCards) {
			if (!p.getName().equals(solution.person)) {
				dealDeck.add(p);
			}
		}
		//Add all weapon cards not in the solution to the deck
		for (Card w:weaponCards) {
			if (!w.getName().equals(solution.weapon)) {
				dealDeck.add(w);
			}
		}
	}
	
	public void deal() {
		//Randomly chooses an answer from the full deck
		selectAnswer();
		//Builds a deck to deal from that does not include the solution cards
		buildDeck();
		//Deal the cards to each players hand
		int counter = 0;
		for (Card c : dealDeck) {
			int recip = (counter % (comp.size() + 1)) - 1;
			if (recip == -1) {
				player.hand.add(c);
			}
			else {
				comp.get(recip).hand.add(c);
			}
			dealtCards.add(c);
			counter++;
		}

	}
	
	public void selectAnswer() {
		//Assign the "winning" 3 cards
		Random r = new Random();
		//Select location
		int rand = r.nextInt(roomCards.size());
		Card place = roomCards.get(rand);
		//Select culprit
		rand = r.nextInt(personCards.size());
		Card person = personCards.get(rand);
		//Select weapon
		rand = r.nextInt(weaponCards.size());
		Card weapon = weaponCards.get(rand);
		//Build the solution
		setSolution(person.getName(), place.getName(), weapon.getName());
	}
	
	public Card handleSuggestion(Solution suggestion) {
		//Check ID which is equal to the index
		int id;
		if (suggestion.accuserId == comp.size()-1) {
			id = -1;
		}
		else {
			id = suggestion.accuserId + 1;
		}
		int i = id +1;
		Card resolution = null;
		//Players to go in order and look in their own hands for the card
		while (id != suggestion.accuserId) {
			if (id == -1) {
				resolution = player.disproveSuggestion(suggestion);
			}
			else {
				resolution = comp.get(id).disproveSuggestion(suggestion);
			}
			if (resolution != null) {
				return resolution;
			}
			i++;
			id = (i % (comp.size() + 1)) - 1;
		}
		//If card not in player hand, move to next player
		//P--1--2--3--4--5--P
		return resolution;
	}
	
	public boolean checkAccusation(Solution accusation) {
		if (accusation.person.equals(solution.person) && accusation.weapon.equals(solution.weapon) && accusation.room.equals(solution.room)){
			return true;
		}
		else {
			return false;
		}
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
		return adjMatrix.get(board[row][col]);
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public Color convertColor(String strColor) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null;
		}
		return color;
	}
}
