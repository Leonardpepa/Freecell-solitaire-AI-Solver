import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class State {

	private ArrayList<Card> freecells;
	private ArrayList<Stack<Card>> foundations;
	private ArrayList<Stack<Card>> stacks;
	private HashMap<Card, String> pair;

	public float g;
	public float h;
	public float f;
	private State parent;

	public State() {
		freecells = new ArrayList<>(4);
		foundations = new ArrayList<>(4);
		stacks = new ArrayList<>(8);
		pair = new HashMap<>();
		for (int i = 0; i < 4; i++) {
			foundations.add(new Stack<>());
			stacks.add(new Stack<>());
			stacks.add(new Stack<>());
		}

	}

	public void printState() {
		System.out.println("freecells: " + freecells);
		System.out.println();
		System.out.print("foundation: ");
		for (Stack<Card> stack : foundations) {
			System.out.println(stack);
		}
		System.out.println();
		System.out.println("stacks: ");
		for (Stack<Card> stack : stacks) {
			System.out.println(stack);
		}
	}

	@Override
	public State clone() {
		State state = new State();

		for (Card c : this.freecells) {
			state.getFreecells().add(c.clone());
		}

		for (int i = 0; i < 4; i++) {
			for (Card c : foundations.get(i)) {
				state.getFoundations().get(i).add(c.clone());
			}
		}

		for (int i = 0; i < 8; i++) {
			for (Card c : stacks.get(i)) {
				state.getStacks().get(i).add(c.clone());
			}
		}
		return state;
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
			if (this.foundations.get(i).size() != s.getFoundations().get(i).size()) {
				return false;
			}
			for (int j = 0; j < this.foundations.get(i).size(); i++) {
				if (!this.foundations.get(i).get(j).equals(s.getFoundations().get(i).get(j))) {
					return false;
				}
			}
		}

		for (int i = 0; i < 8; i++) {

			if (this.stacks.get(i).size() != s.getStacks().get(i).size()) {
				return false;
			}
			for (int j = 0; j < this.stacks.get(i).size(); j++) {
				if (!this.stacks.get(i).get(j).equals(s.getStacks().get(i).get(j))) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean moveCardToFreecell(Card card) {

		removeCard(card);

		freecells.add(card);
		pair.put(card, "freecell");
		return true;
	}

	// this method is called if stack you want to move your card in empty
	public boolean moveCardToStack(Card cardToMove, Stack<Card> stack) {

		removeCard(cardToMove);
		
		stack.add(cardToMove);
		pair.put(cardToMove, "stack");
		return true;
	}

	public boolean moveCardToFoundation(Card cardToMove, Stack<Card> foundation) {

		removeCard(cardToMove);
		
		foundation.add(cardToMove);
		pair.put(cardToMove, "foundation");
		return true;
	}

	public boolean canMoveToFreecell(Card card) {
		return freecells.size() < 4 && !freecells.contains(card);
	}

	public boolean canMoveToFoundation(Card cardToMove, Stack<Card> foundation) {

		if (foundation.isEmpty() && cardToMove.getValue() == 0) {
			return true;
		}

		if (!foundation.isEmpty() && cardToMove.isLargerAndSameSuit(foundation.peek())) {
			return true;
		}

		return false;
	}

	public boolean canMoveToStack(Card cardToMove, Card cardOnStack) {

		if (cardToMove.isSmallerAndDifferentColor(cardOnStack)) {
			return true;
		}
		return false;
	}

	public boolean isSolved() {

		if (!freecells.isEmpty()) {
			return false;
		}

		for (int i = 0; i < 8; i++) {
			if (!stacks.get(i).isEmpty()) {
				return false;
			}
		}

		for (int i = 0; i < 4; i++) {
			Card previus = null;
			for (Card c : foundations.get(i)) {
				if (previus == null) {
					previus = c;
				} else {
					if (previus.getSuit() != c.getSuit() || previus.getValue() >= c.getValue()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public void removeCard(Card card) {
		if (pair.get(card).equals("freecell")) {
			freecells.remove(card);
		} else if (pair.get(card).equals("stack")) {
			stacks.forEach(stack -> stack.remove(card));
		} else if (pair.get(card).equals("foundation")) {
			foundations.forEach(stack -> stack.remove(card));
		}
	}

	public ArrayList<Card> getFreecells() {
		return freecells;
	}

	public void setFreecells(ArrayList<Card> freecells) {
		this.freecells = freecells;
	}

	public ArrayList<Stack<Card>> getStacks() {
		return stacks;
	}

	public void setStacks(ArrayList<Stack<Card>> stacks) {
		this.stacks = stacks;
	}

	public ArrayList<Stack<Card>> getFoundations() {
		return foundations;
	}

	public void setFoundations(ArrayList<Stack<Card>> foundations) {
		this.foundations = foundations;
	}

	public HashMap<Card, String> getPair() {
		return pair;
	}

	public void setPair(HashMap<Card, String> pair) {
		this.pair = pair;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

}
