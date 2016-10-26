package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
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
		//if room is in list that was not just visited, must select it
		testCompPlayer.setLocation(15, 21);
		board.calcTargets(15, 21, 3);
		assertEquals(board.getCellAt(13, 22), testCompPlayer.pickLocation(board.targets));
		testCompPlayer.visitedRooms.add(board.getCellAt(13, 22));
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
	public void testAccusationCheck() {
		ComputerPlayer testCompPlayer = new ComputerPlayer();
		Solution testAccusation = new Solution();
		board.selectAnswer();
		board.setSolution("Hunter Cobalt","Rec Room", "Wrench");
		//Correct Solution
		testAccusation = testCompPlayer.makeAccusation("Hunter Cobalt", "Rec Room", "Wrench");
		assertTrue(board.checkAccusation(testAccusation));
		//Wrong Person
		testAccusation = testCompPlayer.makeAccusation("Captain Ebony", "Rec Room", "Wrench");
		assertFalse(board.checkAccusation(testAccusation));
		//Wrong Weapon
		testAccusation = testCompPlayer.makeAccusation("Hunter Cobalt", "Rec Room", "Rock");
		assertFalse(board.checkAccusation(testAccusation));
		//Wrong Room
		testAccusation = testCompPlayer.makeAccusation("Hunter Cobalt", "Kitchen", "Wrench");
		assertFalse(board.checkAccusation(testAccusation));
	}
	
	@Test
	public void testDisproveSuggestion() {
		//if player has only one matching card it should be returned
		Card testRoomCard = new Card("Mess Hall", CardType.ROOM);
		Card testPlayerCard = new Card("Captain Ebony", CardType.PERSON);
		ComputerPlayer testCompPlayer = new ComputerPlayer();
		testCompPlayer.hand.add(testRoomCard);
		Solution testSuggestion = new Solution("Arlan Citrine", "Mess Hall", "Heating Coil");
		assertEquals(testRoomCard, testCompPlayer.disproveSuggestion(testSuggestion));
		//if player has >1 matching card, returned card should be chosen randomly
		testSuggestion = new Solution("Captain Ebony", "Mess Hall", "Heating Coil");
		testCompPlayer.hand.add(testPlayerCard);
		int testp = 0;
		int testr = 0;
		for (int i=0; i<300; i++) {
			if(testPlayerCard.getName().equals(testCompPlayer.disproveSuggestion(testSuggestion).getName())) {
				testp++;
			}
			else if (testRoomCard.getName().equals(testCompPlayer.disproveSuggestion(testSuggestion).getName())) {
				testr++;
			}
		}
		assert(testp > 1);
		assert(testr > 1);
		//if player has no matching cards, null is returned
		testSuggestion = new Solution("Arlan Citrine", "Laboratory", "Heating Coil");
		assertEquals(null, testCompPlayer.disproveSuggestion(testSuggestion));
	}
	
	@Test
	public void testSuggestionHandling() {
		
		Solution testSuggestion = new Solution();
		board.deal();
		testSuggestion = board.getSolution();
		Card testCard = new Card(testSuggestion.person, CardType.PERSON);
		ComputerPlayer accusingCompPlayer = board.comp.get(0);
		ComputerPlayer accusingCompPlayerTwo = board.comp.get(3);
		HumanPlayer humanPlayer = board.player;
		//suggestion no one can disprove returns null
		assertEquals(null, board.handleSuggestion(testSuggestion));
		//suggestion only accusing player can disprove returns null
		accusingCompPlayer.hand.add(testCard);
		assertEquals(null, board.handleSuggestion(testSuggestion));
		accusingCompPlayer.hand.remove(testCard);
		//suggestion only human can disprove returns answer
		humanPlayer.hand.add(testCard);
		assertEquals(testCard, board.handleSuggestion(testSuggestion));
		//suggestion only human can disprove, but human is accuser, returns null
		testSuggestion.setAccuserId(-1);
		assertEquals(null, board.handleSuggestion(testSuggestion));
		//suggestion that 2 players can disprove, correct player returns answer
		accusingCompPlayer.hand.add(testCard);
		Card testCardTwo = new Card(testSuggestion.room, CardType.ROOM);
		accusingCompPlayerTwo.hand.add(testCardTwo);
		assertEquals(testCard, board.handleSuggestion(testSuggestion));
		//suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
		accusingCompPlayer.hand.remove(testCard);
		humanPlayer.hand.add(testCard);
		testSuggestion.setAccuserId(0);
		assertEquals(testCardTwo, board.handleSuggestion(testSuggestion));
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
