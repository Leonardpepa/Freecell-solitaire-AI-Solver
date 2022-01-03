import java.util.Objects;

public class Card {
	private char suit;
	private int value;
	private String color;
	private String isLocated;

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
				break;
			default:
				color = "error";
		}

	}

	public Card(char suit, int value, String isLocated) {
		this.suit = suit;
		this.value = value;
		this.isLocated = isLocated;
		switch (suit) {
			case 'S':
			case 'C':
				color = "black";
				break;
			case 'H':
			case 'D':
				color = "red";
				break;
			default:
				color = "error";
		}

	}

	public boolean isSmallerAndDifferentColor(Card card) {
		return ((this.getValue() + 1) == card.getValue()) && !this.color.equalsIgnoreCase(card.getColor());
	}

	public boolean isLargerAndSameSuit(Card card) {
		return (this.value - 1) == card.getValue() && this.suit == card.getSuit();
	}

	@Override
	public Card clone() {
		return new Card(this.suit, this.value, this.isLocated);
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, suit, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return Objects.equals(color, other.color) && suit == other.suit && value == other.value;
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

	public String getIsLocated() {
		return isLocated;
	}

	public void setIsLocated(String isLocated) {
		this.isLocated = isLocated;
	}

}
