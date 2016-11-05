package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{

	//private JCheckBox person1;
	
	public DetectiveNotes() {
		setTitle("Detective Notes");
		setSize(400,600);
		
		setLayout(new GridLayout(3,2));
		JPanel panel = createPeople();
		add(panel);
		panel = createPersonGuess();
		add(panel);
		panel = createRooms();
		add(panel);
		panel = createRoomGuess();
		add(panel);
		panel = createWeapon();
		add(panel);
		panel = createWeaponGuess();
		add(panel);
		
	}

	private JPanel createPeople() {
		JPanel panel = new JPanel();
		
		JCheckBox person1 = new JCheckBox ("First Person");
		panel.add(person1);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		return panel;
	}
	
	private JPanel createPersonGuess() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		return panel;
	}
	

	private JPanel createRooms() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		return panel;
	}
	
	 private JPanel createRoomGuess() {
		 	JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
			return panel;
	}
	 
	 private JPanel createWeapon() {
		 	JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			return panel;
	}

	 
	private JPanel createWeaponGuess() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel;
	}




	
	
	
	
}
