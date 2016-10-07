package tests;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import experiment.IntBoard;


public class FileInitTests{
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 21;
	
	public static Board board;
	
	@BeforeClass
	public static void setUp(){
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueLegend.txt");
		board.initialize();
	}

	@Test
	public void testRooms(){
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Mercury", legend.get('M'));
		assertEquals("Neptune", legend.get('N'));
		assertEquals("Jupiter", legend.get('J'));
		assertEquals("Uranus", legend.get('U'));
	}


	@Test
	public void testBoardDimensions(){
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}


	//Test to verify at least one doorway in each direction.
	//Also verify cells that don't contain doorways return false for isDoorway
	@Test
	public void FourDoorDirections(){
		BoardCell room = board.getCellAt(0,5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(1,2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(4,9);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(18,7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(19,5);
		assertFalse(room.isDoorway());
		room = board.getCellAt(9,3);
		assertFalse(room.isDoorway());
		BoardCell cell = board.getCellAt(7, 5);
		assertFalse(cell.isDoorway());
	}
	

	//Test that the correct number of doors have been loaded
	@Test
	public void testNumberOfDoorways(){
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
	public void testRoomInitial(){
		assertEquals('M', board.getCellAt(1, 1).getInitial());
		assertEquals('V', board.getCellAt(4, 8).getInitial());
		assertEquals('U', board.getCellAt(22, 7).getInitial());
		assertEquals('X', board.getCellAt(10, 9).getInitial());
		assertEquals('W', board.getCellAt(13, 2).getInitial());
		assertEquals('A', board.getCellAt(15, 19).getInitial());
	}
	
}
