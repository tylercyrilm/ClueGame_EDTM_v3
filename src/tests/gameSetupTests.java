package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;
	
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("data/ENTM_ClueMap.csv", "data/ENTM_Legend.txt");
		board.setWPConfigFiles("data/ENTM_CluePlayers.txt", "data/ENTM_ClueWeapons.txt");
		board.initialize();
		board.deal();
	}

	@Test
	public void playerInitTest() {
		Player testPlayer = new HumanPlayer();
		testPlayer = board.player;
		assertEquals(testPlayer.getName(), "Rosette Foxwell");
		assertEquals(Color.RED, testPlayer.getColor());
		assertEquals(testPlayer.getRow(), 17);
		assertEquals(testPlayer.getColumn(), 16);
		
		Player testCompPlayer = new ComputerPlayer();
		testCompPlayer = board.comp.get(0);
		assertEquals(testCompPlayer.getName(), "SL-8-R");
		assertEquals(Color.GRAY, testCompPlayer.getColor());
		assertEquals(testCompPlayer.getRow(), 11);
		assertEquals(testCompPlayer.getColumn(), 1);
		testCompPlayer = board.comp.get(4);
		assertEquals(testCompPlayer.getName(), "Hunter Cobalt");
		assertEquals(Color.BLUE, testCompPlayer.getColor());
		assertEquals(testCompPlayer.getRow(), 19);
		assertEquals(testCompPlayer.getColumn(), 11);
		
	}

	@Test
	public void loadCardsTest() {
		//Ensure deck contains correct total number of cards
		assertEquals(21, board.roomCards.size() + board.personCards.size() + board.weaponCards.size());
		//Ensure deck contains correct number of each type of card
		assertEquals(9, board.roomCards.size());
		assertEquals(6, board.personCards.size());
		assertEquals(6, board.weaponCards.size());
		//Choose one room, one weapon, one person, ensure the deck contains each of those--test loading names
		assertEquals("Hangar", board.roomCards.get(0).getName());
		assertEquals("SL-8-R", board.personCards.get(0).getName());
		assertEquals("Rock", board.weaponCards.get(5).getName());
	}
	
	private boolean hasNoCommonElements(Set<Card> s1, Set<Card> s2) {
		for (Card c1: s1) {
			if (s2.contains(c1)) {
				return false;
			}
		}
		return true;
	}
	
	@Test
	public void dealTest() {
		Player testPlayer;
		testPlayer = board.player;
		//all cards should be dealt
		assertEquals(18, board.dealtCards.size());
		//all players should have roughly same # of cards
		assertEquals(3, board.player.hand.size());
		assertEquals(3, board.comp.get(0).hand.size());
		assertEquals(3, board.comp.get(1).hand.size());
		assertEquals(3, board.comp.get(2).hand.size());
		assertEquals(3, board.comp.get(3).hand.size());
		assertEquals(3, board.comp.get(4).hand.size());
		//each card should be dealt only once
		assertTrue(hasNoCommonElements(board.player.hand, board.comp.get(0).hand));
		assertTrue(hasNoCommonElements(board.player.hand, board.comp.get(1).hand));
		assertTrue(hasNoCommonElements(board.player.hand, board.comp.get(2).hand));
		assertTrue(hasNoCommonElements(board.player.hand, board.comp.get(3).hand));
		assertTrue(hasNoCommonElements(board.player.hand, board.comp.get(4).hand));
	}
}
