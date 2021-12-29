
public class Auxiliary {

	public static void moveHelperMessage() {
		System.out.println();
		System.out.println("Move Helper message");
		System.out.println("---- Move card to freecell ----");
		System.out.println("<freecell> <card identifier ex: D5>");
		System.out.println();
		System.out.println("---- Move card to stack ----");
		System.out.println("<stack> <card identifier ex: D5 (card to move)> <card identifier ex: S4 (card on stack)>");
		System.out.println();
		System.out.println("---- Move card to empty stack ----");
		System.out.println("<newsstack>  <card identifier ex: D5>");
		System.out.println();
		System.out.println("---- Move card to foundation ----");
		System.out.println("<foundation> <card identifier ex: D5>");

	}

	public static void freecellsFullMessage() {
		System.err.println("Freecells are full or the card is already in a freecell");
		System.out.println();
	}

	public static void invalidMoveToStack() {
		System.err.println("Card should be smaller by 1 and different color to be placed in a stack");
		System.out.println();
	}

	public static void InvalidMoveToFoundation() {
		System.err.println("Card should be A to start the foundation stack");
		System.err.println("Card should be smaller by 1 and same suit to be placed in the foundation");
		System.out.println();
	}

	public static void genericError() {
		System.err.println("An Error ocured");
	}
	
}
