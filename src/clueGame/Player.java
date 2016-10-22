package clueGame;

import com.sun.prism.paint.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;	//CHANGE BACK TO A COLOR OBJECT
	
	public Card disproveSuggestion(Solution suggestion) {
		Card testCard = new Card();
		
		return testCard;
	}
	
	public String getName() {
		return playerName;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setName(String name) {
		playerName = name;
	}
	
	public void setColor(Color c) {
		color = c; 
	}
	
	public void setRow(int i) {
		row = i;
	}
	
	public void setColumn(int col) {
		column = col;
	}
}
