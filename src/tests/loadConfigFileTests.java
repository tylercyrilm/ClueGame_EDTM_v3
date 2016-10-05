package tests;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.DoorDirection;
import experiment.BoardCell;
import experiment.IntBoard;

public class loadConfigFileTests{
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 21;
	
	@BeforeClass
	public static void setUp(){
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLegend.txt", "ClueLayout.csv");
		board.initialize();
	}

	@Test
	public void roomsTest(){
		Board board = Board.getInstance();
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Mercury", legend.get('M'));
		assertEquals("Neptune", legend.get('N'));
		assertEquals("Jupiter", legend.get('J'));
		assertEquals("Uranus", legend.get('U'));
	}


	@Test
	public void numRowsColsTest(){
		Board board = Board.getInstance();
		assertEquals(NUM_ROWS, board.numRows);
		assertEquals(NUM_COLUMNS, board.numColumns);
	}


	//Test to verify at least one doorway in each direction.
	//Also verify cells that don't contain doorways return false for isDoorway
	@Test
	public void doorwayDirectionsTest(){
		Board board = Board.getInstance();
		BoardCell room = board.getCellAt(1,5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(2,3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(5,10);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(19,7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(19,5);
		assertFalse(room.isDoorway());
		room = board.getCellAt(9,3);
		assertFalse(room.isDoorway());
	}
	

	//Test that the correct number of doors have been loaded
	@Test
	public void testNumDoors(){
		Board board = Board.getInstance();
		int doorNums = 0;
		for(int r = 0; r < NUM_ROWS; r++){
			for(int c = 0; c < NUM_COLUMNS; c++){
				BoardCell cell = board.getCellAt(r, c);
				if(cell.isDoorway()){
					doorNums++;
				}
			}
		}
		assertEquals(12, doorNums);
	}


	//Test some of the cells to ensure they have the correct initial
	@Test
	public void testRoomLetters(){
		Board board = Board.getInstance();
		assertEquals('M', board.getCellAt(1, 1).getLabel());
		assertEquals('V', board.getCellAt(4, 8).getLabel());
		assertEquals('U', board.getCellAt(22, 7).getLabel());
		assertEquals('X', board.getCellAt(10, 9).getLabel());
		assertEquals('W', board.getCellAt(13, 2).getLabel());
		assertEquals('A', board.getCellAt(15, 19).getLabel());
	}
	
}

//Made comment to see if git bash is working
