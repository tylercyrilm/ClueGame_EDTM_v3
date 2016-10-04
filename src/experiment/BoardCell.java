package experiment;

import clueGame.DoorDirection;

public class BoardCell {
	public int row;
	public int column;
	
	public void setLocation(int i, int j) {
		row = i;
		column = j;
		
	}
	
	public BoardCell(){
		super();
	}
	
	@Override
	public String toString(){
		return "[" + row + ", " + column + "]";
	}
	
	public boolean isDoorway(){
		return false;
	}
	
	private DoorDirection getDoorDirection(){
		return DoorDirection.NONE;
	}
}
