package clueGame;

public class BoardCell {
	public int row;
	public int column;
	public String initial;
	public DoorDirection direction = DoorDirection.NONE;
	
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
	
	public boolean isDoorway(BoardCell cell){
		if (direction == DoorDirection.NONE) return false;
		else{
			return true;
		}
	}
	
	public DoorDirection getDoorDirection(){
		return DoorDirection.NONE;
	}
	
	public Object getInitial() {
		return initial; 
	}
}
