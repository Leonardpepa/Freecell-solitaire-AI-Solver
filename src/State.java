import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

// CLASS STATE
// Represents the Tree Node
// holds information for all the deck
// holds the function for the logic of moves

// implements the comparable interface so we can choose based on the algorithm how to sort the nodes in the Tree Set
public class State implements Comparable<State> {

	// Array list that holds the freecells the order does not matter
	private ArrayList<Card> freecells;
	// Array List that holds the 4 stacks of foundations
	private ArrayList<Stack<Card>> foundations;

	// Array List that holds the 8 stacks of cascades
	private ArrayList<Stack<Card>> stacks;

	// helper data structure that contains the information of every cards position
	private HashMap<Card, String> pair;

	// move that resulted to this state
	private String move;

	// algorithm is used
	private String method;

	public int g;
	public int h;
	public int f;

	// parent of this state
	private State parent;

	// constructor
	// initialise the data structures ass needed
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

	// print the state to the console
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

	// Unique generated hash for each state
	@Override
	public int hashCode() {
		return Objects.hash(f, foundations, freecells, g, h, method, move, pair, parent, stacks);
	}

	// check for equality between states
	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}

		State s = (State) o;

		if (this == s) {
			return true;
		}

		// check if the two states have the same numbers of freecell cards
		if (this.freecells.size() != s.getFreecells().size()) {
			return false;
		}

		// check if the cards in the freecells are the same
		// order does not matter
		for (int i = 0; i < this.freecells.size(); i++) {
			if (!s.getFreecells().contains(this.freecells.get(i))) {
				return false;
			}
		}

		// check if the two states have the same numbers of foundation cards
		// check if the cards in the foundation are the same
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

		// check if the two states have the same numbers of stack cards
		// check if the cards in the stack are the same
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

	// ** This function decides how to compare two nodes based on the algorithm **
	// ** This function is being used by the Tree Set data structure to sort the
	// content **
	@Override
	public int compareTo(State otherState) {

		// if two nodes are the same
		if (this.equals(otherState)) {
			return 0;
		}

		// if the algorithm is breadth first search
		if (method.equals(MyUtils.BREADTH)) {

			// we care to search first the nodes with the lowest g
			// if they have the same g pick one
			// we need to search all the nodes with g and the move to g + 1
			if (this.g > otherState.getG())
				return 1;
			else if (this.g < otherState.getG())
				return -1;
			else {
				return 1;
			}

			// if the algorithm is depth first search
		} else if (method.equals(MyUtils.DEPTH)) {

			// we want to search all the children of a node before continue to another
			if (this.g > otherState.getG())
				return -1;
			else if (this.g < otherState.getG())
				return 1;
			else {
				return 1;
			}

			// if the algorithm is best first search
		} else if (method.equals(MyUtils.BEST)) {

			// we need to check the nodes with lower heuristic value
			if (this.h > otherState.getH())
				return 1;
			else if (this.h < otherState.getH())
				return -1;
			else {
				// if the heuristic value are equal
				if (this.g > otherState.getG()) {
					return 1;
				}
				return -1;
			}

			// if the algorithm is astar
		} else {
			// we need to check the nodes with lower f
			if (this.f > otherState.getF())
				return 1;
			else if (this.f < otherState.getF())
				return -1;
			else {
				// if the heuristic value are equal
				if (this.g > otherState.getG()) {
					return 1;
				}
				return -1;
			}

		}
	}

	// Create a deep copy of this object
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

	// return this object as a string
	@Override
	public String toString() {
		return "State [freecells=" + freecells + ",\n foundations=" + foundations + ",\n stacks=" + stacks + ",\n move="
				+ move + ",\n g=" + g + ",\n h=" + h + ",\n f=" + f + ",\n parent=" + parent + "]";
	}

	// function that moves a card to freecell
	// also removes the card from its position
	public boolean moveCardToFreecell(Card card) {

		removeCardFromItsPosition(card);

		freecells.add(card);
		pair.put(card, MyUtils.FREECELL);
		return true;
	}

	// function that moves the card to a stack that is provided
	// also removes the card from its position
	public boolean moveCardToStack(Card cardToMove, Stack<Card> stack) {

		removeCardFromItsPosition(cardToMove);

		stack.add(cardToMove);
		pair.put(cardToMove, MyUtils.STACK);
		return true;
	}

	// function that moves the card to a foundation that is provided
	// also removes the card from its position
	public boolean moveCardToFoundation(Card cardToMove, Stack<Card> foundation) {

		removeCardFromItsPosition(cardToMove);

		foundation.add(cardToMove);
		pair.put(cardToMove, MyUtils.FOUNDATION);
		return true;
	}

	// check if a card can move to a freecell
	// order does not matter
	// return true if it can
	// false if it can't move
	public boolean freecellRule(Card card) {
		return freecells.size() < 4;
	}

	// check if a card can move to a foundation that is provided
	// return true if it can
	// false if it can't move
	public boolean foundationRule(Card cardToMove, Stack<Card> foundation) {

		// check if the foundation is empty the card need to be A (value 0)
		if (foundation.isEmpty() && cardToMove.getValue() == 0) {
			return true;
		}

		// check if the cards follow's the preconditions
		if (!foundation.isEmpty() && cardToMove.isLargerAndSameSuit(foundation.peek())) {
			return true;
		}

		return false;
	}

	// check if a card can move to a stack that is provided
	// return true if it can
	// false if it can't move
	public boolean stackRule(Card cardToMove, Stack<Card> stack) {

		// f its empty any card can move to that stack
		if (stack.isEmpty()) {
			return true;
		}
		// check if the cards follow's the preconditions
		if (cardToMove.isSmallerAndDifferentColor(stack.peek())) {
			return true;
		}
		return false;
	}

	// check if this state is a solution
	public boolean isSolved() {

		// no cards in a freecell
		if (!freecells.isEmpty()) {
			return false;
		}

		// no card in a stack
		for (int i = 0; i < 8; i++) {
			if (!stacks.get(i).isEmpty()) {
				return false;
			}
		}

		// check if all the cards follow the precondition
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

	// uses the helper data structure to remove the card from its position
	public void removeCardFromItsPosition(Card cardToMove) {

		if (pair.get(cardToMove).equalsIgnoreCase(MyUtils.FREECELL)) {

			freecells.remove(cardToMove);

		} else if (pair.get(cardToMove).equalsIgnoreCase(MyUtils.STACK)) {

			// search for the right stack
			for (Stack<Card> stack : this.stacks) {
				if (!stack.isEmpty() && stack.peek().equals(cardToMove)) {
					stack.pop();
					break;
				}
			}

		} else if (pair.get(cardToMove).equalsIgnoreCase(MyUtils.FOUNDATION)) {
			// we get the right foundation then we remove the card
			Stack<Card> foundation = MyUtils.getFoundation(this, cardToMove.getSuit());
			foundation.remove(cardToMove);
		}

	}

	// return all the children of this state
	public LinkedList<State> getChildrenOfState(String method) {

		LinkedList<State> children = new LinkedList<State>();

		// moves from foundation to other positions
		children.addAll(getMoveFromFoundationToOtherPosition(method));

		// moves from stack to other position
		children.addAll(getMovesFromStackToOtherPosition(method));

		// moves from freecells to other position
		children.addAll(getMovesFromFreecellsToOtherPosition(method));

		return children;
	}

	// return all the moves from freecell to other position
	public LinkedList<State> getMovesFromFreecellsToOtherPosition(String method) {

		LinkedList<State> children = new LinkedList<State>();

		// if freecells are empty we return an empty treeset
		if (this.freecells.isEmpty()) {
			return children;
		}

		// for every Card in freecell
		for (Card card : this.freecells) {

			// make a copy of the card
			Card cardToMove = card.clone();

			// variable to check if this card has moved to new stack
			// meaning we don't need to make another children of this card moving to another
			// new stack
			// its the same state
			boolean hasMovedToNewStack = false;

			// check if card can move from freecell to foundation
			State childrenState = expandedToFoundation(cardToMove, MyUtils.getFoundation(this, cardToMove.getSuit()));

			// if can we add it to the children
			if (childrenState != null) {
				children.add(childrenState);
			}

			childrenState = null;

			// check card move from freecell to stack
			// for each stack
			for (int i = 0; i < 8; i++) {

				// check if card can move to that stack
				if (this.stackRule(cardToMove, stacks.get(i))) {

					// if its an empty stack and we already have a child with that card being move
					// to an empty stack we continue to check the other stacks
					if (stacks.get(i).isEmpty() && hasMovedToNewStack) {
						continue;
					}

					// make a copy of the state
					childrenState = this.clone();

					// execute the move on that state
					childrenState.moveCardToStack(cardToMove, childrenState.getStacks().get(i));

					// set its parent
					childrenState.setParent(this);
					// set the heuristic value
					childrenState.setH(childrenState.heuristicFunction());
					// the f value
					childrenState.setF(method, childrenState);

					// if the card is moved to an empty stack we make the variable that checks for
					// that true
					// also we set the move of this state to new stack
					if (this.getStacks().get(i).isEmpty()) {
						childrenState.setMove(MyUtils.NEWSTACK + " " + cardToMove.toString());
						hasMovedToNewStack = true;
					} else {
						// else we set the the move to stack <card> <card>
						childrenState.setMove(MyUtils.STACK + " " + cardToMove.toString() + " "
								+ this.getStacks().get(i).peek().toString());
					}
					// in the end we add that child to the children tree set
					children.add(childrenState);
					childrenState = null;

				}
			}

		}
		return children;
	}

	// return the children's with the moves from a stack to another stack empty or
	// not
	public LinkedList<State> getMovesFromStackToOtherPosition(String method) {

		LinkedList<State> children = new LinkedList<State>();

		// for every stack
		for (int i = 0; i < 8; i++) {

			// if this stack is empty we continue with the next stack
			if (this.stacks.get(i).isEmpty()) {
				continue;
			}

			// make a copy of the card on top of teh stack
			Card cardToMove = this.stacks.get(i).peek().clone();

			// variable to check if this card has moved to new stack
			// meaning we don't need to make another children of this card moving to another
			// new stack
			// its the same state
			boolean hasMovedToNewStack = false;

			State childrenState = null;

			// check if card can move from stack to foundation
			childrenState = expandedToFoundation(cardToMove, MyUtils.getFoundation(this, cardToMove.getSuit()));
			if (childrenState != null) {
				// if can move add it to the children
				children.add(childrenState);
			}

			childrenState = null;

			// from stack to another stack
			// check for its other stack
			for (int j = 0; j < 8; j++) {

				// if its the same stack contnuew with the next stack
				if (i == j) {
					continue;
				}

				// don't allow card to move from new stack to another new stack
				// if card is the only card in i stack dont move it to empty stack
				// we get the same deck
				if (this.stacks.get(i).size() == 1 && this.stacks.get(j).isEmpty()) {
					continue;
				}

				// check if card can move to stack
				if (this.stackRule(cardToMove, this.stacks.get(j))) {

					// if its an empty stack and we already have a child with that card being move
					// to an empty stack we continue to check the other stacks
					if (this.stacks.get(j).isEmpty() && hasMovedToNewStack) {
						continue;
					}

					// make a copy of the state
					childrenState = this.clone();
					// execute the move on that state
					childrenState.moveCardToStack(cardToMove, childrenState.getStacks().get(j));
					// set its parent
					childrenState.setParent(this);

					// set the heuristic value
					childrenState.setH(childrenState.heuristicFunction());
					// the f value
					childrenState.setF(method, childrenState);

					// if the card is moved to an empty stack we make the variable that checks for
					// that true
					// also we set the move of this state to new stack
					if (this.stacks.get(j).isEmpty()) {
						childrenState.setMove(MyUtils.NEWSTACK + " " + cardToMove.toString());
						hasMovedToNewStack = true;
					} else {
						// else we set the the move to stack <card> <card>
						childrenState
								.setMove(MyUtils.STACK + " " + cardToMove.toString() + " " + this.stacks.get(j).peek());
					}
					// in the end we add that child to the children tree set
					children.add(childrenState);
					childrenState = null;
				}

			}

			// check if card can move from stack to freecell
			childrenState = expandedToFreecell(cardToMove);
			if (childrenState != null) {
				children.add(childrenState);
			}

		}

		return children;
	}

	// creates child that occurred from a card move to foundation
	public State expandedToFoundation(Card cardToMove, Stack<Card> foundation) {
		if (this.foundationRule(cardToMove, foundation)) {
			// make a copy of the state
			State childrenState = this.clone();
			// execute the move on that state
			childrenState.moveCardToFoundation(cardToMove, MyUtils.getFoundation(childrenState, cardToMove.getSuit()));
			// set its parent
			childrenState.setParent(this);
			// set the heuristic value
			childrenState.setH(childrenState.heuristicFunction());
			// the f value
			childrenState.setF(method, childrenState);
			// set the move that created this state
			childrenState.setMove(MyUtils.FOUNDATION + " " + cardToMove.toString());
			// return the children state
			return childrenState;
		}
		return null;
	}

	// creates child that occurred from a card move to freecell
	public State expandedToFreecell(Card cardToMove) {
		if (this.freecellRule(cardToMove)) {
			// make a copy of the state
			State childrenState = this.clone();
			// execute the move on that state
			childrenState.moveCardToFreecell(cardToMove);
			// set its parent
			childrenState.setParent(this);
			// set the heuristic value
			childrenState.setH(childrenState.heuristicFunction());
			// the f value
			childrenState.setF(method, childrenState);
			// set the move that created this state
			childrenState.setMove(MyUtils.FREECELL + " " + cardToMove.toString());
			// return the children state
			return childrenState;
		}
		return null;
	}

	// get children that occurred from cards moves from foundation to other
	// position
	public LinkedList<State> getMoveFromFoundationToOtherPosition(String method) {

		LinkedList<State> children = new LinkedList<State>();

		for (int i = 0; i < 4; i++) {

			if (this.foundations.get(i).isEmpty()) {
				continue;
			}

			Card cardToMove = this.foundations.get(i).peek().clone();
			boolean hasMovedToNewStack = false;

			for (int j = 0; j < 8; j++) {

				if (this.stackRule(cardToMove, this.stacks.get(j))) {

					if (this.stacks.get(j).isEmpty() && hasMovedToNewStack) {
						continue;
					}

					State childrenState = this.clone();
					childrenState.moveCardToStack(cardToMove, childrenState.getStacks().get(j));

					childrenState.setParent(this);
					childrenState.setH(childrenState.heuristicFunction());
					childrenState.setF(method, childrenState);
					if (this.stacks.get(j).isEmpty()) {
						childrenState.setMove(MyUtils.NEWSTACK + " " + cardToMove.toString());
						hasMovedToNewStack = true;
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

	// heuristic function
	// calculates the heuristic value
	// by giving penlaty to each card is not in the foundation and its not in order
	// in the stacks
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

	// bigger penalty if the card has small value
	private int getWorthOfCardReversed(Card card) {
		return Math.abs(MyUtils.N - card.getValue());
	}

	// number of cards being in wrong order
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

	// if method is astar set the f value to the g + h
	public void setF(String method, State state) {
		if (method.equals(MyUtils.ASTAR)) {
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
