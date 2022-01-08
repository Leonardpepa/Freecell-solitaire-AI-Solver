import java.util.Stack;

/**
 * 
 * @author Leonard Pepa ICS20033
 *
 */

// CLASS MYUTILS
// Store's some helpful variables and functions
public class MyUtils {

	public static final int DIAMONDS = 0;
	public static final int SPADES = 1;
	public static final int CLUBS = 2;
	public static final int HEARTS = 3;

	public static final String FOUNDATION = "source";
	public static final String STACK = "stack";
	public static final String FREECELL = "freecell";
	public static final String NEWSTACK = "newstack";

	public static final String BREADTH = "breadth";
	public static final String DEPTH = "depth";
	public static final String BEST = "best";
	public static final String ASTAR = "astar";
	
	// Time limit up to 30 seconds
	public static final long LIMIT = 30000;

	// N cards in a single foundation when game completed
	// gets updated when we initialise the initial state
	public static int N = 0;

	// Returns the method the user chose
	// if user provides wrong method
	// uses astar (because is faster)
	public static String getMethod(String method) {

		switch (method.toLowerCase()) {
		case BREADTH:
			return BREADTH;
		case DEPTH:
			return DEPTH;
		case BEST:
			return BEST;
		case ASTAR:
			return ASTAR;
		default:
			Auxiliary.wrongMethod();
			return ASTAR;
		}

	}

	// Method that returns the foundation stack based on the cards suit
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

	// Method that find's the card on the deck and return's the cards instance
	// input the tree node we need to look for the card
	// the cards suit, value
	// output the card's instance if it's on top of a foundation / stack or in a
	// freecell
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

	// Returns the index of a stack the card is located
	// only if is located on top of the stack
	public static int getStackIdxFromCard(State state, Card card) {
		for (int i = 0; i < 8; i++) {
			if (state.getStacks().get(i).size() != 0 && state.getStacks().get(i).peek().equals(card)) {
				return i;
			}
		}
		return -1;
	}

	// Returns the index of a foundation the card is located
	// only if is located on top of the foundation
	public static int getfoundationIdxFromCard(State state, Card card) {
		for (int i = 0; i < 8; i++) {
			if (state.getFoundations().get(i).size() != 0 && state.getFoundations().get(i).peek().equals(card)) {
				return i;
			}
		}
		return -1;
	}

}
