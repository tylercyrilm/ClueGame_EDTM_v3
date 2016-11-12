package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ShowCardPanel extends JPanel{

	public ShowCardPanel(Set<Card> playerHand) {
		setLayout(new GridLayout(3,1));
		add(createPeople(playerHand));
		add(createRoom(playerHand));
		add(createWeapon(playerHand));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		setPreferredSize(new Dimension(100,300));
	}
	
	private JPanel createPeople(Set<Card> ph) {
		JPanel people = new JPanel();
		for(Card c: ph) {
			if(c.getType() == CardType.PERSON) {
				JTextField currentText = new JTextField(c.getName());
				currentText.setEditable(false);
				people.add(currentText);
			}
		}
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return people;
	}

	private JPanel createWeapon(Set<Card> ph) {
		JPanel weapon = new JPanel();
		for(Card c: ph) {
			if(c.getType() == CardType.WEAPON) {
				JTextField currentText = new JTextField(c.getName());
				currentText.setEditable(false);
				weapon.add(currentText);
			}
		}
		weapon.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		return weapon;
	}
	
	private JPanel createRoom(Set<Card> ph) {
		JPanel room = new JPanel();
		for(Card c: ph) {
			if(c.getType() == CardType.ROOM) {
				JTextField currentText = new JTextField(c.getName());
				currentText.setEditable(false);
				room.add(currentText);
			}
		}
		room.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return room;
	}
}
