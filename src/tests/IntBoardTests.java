package tests;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	private static BoardCell[][] grid;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		grid = new BoardCell[4][4];
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				grid[i][j] = new BoardCell();
				grid[i][j].setLocation(i, j);
			}
		}
	}

	@Test
	//Test adjacencies for top left corner, location[0][0]
	public void testAdjacecy00(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		System.out.println(testList);
		System.out.println(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	//Test bottom right corner, location[3][3]
	public void testAdjacency33(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(3, 3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	@Test
	//Test right edge, location[1][3]
	public void testAdjacency13(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(1, 3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	@Test
	//Test left edge, location[3][0]
	public void testAdjacency30(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(3, 0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	//Test second column middle of grid, location[1][1]
	public void testAdjacency11(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(1, 1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	//Test second from last column middle of grid, location[2][2]
	public void testAdjacency22(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(2, 2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	//Test targets for location 00 w/ 3 steps
	public void testTarget00_3(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	//Test targets for location 00 w/ 2 steps
	public void testTarget00_2(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	@Test
	//Test targets for location 00 w/ 1 step
	public void testTarget00_1(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	//Test targets for location 22 w/ 1 steps
	public void testTarget22_1(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
	}
	
	@Test
	//Test targets for location 22 w/ 2 steps
	public void testTarget22_2(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
	}
	
	@Test
	//Test targets for location 00 w/ 3 steps
	public void testTarget22_3(){
		IntBoard board = new IntBoard(grid);
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
	}
	
	

}

