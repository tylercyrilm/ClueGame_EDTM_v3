package experiment;

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
	
	private boolean isDoorway(){
		return false;
	}
	
	private DoorDirection getDoorDirection(){
		return NONE;
	}
}
