package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
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
		//if no rooms in list, select randomly
		ComputerPlayer testCompPlayer = new ComputerPlayer();
		testCompPlayer.setLocation(17, 23);
		board.calcTargets(17, 23, 2);

		int L1 = 0;
		int L2 = 0;
		int L3 = 0;
		int L4 = 0;
		for (int i=0; i<300; i++) {
			BoardCell testCell = testCompPlayer.pickLocation(board.targets);
			if(testCell == (board.getCellAt(17, 21))) {
				L1++;
			}
			else if (testCell == (board.getCellAt(16, 22))) {
				L2++;
			}
			else if (testCell == (board.getCellAt(16, 24))) {
				L3++;
			}
			else if (testCell == board.getCellAt(17, 25)) {
				L4++;
			}
		}
		assert(L1 > 1);
		assert(L2 > 1);
		assert(L3 > 1);
		assert(L4 > 1);
		//if room in list that was not just visited, must select it
		testCompPlayer.setLocation(15, 21);
		board.calcTargets(15, 21, 3);
		assertEquals(board.getCellAt(13, 22), testCompPlayer.pickLocation(board.targets));
		//if room just visited is in list, each target including room selected randomly
		int L11 = 0;
		int L22 = 0;
		int L33 = 0;
		int L44 = 0;
		int L55 = 0;
		int L66 = 0;
		int L77 = 0;
		int L88 = 0;
		int L99 = 0;
		int room = 0;
		for (int i=0; i<300; i++) {
			BoardCell testCell = testCompPlayer.pickLocation(board.targets);
			if(testCell == (board.getCellAt(13, 20))) {
				L11++;
			}
			else if (testCell == (board.getCellAt(13, 22))) {
				L22++;
			}
			else if (testCell == (board.getCellAt(14, 19))) {
				L33++;
			}
			else if (testCell == board.getCellAt(14, 21)) {
				L44++;
			}
			else if (testCell == board.getCellAt(15, 20)) {
				L55++;
			}
			else if (testCell == board.getCellAt(16, 21)) {
				L66++;
			}
			else if (testCell == board.getCellAt(16, 23)) {
				L77++;
			}
			else if (testCell == board.getCellAt(17, 20)) {
				L88++;
			}
			else if (testCell == board.getCellAt(17, 22)) {
				L99++;
			}
			else if (testCell == board.getCellAt(12, 21)) {
				room++;
			}
		}
		assert(L11 > 1);
		assert(L22 > 1);
		assert(L33 > 1);
		assert(L44 > 1);
		assert(L55 > 1);
		assert(L66 > 1);
		assert(L77 > 1);
		assert(L88 > 1);
		assert(L99 > 1);
		assert(room > 1);
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
