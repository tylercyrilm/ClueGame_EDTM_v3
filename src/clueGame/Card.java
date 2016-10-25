package clueGame;

public class Card {
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]";
	}

	private String cardName;
	private CardType type;
	
	public boolean equals(){
		
		return false;
	}
	
	public String getName() {
		return cardName;
	}

	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
	public Card() {
		super();
	}
}
