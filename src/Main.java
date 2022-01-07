import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * 
 * @author Leonard Pepa ICS20033
 *
 */

// FREECELL AI SOLVER 

// Assignment for the subject Artificial Intelligence from University Of Macedonia
// Professor Ioannis Refanidis

// ALGORITHMS USED
// DFS (DEPTH-FIRST-SEARCH)
// BFS (BREADTH-FIRST-SEARCH)
// BEST (BEST-FIRST-SEARCH)
// ASTAR (A* SEARCH)

// RUN
// Runs via terminal with commands:
// java -jar <name of the jar> <algorithm selected breadth/depth/best/astar> <input file> <output file>
// The result will be a file with the steps that solve the problem if the algorithm finds a solution in given time 

// TEST CASE GENERATOR
// a c program named generator.c is provided by Ioannis Refanidis (Professor) to generate freecell decks
// usage of this generator:
// compile with gcc generator.c -o generator
// run with generator.exe <file name> <from (ex: 1)> <to (ex: 10)> 
// the result is the creation of 10 files from name1.txt to name10.txt

// Deck
// N number of cards in a single foundation when game completed
// Total cards N * 4
// Suits: S (SPADES) D (DIAMONDS) H (HEARTS) C (CLUBS)
// Card value are from 0 to (N-1) 

//	Time
//	all algorithm's run for a minute because the number of nodes that are created is to high and we encounter java out of memory error

// this program takes care of symmetrical equal positions
// by storing the freecell cards in one array list 
// order does not matter because we just check if the card is in freecells and we don't check for its index
// example of symmetrical positions: 
// freecells: [D0, S2, D1,C5]
// freecells: [C5, D0, S2, D1]
// its the same position because the same cards are in freecels

// other symmetrical positions are when a card is moved from empty stack to another empty stack
// this program does not count this move as valid children 
// example of symmetrical positions in stacks:
// stacks: [D0]
//		   [C1, S1, H4]
//		   []
//		   []
//		   []
//		   []
//		   []
//		   []
//
//stacks: []
//		  [C1, S1, H4]
//		  []
//		  [D0]
//		  []
//		  []
//		  []
//		  []
// its the same position so we don't count it as a valid child

// Main class the starts the program
public class Main {

	// Arguments algorithm <input file> <output file>
	public static void main(String[] args) {
		

		// show error message if invalid arguments
		if (args.length != 3) {
			JOptionPane.showMessageDialog(null, Auxiliary.getSyntaxError(), "Error | Invalid Arguments",
					JOptionPane.ERROR_MESSAGE);
			Auxiliary.syntaxError();
			System.exit(1);
		} else {

			// store arguments to variables
			String method = MyUtils.getMethod(args[0]);
			String input = args[1];
			String output = args[2];

			// Reading the file might cause an exception if you provide wrong file name or a
			// file that is not is the same directory
			try {

				// Read the initial state from the input file
				State initialState = FileHandler.readFile(input);

				// Initialise the frontier
				Frontier frontier = new Frontier();

				// Start the search passing the state of the deck the algorithm we want to use
				// and the output filename we want to store the solution if exists
				frontier.search(initialState, method, output);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
