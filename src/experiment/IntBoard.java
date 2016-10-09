package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjacent = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private BoardCell[][] grid;
	
	public IntBoard(BoardCell[][] grid) {
		super();
		this.grid = grid;
		calcAdjacencies();
	}

	public void calcAdjacencies(){
		int x = grid[0].length;
		int y = grid.length;
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				Set<BoardCell> adj = new HashSet();
				if(i - 1 >= 0){ 
					adj.add(grid[i-1][j]);
				}
				if (i + 1 < x){
					adj.add(grid[i +1][j]);
				}
				if (j - 1 >= 0){
					adj.add(grid[i][j-1]);
				}
				if (j + 1 < y){
					adj.add(grid[i][j+1]);
				}
				adjacent.put(grid[i][j], adj);
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
		System.out.println("here");
		//set of adjcells by calling calcadjacent for startcell
		Set<BoardCell> adjCells = getAdjList(startCell);
		visited.add(startCell);
		if(pathLength == 1){
			for (BoardCell adjCell : adjCells) {
				System.out.println(adjCell.getInitial());
				if(!visited.contains(adjCell)){
					targets.add(adjCell);
				}
			}
		}
		else{
			for (BoardCell adjCell : adjCells) {
				calcTargets(adjCell, pathLength - 1);
			}
		}
		visited.remove(startCell);
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjacent.get(cell);
	}
	
	public BoardCell getCell(int row, int column){
		return grid[row][column];	
	}
	
	
}
