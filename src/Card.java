import java.util.Objects;
/**
 * 
 * @author Leonard Pepa ICS20033
 *
 */

// CARD CLASS

// Holds the information need to a card

public class Card {
	private char suit;
	private int value;
	private String color;

	// Constructor
	// based on the suit it calculates the colour
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

	// method to check if a this card is smaller by 1 and different colour from
	// other card
	// this method is called when we want to move a card to a stack
	public boolean isSmallerAndDifferentColor(Card card) {
		return ((this.getValue() + 1) == card.getValue()) && !this.color.equalsIgnoreCase(card.getColor());
	}

	// method to check if a this card is bigger by 1 and same suit from other card
	// this method is called when we want to move a card to a foundation
	public boolean isLargerAndSameSuit(Card card) {
		return (this.value - 1) == card.getValue() && this.suit == card.getSuit();
	}

	// creates a copy of the card
	@Override
	public Card clone() {
		return new Card(this.suit, this.value);
	}

	// generates a unique hash for each card
	@Override
	public int hashCode() {
		return Objects.hash(color, suit, value);
	}

	// check for card equality
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

	// return the card as a string
	public String toString() {
		return suit + String.valueOf(value);
	}

	// getters and setters
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
