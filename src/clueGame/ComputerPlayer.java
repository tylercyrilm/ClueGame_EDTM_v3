package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import clueGame.Board;

public class ComputerPlayer extends Player {
	public Set<Card> seenPeopleCards = new HashSet<Card>();
	public Set<Card> seenWeaponCards = new HashSet<Card>();
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell testCell = new BoardCell();
		return testCell;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion(Board board) {
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
	}
}
