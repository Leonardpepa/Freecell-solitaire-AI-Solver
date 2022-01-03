import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
	
	private ArrayList<State> solution;

	public Solution(State lastState, String filename){
		solution = new ArrayList<>();
		printSolution(extractSolution(lastState));
		writeSolutionToFile(filename);
	}

	private ArrayList<State> extractSolution(State lastState) {
		State parent = lastState;

		while(parent != null){
			solution.add(parent);
			parent = parent.getParent();
		}

		Collections.reverse(solution);
		solution.remove(0);

		return solution;
	}
	
	
	public void printSolution(List<State> solution) {
		System.out.println("Total Steps: " + solution.size());
		solution.forEach(node -> System.out.println(node.getMove()));
	}

	public void writeSolutionToFile(String filename) {
		FileHandler.writeFile(filename, solution);
	}
	
	
}
