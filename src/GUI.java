import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame{
	public GUI() {
		setTitle("CLUE");
		setSize(1000, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout();
	}
	
	private void createLayout() {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new GridLayout(2,3));

		//WhoseTurn
		JPanel whoseTurn = new JPanel();
		whoseTurn.setLayout(new GridLayout(3,1));
		JLabel turnLabel = new JLabel("Whose turn?");
		JTextField turnText = new  JTextField(50);
		whoseTurn.add(turnLabel);
		whoseTurn.add(turnText);
		
		//Die
		JPanel die = new JPanel();
		JLabel rollLabel = new JLabel("Roll");
		JTextField rollText = new  JTextField(10);
		die.add(rollLabel);
		die.add(rollText);
		die.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		
		//Guess
		JPanel guess = new JPanel();
		//guess.setLayout(new GridLayout(2,1));
		JLabel guessLabel = new JLabel("Guess");
		JTextField guessText = new  JTextField(20);
		guess.add(guessLabel);
		guess.add(guessText);
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		
		//GuessResult
		JPanel guessResult = new JPanel();
		//guessResult.setLayout(new GridLayout(2, 1));
		JLabel resultLabel = new JLabel("Response");
		JTextField resultText = new  JTextField(20);
		guessResult.add(resultLabel);
		guessResult.add(resultText);
		guessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));

		
		//add buttons to the button panel
		JButton nextPlayerButton = new JButton("Next player");
		masterPanel.add(nextPlayerButton);
		JButton accusationButton = new JButton("Make an accusation");
		masterPanel.add(accusationButton);
		add(masterPanel);
		
		//add Labels to the button Panel
		masterPanel.add(whoseTurn);
		masterPanel.add(die);
		masterPanel.add(guess);
		masterPanel.add(guessResult);
		
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
		
	}
	

}
