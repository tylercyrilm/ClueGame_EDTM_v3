package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	public int row;
	public int column;
	public char initial;
	public DoorDirection direction = DoorDirection.NONE;
	private int cellDim = 30; 
	
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
		if (this.direction == DoorDirection.NONE){
			return false;
		}
		return true;
	}
	public boolean isWalkway() {
		if (initial == 'W') {
			return true;
		}
		else {
			return false;
		}
	}
	public DoorDirection getDoorDirection(){
		return direction;
	}
	
	public char getInitial() {
		return initial; 
	}
	
	public void draw(Graphics g) {
		if (isWalkway()) {
			g.setColor(Color.PINK);
			g.drawRect(column*cellDim, row*cellDim, cellDim, cellDim);
			g.setColor(Color.YELLOW);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
		}
		else if (isDoorway()) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
		}
		else if (initial == 'X') {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
		}
		else {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
		}
	}
}
