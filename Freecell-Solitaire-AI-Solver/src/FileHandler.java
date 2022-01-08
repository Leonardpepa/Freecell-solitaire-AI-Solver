import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Leonard Pepa ICS20033
 *
 */

// CLASS FILEHANDLER

// Handles all the operations with the input output
public class FileHandler {

	// Read the initial state given by the input file
	// create and return the root node
	// type State
	public static State readFile(String filename) throws IOException {

		// Initialise empty node
		State state = new State();

		// create file and reader instances
		File file = new File(filename);
		FileReader reader = null;
		BufferedReader input = null;

		try {
			// Initialise the readers
			reader = new FileReader(file.getCanonicalFile());
			input = new BufferedReader(reader);

			// first line of the file
			String line = input.readLine();

			// keep track of the stack we are in
			int i = 0;

			// keep track of total cards
			int numberOfCards = 0;

			// for each line
			while (line != null) {

				// split the line to spaces and store in array
				String[] cards = line.split(" ");

				// for each card
				for (String c : cards) {
					// Increment the numbers of card
					numberOfCards++;

					// get the suit
					char suit = c.charAt(0);
					// get the value (needs to be between 0 N * 4)
					int value = Integer.valueOf(c.substring(1));

					// create the card
					Card card = new Card(suit, value);

					// add card to the stack
					state.getStacks().get(i).add(card);
					// add the position of the card to a helper data structure
					// stores key value pair
					// exaple D5: stack
					state.getPair().put(card, MyUtils.STACK);
				}
				// increment the i so we move to next stack
				i++;
				// read the next line
				line = input.readLine();

			}
			// calculate N based on total cards
			MyUtils.N = numberOfCards / 4;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
			input.close();
		}

		return state;
	}

	// write the solution found on the file
	// format:
	// k (numbers of steps) new line
	// each move in new line
	// moves can be
	// freecell <Card>
	// stack <Card to move> <card below>
	// newstack <Card>
	// source <card>
	public static void writeFile(String filname, List<State> solution) {
		File file = new File(filname);
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(solution.size() + System.lineSeparator());
			for (State node : solution) {
				fileWriter.write(node.getMove() + System.lineSeparator());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// function that reads the moves from a solution file
	// and return an array list with the moves
	public static ArrayList<String> readMoves(String filename) {
		ArrayList<String> moves = new ArrayList<>();

		File file = new File(filename);

		FileReader reader = null;
		BufferedReader input = null;

		try {
			reader = new FileReader(file.getCanonicalFile());
			input = new BufferedReader(reader);

			String line = input.readLine().trim();
			int n = Integer.valueOf(line);

			for (int i = 0; i < n; i++) {
				line = input.readLine();
				moves.add(line);
			}
			return moves;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return moves;
	}

}
