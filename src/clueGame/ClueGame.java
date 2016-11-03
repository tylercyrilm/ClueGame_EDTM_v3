package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;



public class ClueGame extends JFrame {
	private static Board board;
	private Graphics g;
	
	public ClueGame() {

		board = Board.getInstance();
		board.setConfigFiles("data/ENTM_ClueMap.csv", "data/ENTM_Legend.txt");
		board.setWPConfigFiles("data/ENTM_CluePlayers.txt", "data/ENTM_ClueWeapons.txt");
		board.initialize();
		
		setSize(board.getNumColumns()*30, board.getNumRows()*30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(board, BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		ClueGame window = new ClueGame();
		window.setVisible(true);
	}
}
