
//	CLASS AUXIALIARY

//	Is used to provide the user with some messages
public class Auxiliary {

	// Error if invalid arguments
	public static void syntaxError() {
		System.out.println("freecell <method> <input-file> <output-file>");
		System.out.println("where: ");
		System.out.println("<method> = breadth|depth|best|astar");
		System.out.println("<input-file> is a file containing a freecell description.");
		System.out.println("<output-file> is the file where the solution will be written.");
	}

	// Return the error as a string so we can use it in a pop up window
	public static String getSyntaxError() {
		return "freecell <method> <input-file> <output-file>\n" + "where: " + "<method> = breadth|depth|best|astar\n"
				+ "<input-file> is a file containing a freecell description.\n"
				+ "<output-file> is the file where the solution will be written.";
	}

	// Prints the results in the console
	public static void printResults(boolean solutionFound, Long timeElapsed, int nodesIFrontier,
			int numOfNodesExpanded) {
		System.out.println();

		if (!solutionFound) {
			System.out.println("Solution not found");
		} else {
			System.out.println("Solution found");
		}

		System.out.println("Time elapsed: " + timeElapsed + "ms");
		System.out.println("Nodes expanded: " + numOfNodesExpanded);
		System.out.println("Nodes in frontier: " + nodesIFrontier);
	}

	// Message when the search starts
	public static void printStartSearchingMessage(String method) {
		System.out.println();
		System.out.println("Start Searching....");
		System.out.println("Using algorithm: " + method);
		System.out.println();
	}

	// If the user accidentally provides wrong algorithm 
	public static void wrongMethod() {
		System.err.println("Algorithm provided does not exist");
		System.err.println("Trying astar instead");
		System.err.println("Or terminate the program and provide a correct algorithm");
	}

	// Helper messages for game mode
	// Game mode is class created for testing purposes (testing manually) and also
	// playing the game
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
		System.out.println("<source> <card identifier ex: D5>");

	}

	// Game messages

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
