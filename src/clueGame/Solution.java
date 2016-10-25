package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	public int accuserId;
	
	public Solution(String person, String room, String weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public Solution() {
		super();
	}
	
	public void setAccuserId(int i) {
		accuserId = i;
	}
}
