package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;



public class ClueGame extends JFrame   {
	private static Board board;
	private DetectiveNotes notes;
	//private Graphics g;
	
	public ClueGame() {

		board = Board.getInstance();
		board.setConfigFiles("data/ENTM_ClueMap.csv", "data/ENTM_Legend.txt");
		board.setWPConfigFiles("data/ENTM_CluePlayers.txt", "data/ENTM_ClueWeapons.txt");
		board.initialize();
		board.deal();
		
		setSize(board.getNumColumns()*40, (board.getNumRows()+1)*50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(board, BorderLayout.CENTER);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		ShowCardPanel sc = new ShowCardPanel(board.player.hand);
		add(sc, BorderLayout.EAST);
		
		GUI gui = new GUI(board);
		add(gui, BorderLayout.SOUTH);
		
		
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createDetectiveNotesItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	

	private JMenuItem createDetectiveNotesItem() {
		JMenuItem item = new JMenuItem("Show Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				notes = new DetectiveNotes(board);
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	public static void main(String[] args) {
		ClueGame window = new ClueGame();
		window.setVisible(true);
		JOptionPane.showMessageDialog(window, "You are " + board.player.getName() + ", press next player to begin play." , "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
	}


}
