package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	public int row;
	public int column;
	public char initial;
	public DoorDirection direction = DoorDirection.NONE;
	private int cellDim = 30; 
	private int doorWidth = 10; 
	public boolean writesName = false;
	
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
			g.setColor(Color.YELLOW);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
			g.setColor(Color.PINK);
			g.drawRect(column*cellDim, row*cellDim, cellDim, cellDim);
		}
		else if (isDoorway()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);	
			switch (direction){
				case UP: {
					g.setColor(Color.BLUE);
					g.fillRect(column*cellDim, row*cellDim, cellDim, doorWidth);				
					break;	
				}
				case DOWN: {
					g.setColor(Color.BLUE);
					g.fillRect(column*cellDim, (row+1)*cellDim - doorWidth, cellDim, doorWidth);
					break;	
				}
				case LEFT: {
					g.setColor(Color.BLUE);
					g.fillRect(column*cellDim, row*cellDim, doorWidth, cellDim);
					break;	
				}
				case RIGHT: {
					g.setColor(Color.BLUE);
					g.fillRect((column+1)*cellDim - doorWidth, row*cellDim, doorWidth, cellDim);
					break;	
				}
				default: {
					g.setColor(Color.BLUE);
					g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
				}
			
			}
		}
		else if (initial == 'X') {
			g.setColor(Color.RED);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
		}
		else {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column*cellDim, row*cellDim, cellDim, cellDim);
		}
		if(writesName) {
			g.drawString("Name of Room", column*cellDim, row*cellDim);
		}
	}
}
