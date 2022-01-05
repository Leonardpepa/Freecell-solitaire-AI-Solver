import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Leonard Pepa ICS20033
 *
 */

// CLASS SOLUTION
// holds the information and methods for the solution of the game
public class Solution {

	// array list with the solution
	private ArrayList<State> solution;

	// constructor
	// parameters
	// the last state with solved condition
	// fine name we want to write the solution
	public Solution(State lastState, String filename) {
		solution = new ArrayList<>();
		printSolution(extractSolution(lastState));
		writeSolutionToFile(filename);
	}

	// extract the solution from last state
	// going from parent to parent until we arrive at the first state
	private ArrayList<State> extractSolution(State lastState) {

		State parent = lastState;
		// going backwards
		while (parent != null) {
			solution.add(parent);
			parent = parent.getParent();
		}

		// reverse the solution so we have the moves in order
		Collections.reverse(solution);
		// remove the first node
		// because it does not have a move
		// it came from the file
		solution.remove(0);

		return solution;
	}

	// print the solution to the console
	public void printSolution(List<State> solution) {
		System.out.println("Total Steps: " + solution.size());
		solution.forEach(node -> System.out.println(node.getMove()));
	}

	// write the solution to the file
	public void writeSolutionToFile(String filename) {
		FileHandler.writeFile(filename, solution);
	}

}
