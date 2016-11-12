package clueGame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI extends JPanel{
	
	private Board board;
	JButton nextPlayerButton;
	JPanel whoseTurn;
	JTextField turnText;
	JTextField rollText;
	JTextField guessText;
	
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
		//guess.setLayout(new GridLayout(2,1));
		JLabel guessLabel = new JLabel("Guess");
		guessText = new  JTextField(25);
		guessText.setEditable(false);
		guess.add(guessLabel);
		guess.add(guessText);
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Suggestion"));
		
		//GuessResult
		JPanel guessResult = new JPanel();
		//guessResult.setLayout(new GridLayout(2, 1));
		JLabel resultLabel = new JLabel("Response");
		JTextField resultText = new  JTextField(15);
		guessResult.add(resultLabel);
		guessResult.add(resultText);
		guessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));


		masterPanel.add(whoseTurn);
		//add buttons to the button panel
		nextPlayerButton = new JButton("Next player");
		nextPlayerButton.addActionListener(new ButtonListener());
		masterPanel.add(nextPlayerButton);
		JButton accusationButton = new JButton("Make an accusation");
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
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (board.player.getFinishState()) {
				
			}
			else {
				board.nextPlayer();
				turnText.setText(board.getCurrentPlayer().getName());
				Integer roll = board.rollDie();
				rollText.setText(Integer.toString(roll));
				board.takeTurn(roll);
				guessText.setText(board.suggestion.person + " in the " + board.suggestion.room + " with the " + board.suggestion.weapon);
			}
		}
		
	}

}