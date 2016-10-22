package clueGame;

import com.sun.prism.paint.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
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
}
