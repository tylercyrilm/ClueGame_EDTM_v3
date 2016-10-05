package experiment;

import clueGame.DoorDirection;

public class BoardCell {
	public int row;
	public int column;
	public String label;
	
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
	
	public DoorDirection getDoorDirection(){
		return DoorDirection.NONE;
	}
	
	public String getLabel(){
		return label;
	}
}
