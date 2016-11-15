package clueGame;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class accusationDialog extends JDialog {
	Board board;
	accusationDialog(Board board, String name) {
		this.board = board;
		setTitle(name);
		setSize(400,700);
		
		setLayout(new GridLayout(3,1));
		add(createPersonGuess());
		add(createRoomGuess());
		add(createWeaponGuess());
	}
	
	private JPanel createPersonGuess() {
		JPanel panel = new JPanel();
		JComboBox<String> people = new JComboBox<String>();
	 	for (Card c: board.personCards) {		
			people.addItem(c.getName());
		}
	 	panel.add(people);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		return panel;
	}
	
	private JPanel createRoomGuess() {
	 	JPanel panel = new JPanel();
	 	JComboBox<String> rooms = new JComboBox<String>();
	 	for (Card c: board.roomCards) {		
			rooms.addItem(c.getName());
		}
	 	panel.add(rooms);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		return panel;
	}
	
	private JPanel createWeaponGuess() {
		JPanel panel = new JPanel();
		JComboBox<String> weapons = new JComboBox<String>();
	 	for (Card c: board.weaponCards) {		
			weapons.addItem(c.getName());
		}
	 	panel.add(weapons);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel;
	}
}
