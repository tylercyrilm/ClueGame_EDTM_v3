package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		@BeforeClass
		public static void setUp() {
			// Board is singleton, get the only instance and initialize it		
			board = Board.getInstance();
			board.setConfigFiles("data/ClueLayout.csv", "data/ClueLegend.txt");
			board.initialize();
		}

		// Test that player does not move around within room
		// These cells are pink on the planning spreadsheet
		@Test
		public void testAdjacenciesInsideRooms()
		{
			Set<BoardCell> testList = board.getAdjList(2, 1);
			assertEquals(0, testList.size());
			testList = board.getAdjList(4, 10);
			assertEquals(0, testList.size());
			testList = board.getAdjList(8, 19);
			assertEquals(0, testList.size());
			testList = board.getAdjList(20, 18);
			assertEquals(0, testList.size());
			testList = board.getAdjList(23, 0);
			assertEquals(0, testList.size());
		}

		// Test that the adjacency list from a doorway is only the walkway
		// These tests are green on the planning spreadsheet
		@Test
		public void testAdjacencyRoomExit()
		{
			//RIGHT 
			Set<BoardCell> testList = board.getAdjList(14, 14);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(14, 15)));
			//LEFT 
			testList = board.getAdjList(0, 5);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(0, 4)));
			//DOWN
			testList = board.getAdjList(11, 1);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(12, 1)));
			//UP
			testList = board.getAdjList(18, 6);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(17, 6)));
			
		}
		
		// Test adjacency at entrance to rooms
		// These tests are salmon pink in planning spreadsheet
		@Test
		public void testAdjacencyDoorways()
		{
			//DOWN
			Set<BoardCell> testList = board.getAdjList(4, 17);
			assertTrue(testList.contains(board.getCellAt(3, 17)));
			assertTrue(testList.contains(board.getCellAt(4, 18)));
			assertTrue(testList.contains(board.getCellAt(5, 17)));
			assertTrue(testList.contains(board.getCellAt(4, 16)));
			assertEquals(4, testList.size());
			//RIGHT
			testList = board.getAdjList(16, 4);
			assertTrue(testList.contains(board.getCellAt(15, 4)));
			assertTrue(testList.contains(board.getCellAt(16, 5)));
			assertTrue(testList.contains(board.getCellAt(17, 4)));
			assertTrue(testList.contains(board.getCellAt(16, 3)));
			assertEquals(4, testList.size());
			//LEFT
			testList = board.getAdjList(23, 15);
			assertTrue(testList.contains(board.getCellAt(22, 15)));
			assertTrue(testList.contains(board.getCellAt(23, 16)));
			assertEquals(2, testList.size());
			//UP
			testList = board.getAdjList(10, 19);
			assertTrue(testList.contains(board.getCellAt(9, 19)));
			assertTrue(testList.contains(board.getCellAt(10, 20)));
			assertTrue(testList.contains(board.getCellAt(11, 19)));
			assertTrue(testList.contains(board.getCellAt(10, 18)));
			assertEquals(4, testList.size());
		}

		// Test a variety of walkway scenarios
		//Tests two edges
		// These tests are orange on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			Set<BoardCell> testList = board.getAdjList(12, 0);
			assertTrue(testList.contains(board.getCellAt(12, 1)));
			assertTrue(testList.contains(board.getCellAt(13, 0)));
			assertEquals(2, testList.size());
			
			testList = board.getAdjList(23, 4);
			assertTrue(testList.contains(board.getCellAt(22, 4)));
			assertEquals(1, testList.size());
			
			testList = board.getAdjList(9, 20);
			assertTrue(testList.contains(board.getCellAt(10, 20)));
			assertTrue(testList.contains(board.getCellAt(9,19)));
			assertEquals(2, testList.size());
			
			testList = board.getAdjList(0, 13);
			assertTrue(testList.contains(board.getCellAt(0, 12)));
			assertTrue(testList.contains(board.getCellAt(1, 13)));
			assertEquals(2, testList.size());

			
			testList = board.getAdjList(16, 17);
			assertTrue(testList.contains(board.getCellAt(16, 16)));
			assertTrue(testList.contains(board.getCellAt(16, 18)));
			assertEquals(2, testList.size());

			testList = board.getAdjList(13,6);
			assertTrue(testList.contains(board.getCellAt(12, 6)));
			assertTrue(testList.contains(board.getCellAt(13, 7)));
			assertTrue(testList.contains(board.getCellAt(14, 6)));
			assertTrue(testList.contains(board.getCellAt(13, 5)));
			assertEquals(4, testList.size());
			
			testList = board.getAdjList(4, 14);
			assertTrue(testList.contains(board.getCellAt(4, 15)));
			assertTrue(testList.contains(board.getCellAt(5, 14)));
			assertTrue(testList.contains(board.getCellAt(4, 13)));
			assertEquals(3, testList.size());
			
			testList = board.getAdjList(4, 2);
			assertTrue(testList.contains(board.getCellAt(4, 3)));
			assertTrue(testList.contains(board.getCellAt(5, 2)));
			assertEquals(2, testList.size());

			testList = board.getAdjList(15, 3);
			assertTrue(testList.contains(board.getCellAt(15, 4)));
			assertTrue(testList.contains(board.getCellAt(14, 3)));
			assertTrue(testList.contains(board.getCellAt(15, 2)));
			assertEquals(3, testList.size());
		}
		
		
		// Tests of just walkways, 1 step, includes on edge of board
		// and beside room
		// Have already tested adjacency lists on all four edges, will
		// only test two edges here
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsOneStep() {
			board.calcTargets(15, 0, 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 0)));
			assertTrue(targets.contains(board.getCellAt(15, 1)));
			
			board.calcTargets(23, 9, 1);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCellAt(23, 10)));
			assertTrue(targets.contains(board.getCellAt(22, 9)));				
		}
		
		// Tests of just walkways, 2 steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsTwoSteps() {
			board.calcTargets(15, 0, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(13, 0)));
			assertTrue(targets.contains(board.getCellAt(14, 1)));
			assertTrue(targets.contains(board.getCellAt(15, 2)));
			
			board.calcTargets(23, 9, 2);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCellAt(22, 10)));
			assertTrue(targets.contains(board.getCellAt(21, 9)));	
		}
		
		// Tests of just walkways, 4 steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsFourSteps() {
			board.calcTargets(15, 0, 4);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(13, 0)));
			assertTrue(targets.contains(board.getCellAt(12, 1)));
			assertTrue(targets.contains(board.getCellAt(14, 1)));
			assertTrue(targets.contains(board.getCellAt(13, 2)));
			assertTrue(targets.contains(board.getCellAt(15, 2)));
			assertTrue(targets.contains(board.getCellAt(14, 3)));
			assertTrue(targets.contains(board.getCellAt(15, 4)));
			
			// Includes a path that doesn't have enough length
			board.calcTargets(23, 9, 4);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCellAt(19, 9)));
			assertTrue(targets.contains(board.getCellAt(20, 10)));	
			assertTrue(targets.contains(board.getCellAt(21, 9)));	
			assertTrue(targets.contains(board.getCellAt(22, 10)));	
		}	
		
		// Tests of just walkways plus one door, 6 steps
		// These are LIGHT BLUE on the planning spreadsheet

		@Test
		public void testTargetsSixSteps() {
			board.calcTargets(15, 0, 6);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCellAt(13, 0)));
			assertTrue(targets.contains(board.getCellAt(11, 1)));	
			assertTrue(targets.contains(board.getCellAt(12, 1)));	
			assertTrue(targets.contains(board.getCellAt(14, 1)));	
			assertTrue(targets.contains(board.getCellAt(13, 2)));	
			assertTrue(targets.contains(board.getCellAt(15, 2)));	
			assertTrue(targets.contains(board.getCellAt(12, 3)));
			assertTrue(targets.contains(board.getCellAt(14, 3)));
			assertTrue(targets.contains(board.getCellAt(16, 3)));
			assertTrue(targets.contains(board.getCellAt(13, 4)));
			assertTrue(targets.contains(board.getCellAt(15, 4)));
			assertTrue(targets.contains(board.getCellAt(17, 4)));
			assertTrue(targets.contains(board.getCellAt(14, 5)));
			assertTrue(targets.contains(board.getCellAt(16, 5)));
			assertTrue(targets.contains(board.getCellAt(15, 6)));
		}	
		
		// Test getting into a room
		// These are LIGHT BLUE on the planning spreadsheet

		@Test 
		public void testTargetsIntoRoom()
		{
			// One room is exactly 2 away
			board.calcTargets(17, 9, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(17, 7)));
			assertTrue(targets.contains(board.getCellAt(16, 8)));
			assertTrue(targets.contains(board.getCellAt(15, 9)));
			assertTrue(targets.contains(board.getCellAt(19, 9)));
			assertTrue(targets.contains(board.getCellAt(16, 10)));
			assertTrue(targets.contains(board.getCellAt(18, 10)));
			assertTrue(targets.contains(board.getCellAt(17, 11)));
		}
		
		// Test getting into room, doesn't require all steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsIntoRoomShortcut() 
		{
			board.calcTargets(17, 5, 3);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCellAt(15, 4)));
			assertTrue(targets.contains(board.getCellAt(17, 4)));
			assertTrue(targets.contains(board.getCellAt(19, 4)));
			assertTrue(targets.contains(board.getCellAt(14, 5)));
			assertTrue(targets.contains(board.getCellAt(16, 5)));
			assertTrue(targets.contains(board.getCellAt(15, 6)));
			assertTrue(targets.contains(board.getCellAt(17, 6)));
			assertTrue(targets.contains(board.getCellAt(18, 6)));
			assertTrue(targets.contains(board.getCellAt(16, 7)));
			assertTrue(targets.contains(board.getCellAt(18, 7)));
			assertTrue(targets.contains(board.getCellAt(17, 8)));
			assertTrue(targets.contains(board.getCellAt(16, 3)));
			
		}

		// Test getting out of a room
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testRoomExit()
		{
			// Take one step, essentially just the adj list
			board.calcTargets(4, 9, 1);
			Set<BoardCell> targets= board.getTargets();
			// Ensure doesn't exit through the wall
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(5, 9)));
			// Take two steps
			board.calcTargets(4, 9, 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(6, 9)));
			assertTrue(targets.contains(board.getCellAt(5, 8)));
			assertTrue(targets.contains(board.getCellAt(5, 10)));
		}

	}