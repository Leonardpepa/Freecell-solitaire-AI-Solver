import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeSet;

public class State implements Comparable<State> {

	private ArrayList<Card> freecells;
	private ArrayList<Stack<Card>> foundations;
	private ArrayList<Stack<Card>> stacks;
	private HashMap<Card, String> pair;

	private String move;

	private String method;

	public int g;
	public int h;
	public int f;
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
		System.out.println("-------- State -----------");
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
		System.out.println("-------- End State ---------");
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}
		State s = (State) o;

		if (this == s) {
			return true;
		}

		if (this.freecells.size() != s.getFreecells().size()) {
			return false;
		}

		for (int i = 0; i < this.freecells.size(); i++) {
			if (!s.getFreecells().contains(this.freecells.get(i))) {
				return false;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (this.foundations.get(i).size() != s.getFoundations().get(i).size()) {
				return false;
			}

			for (Card c : this.foundations.get(i)) {
				if (!s.getFoundations().get(i).contains(c)) {
					return false;
				}
			}
		}

		for (int i = 0; i < 8; i++) {
			if (this.getStacks().get(i).size() != s.getStacks().get(i).size()) {
				return false;
			}

			if ((!this.stacks.get(i).isEmpty() && !s.getStacks().get(i).isEmpty())
					&& !this.stacks.get(i).peek().equals(s.getStacks().get(i).peek())) {
				return false;
			}
		}
		return true;

	}

	@Override
	public int compareTo(State otherState) {

		if (this.equals(otherState)) {
			return 0;
		}

		if (method.equals(MyUtils.BREADTH)) {

			if (this.g > otherState.getG())
				return 1;
			else if (this.g < otherState.getG())
				return -1;
			else {
				return 1;
			}

		} else if (method.equals(MyUtils.DEPTH)) {

			if (this.g > otherState.getG())
				return -1;
			else if (this.g < otherState.getG())
				return 1;
			else {
				return 1;
			}

		} else if (method.equals(MyUtils.BEST)) {
			if (this.h > otherState.getH())
				return 1;
			else if (this.h < otherState.getH())
				return -1;
			else {
				if (this.g < otherState.getG())
					return -1;
				return 1;
			}

		} else {
			if (this.f > otherState.getF())
				return 1;
			else if (this.f < otherState.getF())
				return -1;
			else {
				return 1;
			}

		}
	}

	@Override
	public State clone() {
		State state = new State();

		state.setMethod(this.getMethod());

		for (Card c : this.freecells) {
			state.getFreecells().add(c.clone());
			state.getPair().put(c, MyUtils.FREECELL);
		}

		for (int i = 0; i < 4; i++) {
			for (Card c : foundations.get(i)) {
				state.getFoundations().get(i).add(c.clone());
				state.getPair().put(c, MyUtils.FOUNDATION);
			}
		}

		for (int i = 0; i < 8; i++) {
			for (Card c : stacks.get(i)) {
				state.getStacks().get(i).add(c.clone());
				state.getPair().put(c, MyUtils.STACK);
			}
		}

		return state;
	}

	@Override
	public String toString() {
		return "State [freecells=" + freecells + ",\n foundations=" + foundations + ",\n stacks=" + stacks + ",\n move="
				+ move + ",\n g=" + g + ",\n h=" + h + ",\n f=" + f + ",\n parent=" + parent + "]";
	}

	public boolean moveCardToFreecell(Card card) {

		removeCardFromItsPosition(card);

		freecells.add(card);
		pair.put(card, MyUtils.FREECELL);
		return true;
	}

	// this method is called if stack you want to move your card in empty
	public boolean moveCardToStack(Card cardToMove, Stack<Card> stack) {

		removeCardFromItsPosition(cardToMove);

		stack.add(cardToMove);
		pair.put(cardToMove, MyUtils.STACK);
		return true;
	}

	public boolean moveCardToFoundation(Card cardToMove, Stack<Card> foundation) {

		removeCardFromItsPosition(cardToMove);

		foundation.add(cardToMove);
		pair.put(cardToMove, MyUtils.FOUNDATION);
		return true;
	}

	public boolean freecellRule(Card card) {
		return freecells.size() < 4 && !freecells.contains(card);
	}

	public boolean foundationRule(Card cardToMove, Stack<Card> foundation) {

		if (foundation.isEmpty() && cardToMove.getValue() == 0) {
			return true;
		}

		if (!foundation.isEmpty() && cardToMove.isLargerAndSameSuit(foundation.peek())) {
			return true;
		}

		return false;
	}

	public boolean stackRule(Card cardToMove, Stack<Card> stack) {

		if (stack.isEmpty()) {
			return true;
		}

		if (cardToMove.isSmallerAndDifferentColor(stack.peek())) {
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

	public void removeCardFromItsPosition(Card card) {

		
		if (pair.get(card).equalsIgnoreCase(MyUtils.FREECELL)) {

			freecells.remove(card);

		} else if (pair.get(card).equalsIgnoreCase(MyUtils.STACK)) {

			stacks.forEach(stack -> stack.remove(card));

		} else if (pair.get(card).equalsIgnoreCase(MyUtils.FOUNDATION)) {

			foundations.forEach(foundation -> foundation.remove(card));

		}

	}

	public TreeSet<State> getChildrenOfState(String method) {
		TreeSet<State> children = new TreeSet<State>();

		children.addAll(getMoveFromFoundationToOtherPosition(method));
		children.addAll(getMovesFromStackToOtherPosition(method));
		children.addAll(getMovesFromFreecellsToOtherPosition(method));
		return children;
	}

	public TreeSet<State> getMovesFromFreecellsToOtherPosition(String method) {

		TreeSet<State> children = new TreeSet<State>();

		if (this.freecells.isEmpty()) {
			return children;
		}

		HashMap<Card, Boolean> hasMoveToFreecell = new HashMap<Card, Boolean>();

		for (Card card : this.freecells) {

			Card c = card.clone();

			// check card move from freecell to foundation
			State toFoundationState = expandToFoundation(c, MyUtils.getFoundation(this, c.getSuit()));

			if (toFoundationState != null) {
				children.add(toFoundationState);

			}

			toFoundationState = null;

			// check card move from freecell to stack
			for (int i = 0; i < 8; i++) {

				if (this.stackRule(c, stacks.get(i))) {

					if (this.stacks.get(i).isEmpty() && hasMoveToFreecell.containsKey(c) && hasMoveToFreecell.get(c)) {
						continue;
					}

					State childrenState = this.clone();
					childrenState.moveCardToStack(c, childrenState.getStacks().get(i));

					childrenState.setParent(this);
					childrenState.setH(childrenState.heuristicFunction());
					childrenState.setF(method, childrenState);

					if (this.getStacks().get(i).isEmpty()) {
						childrenState.setMove(MyUtils.NEWSTACK + " " + c.toString());
						hasMoveToFreecell.put(c, true);
					} else {
						childrenState.setMove(
								MyUtils.STACK + " " + c.toString() + " " + this.getStacks().get(i).peek().toString());
					}
					children.add(childrenState);
					childrenState = null;

				}
			}

		}
		return children;
	}

	public TreeSet<State> getMovesFromStackToOtherPosition(String method) {
		TreeSet<State> children = new TreeSet<State>();

		HashMap<Card, Boolean> hasMoveToFreecell = new HashMap<Card, Boolean>();

		for (int i = 0; i < 8; i++) {

			if (this.stacks.get(i).isEmpty()) {
				continue;
			}

			Card cardToMove = this.stacks.get(i).peek().clone();

			State childrenState = null;

			// to foundation
			childrenState = expandToFoundation(cardToMove, MyUtils.getFoundation(this, cardToMove.getSuit()));
			if (childrenState != null) {
				children.add(childrenState);
			}
			childrenState = null;

			// from stack to another stack
			for (int j = 0; j < 8; j++) {

				if (i == j) {
					continue;
				}

				if (this.stacks.get(i).size() == 1 && this.stacks.get(j).isEmpty()) {
					continue;
				} else {

					if (this.stackRule(cardToMove, this.stacks.get(j))) {

						if (this.stacks.get(j).isEmpty() && hasMoveToFreecell.containsKey(cardToMove)
								&& hasMoveToFreecell.get(cardToMove)) {
							continue;
						}

						childrenState = this.clone();
						childrenState.moveCardToStack(cardToMove, childrenState.getStacks().get(j));

						childrenState.setParent(this);
						childrenState.setH(childrenState.heuristicFunction());
						childrenState.setF(method, childrenState);

						if (this.stacks.get(j).isEmpty()) {
							childrenState.setMove(MyUtils.NEWSTACK + " " + cardToMove.toString());
							hasMoveToFreecell.put(cardToMove, true);
						} else {
							childrenState.setMove(
									MyUtils.STACK + " " + cardToMove.toString() + " " + this.stacks.get(j).peek());
						}

						children.add(childrenState);
						childrenState = null;
					}

				}

			}

			// to freecell
			if (this.freecellRule(cardToMove)) {
				childrenState = this.clone();
				childrenState.moveCardToFreecell(cardToMove);

				childrenState.setParent(this);
				childrenState.setH(childrenState.heuristicFunction());
				childrenState.setF(method, childrenState);

				childrenState.setMove(MyUtils.FREECELL + " " + cardToMove.toString());
				children.add(childrenState);
				childrenState = null;
			}

		}

		return children;
	}

	public State expandToFoundation(Card cardToMove, Stack<Card> foundation) {
		if (this.foundationRule(cardToMove, foundation)) {
			State childrenState = this.clone();
			childrenState.moveCardToFoundation(cardToMove, MyUtils.getFoundation(childrenState, cardToMove.getSuit()));
			childrenState.setParent(this);
			childrenState.setH(childrenState.heuristicFunction());
			childrenState.setF(method, childrenState);
			childrenState.setMove(MyUtils.FOUNDATION + " " + cardToMove.toString());
			return childrenState;
		}
		return null;
	}

	public TreeSet<State> getMoveFromFoundationToOtherPosition(String method) {

		TreeSet<State> children = new TreeSet<State>();

		HashMap<Card, Boolean> hasMoveToFreecell = new HashMap<Card, Boolean>();

		for (int i = 0; i < 4; i++) {

			if (this.foundations.get(i).isEmpty()) {
				continue;
			}

			Card cardToMove = this.foundations.get(i).peek().clone();

			for (int j = 0; j < 8; j++) {

				if (this.stacks.get(j).isEmpty()) {
					continue;
				}

				if (this.stackRule(cardToMove, this.stacks.get(j))) {

					if (this.stacks.get(j).isEmpty() && hasMoveToFreecell.containsKey(cardToMove)
							&& hasMoveToFreecell.get(cardToMove)) {
						continue;
					}

					State childrenState = this.clone();
					childrenState.moveCardToStack(cardToMove, childrenState.getStacks().get(j));

					childrenState.setParent(this);
					childrenState.setH(childrenState.heuristicFunction());
					childrenState.setF(method, childrenState);
					if (this.stacks.get(j).isEmpty()) {
						childrenState.setMove(MyUtils.NEWSTACK + " " + cardToMove.toString());
						hasMoveToFreecell.put(cardToMove, true);
					} else {

						childrenState.setMove(MyUtils.STACK + " " + cardToMove.toString() + " "
								+ this.getStacks().get(j).peek().toString());
					}
					children.add(childrenState);
					childrenState = null;
				}
			}

		}

		return children;
	}

	public int heuristicFunction() {

		int cardsNotInFoundationScore = 0;
		int cardWrongOrderScore = 0;
		
		for (Card card : this.freecells) {
			cardsNotInFoundationScore += getWorthOfCardReversed(card);
		}

		for (Stack<Card> stack : this.stacks) {
			for (Card card : stack) {
				cardsNotInFoundationScore += getWorthOfCardReversed(card);
			}
		}

		for (Stack<Card> stack : this.stacks) {
			cardWrongOrderScore += wrongOrderOfCardsPenalty(stack);
		}

		return (int) Math.round(0.75 * cardsNotInFoundationScore + 0.25 * cardWrongOrderScore);
	}

	private int getWorthOfCardReversed(Card card) {
		return Math.abs(MyUtils.N - card.getValue());
	}

	private int wrongOrderOfCardsPenalty(Stack<Card> stack) {
		int score = 0;

		int difference = 0;

		Card previus = null;
		for (Card card : stack) {
			if (previus == null) {
				previus = card;
			} else {

				difference = previus.getValue() - card.getValue();

				if (previus.getColor().equals(card.getColor()) || difference != 1) {
					score++;
				}
			}
		}
		return score;
	}

	public void setF(String method, State state) {
		if (method.equals(MyUtils.BEST) || method.equals(MyUtils.ASTAR)) {
			setF(state.getH() + state.getG());
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
	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
		this.g = parent.getG() + 1;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
