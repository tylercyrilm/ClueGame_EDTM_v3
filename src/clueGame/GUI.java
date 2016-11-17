package clueGame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI extends JPanel{
	
	private Board board;
	private JButton nextPlayerButton;
	private JPanel whoseTurn;
	private JTextField turnText, rollText, guessText, resultText;
	
	public GUI(Board board) {
		this.board = board; 
		createLayout();
		setPreferredSize(new Dimension(400,200));
		
	}
	
	private void createLayout() {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new GridLayout(2,3));

		//WhoseTurn
		whoseTurn = new JPanel();
		whoseTurn.setLayout(new GridLayout(3,1));
		JLabel turnLabel = new JLabel("Whose turn?");
		//JTextField turnText = new  JTextField(20);
		whoseTurn.add(turnLabel);
		whoseTurn.add(updateTurnCount());
		
		//Die
		JPanel die = new JPanel();
		JLabel rollLabel = new JLabel("Roll");
		rollText = new  JTextField(5);
		rollText.setEditable(false);
		die.add(rollLabel);
		die.add(rollText);
		die.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		
		//Guess
		JPanel guess = new JPanel();
		JLabel guessLabel = new JLabel("Guess");
		guessText = new  JTextField(25);
		guessText.setEditable(false);
		guess.add(guessLabel);
		guess.add(guessText);
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Suggestion"));
		
		//GuessResult
		JPanel guessResult = new JPanel();
		JLabel resultLabel = new JLabel("Response");
		resultText = new  JTextField(15);
		resultText.setEditable(false);
		guessResult.add(resultLabel);
		guessResult.add(resultText);
		guessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));


		masterPanel.add(whoseTurn);
		//add buttons to the button panel
		nextPlayerButton = new JButton("Next player");
		nextPlayerButton.addActionListener(new PlayerListener());
		masterPanel.add(nextPlayerButton);
		
		JButton accusationButton = new JButton("Make an accusation");
		accusationButton.addActionListener(new AccusationListener());
		masterPanel.add(accusationButton);
		add(masterPanel);
		
		//add Labels to the button Panel
		masterPanel.add(die);
		masterPanel.add(guess);
		masterPanel.add(guessResult);		
	}

	private JTextField updateTurnCount() {
		turnText = new  JTextField(10);
		//turnText.setText(board.getCurrentPlayer().getName());
		turnText.setEditable(false);
		return turnText;
	}
	
	private class PlayerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!board.gameRunning) {
				JOptionPane.showMessageDialog(null, "The game is over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (board.player.getFinishState()) {
				JOptionPane.showMessageDialog(null, "You need to finish your turn! \nChoose a space to move to or make an accusation." , "Illegal", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				board.nextPlayer();
				turnText.setText(board.getCurrentPlayer().getName());
				Integer roll = board.rollDie();
				rollText.setText(Integer.toString(roll));
				board.takeTurn(roll);
				updateText();
			}
			
		}
	}
	
	public void updateText() {
		guessText.setText(board.suggestion.person + " in the " + board.suggestion.room + " with the " + board.suggestion.weapon);
		resultText.setText(board.proveSuggestionFalse);
	}
	
	

	private class AccusationListener implements ActionListener {
		accusationDialog guess;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!board.gameRunning) {
				JOptionPane.showMessageDialog(null, board.getWinner() + " correctly accused " + board.getSolution().person +" in the "+board.getSolution().room+" with the "+board.getSolution().weapon+"." , "Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (board.getCurrentPlayer().getFinishState()) {
				guess = new accusationDialog(board, "Accuse");
				guess.setVisible(true);		
			}
			else {
					JOptionPane.showMessageDialog(null, "It's not your turn!" , "You scamp", JOptionPane.INFORMATION_MESSAGE);
			}
			
			board.getCurrentPlayer().turnFinished();
		}
		
	}
}
