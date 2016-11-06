package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{

	//private JCheckBox person1;
	private Board board; 
	//private JComboBox<String> rooms, people, weapons;
	
	public DetectiveNotes(Board board) {
		this.board = board;
		setTitle("Detective Notes");
		setSize(400,700);
		
		setLayout(new GridLayout(3,2));
		add(createPeople());
		add(createPersonGuess());
		add(createRooms());
		add(createRoomGuess());
		add(createWeapon());
		add(createWeaponGuess());
		
	}

	private JPanel createPeople() {
		JPanel panel = new JPanel();
		for (Card c: board.personCards) {
			JCheckBox person = new JCheckBox (c.getName());
			panel.add(person);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		return panel;
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
	


	private JPanel createRooms() {
		JPanel panel = new JPanel();
		for (Card c: board.roomCards) {
			JCheckBox room = new JCheckBox (c.getName());
			panel.add(room);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
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
	 
	 private JPanel createWeapon() {
		 	JPanel panel = new JPanel();
		 	for (Card c: board.weaponCards) {
				JCheckBox weapon = new JCheckBox (c.getName());
				panel.add(weapon);
			}
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
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
