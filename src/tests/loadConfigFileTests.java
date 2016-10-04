package tests;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import experiment.BoardCell;
import experiment.IntBoard;

public class loadConfigFileTests{
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 21;
	
	@BeforeClass
	public static void setUp(){
		board = Board.getInstance();
		board.setConfigFiles("ClueLegend.txt", "ClueLayout.csv");
		board.initialize();
	}

	@Test
	public void numRoomsTest(){
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Mercury", legend.get('M'));
		assertEquals("Neptune", legend.get('N'));
		assertEquals("Jupiter", legend.get('J'));
		assertEquals("Uranus", legend.get('U'));
	}


	@Test
	public void numRowsColsTest(){
		assertEquals(NUM_ROWS, board.numRows);
		assertEquals(NUM_COLUMNS, board.numColumns);
	}


	//Test to verifty at least one doorway in each direction.
	//Also verify cells that don't contain doorways return false for isDoorway
	@Test
	public void doorwayDirectionsTest(){
		
	}
	

	//Test that the correct number of doors have been loaded



	//Test some of the cells to ensure they have the correct initial
	
	//test comment
	
	
}