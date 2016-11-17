package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class accusationDialog extends JDialog {
	Board board;
	String name;
	
	JComboBox<String> people, rooms, weapons;
	JButton finish;
	
	private String person, room, weapon;
	private boolean isSuggestion;

	
	accusationDialog(Board board, String name) {

		
		this.board = board;
		this.name = name;
		
		if (name.equals("Suggest")) {
			isSuggestion = true;
		}
		else
			isSuggestion = false;
		
		
		setTitle(name);
		setSize(400,700);
		
		setLayout(new GridLayout(4,1));
		add(createPersonGuess());
		add(createRoomGuess());
		add(createWeaponGuess());
		add(finishButton());
	}
	
	private JPanel createPersonGuess() {
		JPanel panel = new JPanel();
		people = new JComboBox<String>();
	 	for (Card c: board.personCards) {		
			people.addItem(c.getName());
		}
	 	people.addActionListener(new comboListener());
	 	panel.add(people);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		return panel;
	}
	
	private JPanel createRoomGuess() {
	 	JPanel panel = new JPanel();
	 	rooms = new JComboBox<String>();
	 	if (isSuggestion) {
	 		rooms.addItem(board.player.getLocationType(board));
	 		board.suggestion.room = board.player.getLocationType(board);
	 	}
	 	else {
	 		for (Card c: board.roomCards) {		
	 			rooms.addItem(c.getName());
	 		}
	 	}
	 	rooms.addActionListener(new comboListener());
	 	panel.add(rooms);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		return panel;
	}
	
	private JPanel createWeaponGuess() {
		JPanel panel = new JPanel();
		weapons = new JComboBox<String>();
	 	for (Card c: board.weaponCards) {		
			weapons.addItem(c.getName());
		}
	 	weapons.addActionListener(new comboListener());
	 	panel.add(weapons);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel;
	}
	
	private JButton finishButton() {
		finish = new JButton(name);
		finish.addActionListener(new finishListener());
		return finish;
	}
	
	private class comboListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == people) {
				person = people.getSelectedItem().toString();
			}
			else if (e.getSource() == weapons) {
				weapon = weapons.getSelectedItem().toString();
			}
			else {
				room = rooms.getSelectedItem().toString();
			}
		}
		
	}
	
	private class finishListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Solution s = new Solution(person, room, weapon);
			board.suggestion = s;
			if (isSuggestion) {
				board.handleSuggestion(s);
				for (Player a: board.comp) {
					if (a.getName().equals(board.suggestion.person)) {
						a.setLocation(board.player.getRow(), board.player.getColumn());
						break;
					}
				}
				board.repaint();
			}
			else {
				if(board.checkAccusation(s)) {
					JOptionPane.showMessageDialog(null, "Your accusation is correct! You win the game!" , "Winner!", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Your accusation is incorrect." , "False Accusation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			setVisible(false);
		}
		
	}
}
