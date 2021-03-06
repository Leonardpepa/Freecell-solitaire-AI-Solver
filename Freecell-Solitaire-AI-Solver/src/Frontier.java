import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * 
 * @author Leonard Pepa ICS20033
 *
 */

// ClASS FRONTIER
public class Frontier {

	// TreeSet data structure to hold the nodes we need to search
	// based on the algorithm this data structure sort's its content
	private TreeSet<State> nodes;

	// current node we search
	private State currentNode;

	// constructor
	public Frontier() {
		nodes = new TreeSet<>();
	}

	// Search for a solution if exists
	// also check's for time to run out
	// and if solution is found call's the methods to print and write it to the file
	public void search(State initialState, String method, String outputFile) {

		// set the method we are using to the initial state
		initialState.setMethod(method);

		// data structure to hold the visited nodes
		HashMap<State, Boolean> visited = new HashMap<State, Boolean>();

		boolean solutionFound = false;

		// start the timer
		Instant start = Instant.now();

		// get the time now to use it in the while
		Long timeElapsed = Duration.between(start, Instant.now()).toMillis();

		// numbers of node's that are expanded initialised to 0
		// increment every time we expand a node
		int numOfNodesExpanded = 0;

		// add the initial state to the Tree Set
		nodes.add(initialState);

		// print message
		Auxiliary.printStartSearchingMessage(method);

		// while we have nodes in the the Tree Set, we have not find a solution and we
		// have not exceed the time limit
		// keep searching										
		// limit is 30 seconds
		while (!nodes.isEmpty() && !solutionFound && timeElapsed < MyUtils.LIMIT) {

			// get the node we have to search next
			// based on the algorithm the TreeSet provides the correct node
			currentNode = nodes.pollFirst();

			// if its already visited continue with the next one
			if (visited.containsKey(currentNode)) {
				continue;
			}
			// else add the current node to visited
			visited.put(currentNode, true);

			// check if current node is a solution
			if (currentNode.isSolved()) {
				// update variable
				solutionFound = true;
				// stop the loop
				break;
			} else {
				// add the children of current node to the TreeSet
				LinkedList<State> children = currentNode.getChildrenOfState(method);

				for (State child : children) {
					// if not already visited
					if (!visited.containsKey(child)) {
						// Tree set decides how to put the items based on algorithm
						nodes.add(child);
					}
				}

				// increment the expanded nodes
				numOfNodesExpanded++;
			}

			// check the time but not to often
			if (numOfNodesExpanded % 2 == 0) {
				timeElapsed = Duration.between(start, Instant.now()).toMillis();
			}
		}

		// create a new solution instance
		// parameters
		// current not that is the solved one
		// output filename
		new Solution(currentNode, outputFile, solutionFound);

		// print the result to the console
		Auxiliary.printResults(solutionFound, (float) (timeElapsed / 1000.0), nodes.size(), numOfNodesExpanded,
				visited.size());

		// free space
		nodes = null;
		currentNode = null;

	}

	// getters and setters
	public TreeSet<State> getNodes() {
		return nodes;
	}

	public void setNodes(TreeSet<State> nodes) {
		this.nodes = nodes;
	}

	public State getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(State currentNode) {
		this.currentNode = currentNode;
	}

}
