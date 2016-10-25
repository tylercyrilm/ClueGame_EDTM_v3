package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import clueGame.Board;
import java.util.Set;
import java.awt.Color;

public class Player {
	private String playerName;
	protected int row;
	protected int column;
	private Color color;	//CHANGE BACK TO A COLOR OBJECT
	public Set<Card> hand = new HashSet<Card>();
	public int id;
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> inHand = new ArrayList<Card>();
		for (Card c : hand) {
			if (c.getName().equals(suggestion.person) || c.getName().equals(suggestion.weapon) || c.getName().equals(suggestion.room)) {
				inHand.add(c);
			}
		}
		
		Random rand = new Random();
		if (inHand.size() != 0) {
			return inHand.get(rand.nextInt(inHand.size()));
		}
		else {
			return null;
		}
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
	
	public void setLocation(int r, int col) {
		row = r;
		column = col;
	}
}
