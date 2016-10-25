package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.Player;

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
		assertEquals("Hangar", board.suggestion.room);
		//if only one weapon not seen, it's selected
		assertEquals("Rock", board.suggestion.weapon);
		//if only one person not seen, it's selected
		assertEquals("Arlan Citrine", board.suggestion.person);
		//if multiple weapons not seen, one of them randomly selected
		int blasterTest = 0;
		int knifeTest = 0;
		int hcTest = 0;
		for (int i=0; i<300; i++) {
			testCompPlayer.createSuggestion();
			if(board.suggestion.weapon == "Blaster") {
				blasterTest++;
			}
			else if(board.suggestion.weapon == "Scalpel"){ 
				knifeTest++;
			}
			else if(board.suggestion.weapon == "Heating Coil") {
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
			testCompPlayer.createSuggestion();
			if(board.suggestion.person == "Captain Ebony") {
				ebonyTest++;
			}
			else if(board.suggestion.person == "Rosette Foxwell"){ 
				foxwellTest++;
			}
			else if(board.suggestion.person == "Prime Viridian") {
				viridianTest++;
			}
		}
		assert(ebonyTest > 1);
		assert(foxwellTest > 1);
		assert(viridianTest > 1);
	}

}
