package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import clueGame.Board;

public class ComputerPlayer extends Player {
	public Set<Card> seenPeopleCards = new HashSet<Card>();
	public Set<Card> seenWeaponCards = new HashSet<Card>();
	public Set<Card> seenRoomCards = new HashSet<Card>();
	public Set<BoardCell> visitedRooms = new HashSet<BoardCell>();
	public ArrayList<BoardCell> doorsInRange = new ArrayList<BoardCell>();
	public ArrayList<BoardCell> totalRooms = new ArrayList<BoardCell>();
	
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		doorsInRange.clear();
		totalRooms.clear();
		for (BoardCell bc : targets) {
			if (bc.isDoorway()) {
				doorsInRange.add(bc);
			} 
		}
		
		if (doorsInRange.size() == 1) {
			if (!visitedRooms.contains(doorsInRange.get(0))) {
				//set player location
				totalRooms.add(doorsInRange.get(0));
			}
			else {
				for (BoardCell c: targets) {
					totalRooms.add(c);		//all targets should be in totalRooms
				}
			}
		}
		
		else if(doorsInRange.size() > 1) {
			if((visitedRooms.contains(doorsInRange.get(0)) && (visitedRooms.contains(doorsInRange.get(1))))) {
				for (BoardCell c: targets) {
					totalRooms.add(c);		//all targets should be in totalRooms
				}
			}
			else if(visitedRooms.contains(doorsInRange.get(0))) {
				totalRooms.add(doorsInRange.get(1));
			}
			else if(visitedRooms.contains(doorsInRange.get(1))) {
				totalRooms.add(doorsInRange.get(0));
			}
			else {
				totalRooms.add(doorsInRange.get(0));
				totalRooms.add(doorsInRange.get(1));
			}
		}
		
		else {
			for (BoardCell c: targets) {
				totalRooms.add(c);		//all targets should be in totalRooms
			}
		}
		Random rand = new Random();
		BoardCell returnCell = totalRooms.get(rand.nextInt(totalRooms.size()));
		row = returnCell.row;
		column = returnCell.column;
		
		return returnCell;
	}
	
	public Solution makeAccusation(String person, String room, String weapon) {
		return new Solution(person, room, weapon);
	}
	

	public void createSuggestion(Board board) {
		if(board.getCellAt(row, column).isDoorway()) {
			Random rand = new Random();
			ArrayList<String> allNonSeenPeople = new ArrayList<String>();
			ArrayList<String> allNonSeenWeapons = new ArrayList<String>();
			String accusedPerson = "";
			String accusedWeapon = "";
			String accusedRoom = "";
			
			//set room to the room you are in
			char r;
			r = board.getCellAt(row, column).getInitial();
			accusedRoom = board.getLegend().get(r);
			
			//Check People
			for (Card c : board.personCards) {
				if (!seenPeopleCards.contains(c)){
					allNonSeenPeople.add(c.getName());
				}
			}
			if (allNonSeenPeople.size() == 1) {
				accusedPerson = allNonSeenPeople.get(0);
			}
			else if (allNonSeenPeople.size() > 1) {
				accusedPerson = allNonSeenPeople.get(rand.nextInt(allNonSeenPeople.size()));
			}
			
			//Check Weapons
			for (Card c : board.weaponCards) {
				if (!seenWeaponCards.contains(c)){
					allNonSeenWeapons.add(c.getName());
				}
			}
			if (allNonSeenWeapons.size() == 1) {
				accusedWeapon = allNonSeenWeapons.get(0);
			}
			else if (allNonSeenWeapons.size() > 1) {
				accusedWeapon = allNonSeenWeapons.get(rand.nextInt(allNonSeenWeapons.size()));
			}
			
			board.suggestion = new Solution(accusedPerson, accusedRoom, accusedWeapon);
			board.handleSuggestion(board.suggestion);
			
		}
	}

	@Override
	public void makeMove(Set<BoardCell> targets, Board board) {
		if (board.proveSuggestionFalse.equals("No new clue")) {
			board.accusation = makeAccusation(board.suggestion.person, board.suggestion.room, board.suggestion.weapon);
			if(board.checkAccusation(board.accusation)){
				JOptionPane.showMessageDialog(null, playerName + " correctly accused " + board.getSolution().person +" in the "+board.getSolution().room+" with the "+board.getSolution().weapon+"." , "Game Over", JOptionPane.INFORMATION_MESSAGE);
				board.gameRunning = false;
			}
			else {
				JOptionPane.showMessageDialog(null, playerName + " incorrectly accused " + board.getSolution().person +" in the "+board.getSolution().room+" with the "+board.getSolution().weapon+"." , "Keep playing", JOptionPane.INFORMATION_MESSAGE);
				board.accusation = null;
				board.proveSuggestionFalse = "";
			}
		}
		if(pickLocation(targets).isDoorway()) {
			boolean personMoved = false;
			createSuggestion(board);
			for (ComputerPlayer p: board.comp) {
				if (board.suggestion.person.equals(p.getName())) {
					p.setLocation(row, column);
					personMoved = true;
					break;
				}
			}
			if (!personMoved){
				board.player.setLocation(row, column);
				visitedRooms.add(board.getCellAt(row, column));
			}
		}
	}
}
