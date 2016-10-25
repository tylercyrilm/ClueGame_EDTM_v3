package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("data/ENTM_ClueMap.csv", "data/ENTM_Legend.txt");
		board.setWPConfigFiles("data/ENTM_CluePlayers.txt", "data/ENTM_ClueWeapons.txt");
		board.initialize();
		
	}

	@Test
	public void testTargetSelection() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAccustaionCheck() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDisproveSuggestion() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSuggestionHandling() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSuggestionCreation() {
		//Room matches current location
		ComputerPlayer testCompPlayer = new ComputerPlayer();
		testCompPlayer.setLocation(7, 22);
		testCompPlayer.createSuggestion(board);
		assertEquals("Hangar", board.suggestion.room);
		//if only one weapon not seen, it's selected
		for (Card c : board.weaponCards) {
			if (!c.getName().equals("Rock")) {
				testCompPlayer.seenWeaponCards.add(c);
			}
		}
		testCompPlayer.createSuggestion(board);
		assertEquals("Rock", board.suggestion.weapon);
		//if only one person not seen, it's selected
		for (Card c : board.personCards) {
			if (!c.getName().equals("Arlan Citrine")) {
				testCompPlayer.seenPeopleCards.add(c);
			}
		}
		testCompPlayer.createSuggestion(board);
		assertEquals("Arlan Citrine", board.suggestion.person);
		
		
		
		
		
		//if multiple weapons not seen, one of them randomly selected
		testCompPlayer.seenPeopleCards.clear();
		testCompPlayer.seenWeaponCards.clear();
		
		//Set-up for random selection
		for (Card c : board.weaponCards) {
			if (!c.getName().equals("Blaster") && !c.getName().equals("Scalpel") && !c.getName().equals("Heating Coil")) {
				testCompPlayer.seenWeaponCards.add(c);
			}
		}
		for (Card c : board.personCards) {
			if (!c.getName().equals("Captain Ebony") && !c.getName().equals("Rosette Foxwell") && !c.getName().equals("Prime Viridian")) {
				testCompPlayer.seenPeopleCards.add(c);
			}
		}
		int blasterTest = 0;
		int knifeTest = 0;
		int hcTest = 0;
		for (int i=0; i<300; i++) {
			testCompPlayer.createSuggestion(board);
			if(board.suggestion.weapon.equals("Blaster")) {
				blasterTest++;
			}
			else if(board.suggestion.weapon.equals("Scalpel")){ 
				knifeTest++;
			}
			else if(board.suggestion.weapon.equals("Heating Coil")) {
				hcTest++;
			}
		}
		assert(blasterTest > 1);
		assert(knifeTest > 1);
		assert(hcTest > 1);
		//if  multiple persons not seen, one randomly selected
		int ebonyTest = 0;
		int foxwellTest = 0;
		int viridianTest = 0;
		for (int i=0; i<300; i++) {
			testCompPlayer.createSuggestion(board);
			if(board.suggestion.person.equals("Captain Ebony")) {
				ebonyTest++;
			}
			else if(board.suggestion.person.equals("Rosette Foxwell")){ 
				foxwellTest++;
			}
			else if(board.suggestion.person.equals("Prime Viridian")) {
				viridianTest++;
			}
		}
		assert(ebonyTest > 1);
		assert(foxwellTest > 1);
		assert(viridianTest > 1);
	}

}
