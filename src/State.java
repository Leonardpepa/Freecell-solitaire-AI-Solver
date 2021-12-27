import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Logger;

public class State {

	private ArrayList<Card> freecells;
	private ArrayList<Stack<Card>> foundations;
	private ArrayList<Stack<Card>> hand;

	public State() {
		freecells = new ArrayList<>(4);
		foundations = new ArrayList<>(4);
		hand = new ArrayList<>(8);

		for (int i = 0; i < 4; i++) {
			foundations.add(new Stack<>());
			hand.add(new Stack<>());
			hand.add(new Stack<>());
		}

	}

	public void printState() {
		System.out.println("freecells: " + freecells);
		System.out.println("*******************************");
		System.out.print("foundation: ");
		for (Stack<Card> stack : foundations) {
			System.out.println(stack);
		}
		System.out.println("*******************************");
		System.out.println("hand: ");
		for (Stack<Card> stack : hand) {
			System.out.println(stack);
		}
	}

	public boolean equals(Object o) {
		State s = (State) o;

		if (this == o) {
			return true;
		}

		if (this.freecells.size() != s.getFreecells().size()) {
			return false;
		}

		for (int i = 0; i < this.freecells.size(); i++) {
			if (!this.freecells.get(i).equals(s.getFreecells().get(i))) {
				return false;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (this.foundations.get(i).size() != s.getFoundation().get(i).size()) {
				return false;
			}
			for (int j = 0; j < this.foundations.get(i).size(); i++) {
				if (!this.foundations.get(i).get(j).equals(s.getFoundation().get(i).get(j))) {
					return false;
				}
			}
		}

		for (int i = 0; i < 8; i++) {

			if (this.hand.get(i).size() != s.getHand().get(i).size()) {
				return false;
			}
			for (int j = 0; j < this.hand.get(i).size(); j++) {
				if (!this.hand.get(i).get(j).equals(s.getHand().get(i).get(j))) {
					return false;
				}
			}
		}

		return true;
	}

	public void moveCardToFreecell(Card card) {
		if (freecells.size() < 4) {
			freecells.add(card);
		} else {
			Logger.getGlobal().warning("YOU DONT HAVE ENOUGH FREECELLS");
		}
	}

	public void moveCardFromFreeCellToHand(int freecellIdx, int handIdx) {

		if (freecells.get(freecellIdx).getValue() >= hand.get(handIdx).peek().getValue()
				|| freecells.get(freecellIdx).getColor().equals(hand.get(handIdx).peek().getColor())) {
			Logger.getGlobal().warning("Invalid Move the card should be differend color and smaller by 1");
			return;
		}

		hand.get(handIdx).add(freecells.remove(freecellIdx));
	}

	public void moveCardFromHandToFoundation(int handIdx, int foundationIdx) {
		if (foundations.get(foundationIdx).size() == 0 && hand.get(handIdx).peek().getValue() != 0) {
			Logger.getGlobal().warning("Only A can be at the bottom of a foundation");
			return;
		}

		if (foundations.get(foundationIdx).size() == 0 && hand.get(handIdx).peek().getValue() == 0) {
			foundations.get(foundationIdx).add(hand.get(handIdx).pop());
			return;
		}

		if (hand.get(handIdx).peek().getValue() >= foundations.get(foundationIdx).peek().getValue()
				|| hand.get(handIdx).peek().getSuit() != foundations.get(foundationIdx).peek().getSuit()) {
			Logger.getGlobal().warning(
					"Invalid Move the card should be same suit and smaller by 1 to be placed in the foundation");
			return;
		}
		foundations.get(foundationIdx).add(hand.get(handIdx).pop());

	}

	public void moveCardFromFreecellToFoundation(int freecellIdx, int foundationIdx) {

		if (foundations.get(foundationIdx).size() == 0 && freecells.get(freecellIdx).getValue() != 0) {
			Logger.getGlobal().warning("Only A can be at the bottom of a foundation");
			return;
		}

		if (foundations.get(foundationIdx).size() == 0 && freecells.get(freecellIdx).getValue() == 0) {
			foundations.get(foundationIdx).add(freecells.remove(freecellIdx));
			return;
		}

		if (freecells.get(freecellIdx).getValue() >= foundations.get(foundationIdx).peek().getValue()
				|| freecells.get(freecellIdx).getSuit() != foundations.get(foundationIdx).peek().getSuit()) {
			Logger.getGlobal().warning(
					"Invalid Move the card should be same suit and smaller by 1 to be placed in the foundation");
			return;
		}
		
		foundations.get(foundationIdx).add(freecells.remove(freecellIdx));

	}

	public void moveCardFromHandToHand(int beginHand, int endHand) {

		if (hand.get(endHand).size() == 0) {
			hand.get(endHand).add(hand.get(beginHand).pop());
			return;
		}

		if (hand.get(beginHand).peek().getValue() >= hand.get(endHand).peek().getValue()
				|| hand.get(beginHand).peek().getColor().equals(hand.get(endHand).peek().getColor())) {
			Logger.getGlobal().warning("Invalid Move the card should be differend color and smaller by 1");
			return;
		}

		hand.get(endHand).add(hand.get(beginHand).pop());

	}

	public ArrayList<Card> getFreecells() {
		return freecells;
	}

	public void setFreecells(ArrayList<Card> freecells) {
		this.freecells = freecells;
	}

	public ArrayList<Stack<Card>> getFoundation() {
		return foundations;
	}

	public void setFoundation(ArrayList<Stack<Card>> foundation) {
		this.foundations = foundation;
	}

	public ArrayList<Stack<Card>> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Stack<Card>> hand) {
		this.hand = hand;
	}

}
