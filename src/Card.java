
public class Card {
	private char suit;
	private int value;
	private String color;

	public Card(char suit, int value) {
		this.suit = suit;
		this.value = value;

		switch (suit) {
		case 'S':
		case 'C':
			color = "black";
			break;
		case 'H':
		case 'D':
			color = "red";
		default:
			color = "error";
		}

	}

	public boolean equals(Object o) {
		Card c = (Card) o;

		if (this == o) {
			return true;
		}

		if (this.suit == c.getSuit() && this.value == c.getValue()) {
			return true;
		}

		return false;

	}

	public String toString() {
		return suit + String.valueOf(value);
	}

	public char getSuit() {
		return suit;
	}

	public void setSuit(char suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
