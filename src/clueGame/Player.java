package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import clueGame.Board;
import java.util.Set;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Player {
	private String playerName;
	protected int row;
	protected int column;
	private Color color;	
	public Set<Card> hand = new HashSet<Card>();
	public int id;
	private static final int cellDim = 25;
	private static final int playerDim = cellDim - 2;
	protected boolean mustFinish = false;
	
	public boolean getFinishState() {
		return mustFinish;
	}
	public void turnFinished() {
		mustFinish = false;
	}
	
	public abstract void makeMove(Set<BoardCell> targets);
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> inHand = new ArrayList<Card>();
		for (Card c : hand) {
			if (c.getName().equals(suggestion.person) || c.getName().equals(suggestion.weapon) || c.getName().equals(suggestion.room)) {
				inHand.add(c);
			}
		}
		
		Random rand = new Random();
		if (!inHand.isEmpty()) {
			return inHand.get(rand.nextInt(inHand.size()));
		}
		else {
			return null;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column*cellDim+1, row*cellDim+1, playerDim, playerDim);
		g.setColor(Color.BLACK);
		g.drawOval(column*cellDim+1, row*cellDim+1, playerDim, playerDim);
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
	
	public void setRow(int r) {
		row = r;
	}
	
	public void setColumn(int col) {
		column = col;
	}
	
	public void setLocation(int r, int col) {
		row = r;
		column = col;
	}
	
}
