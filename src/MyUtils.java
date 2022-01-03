import java.util.Stack;
import java.util.TreeSet;

public class MyUtils {

	public static final int DIAMONDS = 0;
	public static final int SPADES = 1;
	public static final int CLUBS = 2;
	public static final int HEARTS = 3;

	public static final String FOUNDATION = "foundation";
	public static final String STACK = "stack";
	public static final String FREECELL = "freecell";
	public static final String NEWSTACK = "newstack";

	public static final String BREADTH = "breadth";
	public static final String DEPTH = "depth";
	public static final String BEST = "best";
	public static final String ASTAR = "astar";

	public static TreeSet<State> gameHistory = new TreeSet<State>();
	
	public static int N = 0;

	public static Stack<Card> getFoundation(State state, char value) {
		Stack<Card> foundation = null;
		switch (value) {
		case 'D':
			foundation = state.getFoundations().get(DIAMONDS);
			break;
		case 'S':
			foundation = state.getFoundations().get(SPADES);
			break;
		case 'C':
			foundation = state.getFoundations().get(CLUBS);
			break;
		case 'H':
			foundation = state.getFoundations().get(HEARTS);
			break;
		default:
			break;
		}
		return foundation;
	}

	public static Card getCardByIdentifier(State state, char suit, int value) {

		// create card object with the input
		Card c = new Card(suit, value);

		// if card in freecells return it
		if (state.getFreecells().contains(c)) {
			return c;
		}

		// if card on top of a foundation return it
		for (int i = 0; i < 4; i++) {
			if (state.getFoundations().get(i).size() != 0 && state.getFoundations().get(i).peek().equals(c)) {
				return c;
			}
		}

		// if card is on top of a stack return it
		for (int i = 0; i < 8; i++) {
			if (state.getStacks().get(i).size() != 0 && state.getStacks().get(i).peek().equals(c)) {
				return c;
			}
		}

		return null;
	}

	public static int getStackIdxFromCard(State state, Card card) {
		for (int i = 0; i < 8; i++) {
			if (state.getStacks().get(i).size() != 0 && state.getStacks().get(i).peek().equals(card)) {
				return i;
			}
		}
		return -1;
	}

	public static int getfoundationIdxFromCard(State state, Card card) {
		for (int i = 0; i < 8; i++) {
			if (state.getFoundations().get(i).size() != 0 && state.getFoundations().get(i).peek().equals(card)) {
				return i;
			}
		}
		return -1;
	}

}
