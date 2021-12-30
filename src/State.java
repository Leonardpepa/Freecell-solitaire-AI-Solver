import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class State {

	private ArrayList<Card> freecells;
	private ArrayList<Stack<Card>> foundations;
	private ArrayList<Stack<Card>> stacks;
	private HashMap<Card, String> pair;

	private String move;

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
	public State clone() {
		State state = new State();

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

	public boolean canMoveToStack(Card cardToMove, Stack<Card> stack) {

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

	public Frontier getChildrenOfState(String method) {
		Frontier children = new Frontier();

		children.addAll(getMovesFromStackToOtherPosition(method));
		children.addAll(getMovesFromFreecellsToOtherPosition(method));

		return children;
	}

	public Frontier getMovesFromFreecellsToOtherPosition(String method) {

		Frontier children = new Frontier();

		for (Card card : this.freecells) {
			
			Card c = card.clone();
			// check card move from freecell to foundation
			for (int i = 0; i < 4; i++) {
				if (this.canMoveToFoundation(c, foundations.get(i))) {
					State childrenState = this.clone();
					childrenState.moveCardToFoundation(c, childrenState.getFoundations().get(i));
					childrenState.setParent(this);
					childrenState.setMove(MyUtils.FOUNDATION + " " + c.toString());
					if (childrenState.alreadyInHistory()) {
						childrenState = null;
						continue;
					} else {
						children.add(childrenState);
						return children;
					}
				}
			}

			// check card move from freecell to stack
			for (int i = 0; i < 8; i++) {
				if (this.canMoveToStack(c, stacks.get(i))) {
					State childrenState = this.clone();
					childrenState.moveCardToStack(c, childrenState.getStacks().get(i));
					childrenState.setParent(this);
					if (this.getStacks().get(i).isEmpty()) {
						childrenState.setMove(MyUtils.NEWSTACK + " " + c.toString());
					} else {
						childrenState.setMove(
								MyUtils.STACK + " " + c.toString() + " " + this.getStacks().get(i).peek().toString());
					}
					if (childrenState.alreadyInHistory()) {
						childrenState = null;
						continue;
					} else {
						children.add(childrenState);
					}
				}
			}

		}
		return children;
	}

	public Frontier getMovesFromStackToOtherPosition(String method) {
		Frontier children = new Frontier();

		for (int i = 0; i < 8; i++) {

			if (this.stacks.get(i).isEmpty()) {
				continue;
			}

			Card cardToMove = this.stacks.get(i).peek().clone();

			// to foundation
			switch (cardToMove.getSuit()) {
			case 'D':
				if (expandToFoundation(children, cardToMove, MyUtils.DIAMONDS)) {
					return children;
				}
				;
				break;
			case 'S':
				if (expandToFoundation(children, cardToMove, MyUtils.SPADES)) {
					return children;
				}
				;
				break;
			case 'C':
				if (expandToFoundation(children, cardToMove, MyUtils.CLUBS)) {
					return children;
				}
				break;
			case 'H':
				if (expandToFoundation(children, cardToMove, MyUtils.HEARTS)) {
					return children;
				}

				break;

			default:
				break;
			}

			// to freecell
			if (this.canMoveToFreecell(cardToMove)) {
				State childrenState = this.clone();
				childrenState.moveCardToFreecell(cardToMove);
				childrenState.setParent(this);
				childrenState.setMove(MyUtils.FREECELL + " " + cardToMove.toString());
				if (childrenState.alreadyInHistory()) {
					childrenState = null;
					continue;
				} else {
					children.add(childrenState);
				}
			}

		}

		return children;
	}

	public boolean expandToFoundation(Frontier children, Card cardToMove, int suit) {
		if (this.canMoveToFoundation(cardToMove, this.foundations.get(suit))) {
			State childrenState = this.clone();
			childrenState.moveCardToFoundation(cardToMove, childrenState.getFoundations().get(suit));
			childrenState.setParent(this);
			childrenState.setMove(MyUtils.FOUNDATION + " " + cardToMove.toString());
			if (childrenState.alreadyInHistory()) {
				return false;
			} else {
				children.add(childrenState);
				return true;
			}
		}
		return false;
	}

//	public Frontier getMoveFromFoundationToOtherPosition(String method) {
//		Frontier children = new Frontier();
//
//		for (int i = 0; i < 4; i++) {
//
//			if (this.foundations.get(i).isEmpty()) {
//				continue;
//			}
//			Card c = this.foundations.get(i).peek().clone();
//
//			// to freecell
//			if (this.canMoveToFreecell(c)) {
//				State childrenState = this.clone();
//				childrenState.moveCardToFreecell(c);
//				childrenState.setParent(this);
//				childrenState.setMove(MyUtils.FREECELL + " " + c.toString());
//				
//				if(alreadyInHistory(childrenState)) {
//					continue;
//				}else {
//					children.add(childrenState);					
//				}
//				
//			}
//
//			for (int j = 0; j < 8; j++) {
//				if (this.canMoveToStack(c, this.stacks.get(j))) {
//					State childrenState = this.clone();
//					childrenState.moveCardToStack(c, childrenState.getStacks().get(j));
//					childrenState.setParent(this);
//
//					if (this.stacks.get(j).isEmpty()) {
//						childrenState.setMove(MyUtils.NEWSTACK + " " + c.toString());
//					} else {
//
//						childrenState.setMove(
//								MyUtils.STACK + " " + c.toString() + " " + this.getStacks().get(j).peek().toString());
//					}
//
//					children.add(childrenState);
//				}
//			}
//
//		}
//
//		return children;
//	}

	public boolean alreadyInHistory() {
//		for (State historyState : MyUtils.gameHistory) {
//			if (this.equals(historyState)) {
//				return true;
//			}
//		}
//		return false;
		return MyUtils.gameHistory.contains(this);
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
		this.g = parent.getG() + 1;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

}
