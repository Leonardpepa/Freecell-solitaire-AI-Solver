import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Frontier {

	private TreeSet<State> nodes;
	private State currentNode;

	public Frontier() {
		nodes = new TreeSet<>();
	}

	public void search(State initialState, String method, String outputFile) {


		HashSet<State> visited = new HashSet<>();

		boolean solutionFound = false;

		Instant start = Instant.now();

		Long timeElapsed = Duration.between(start, Instant.now()).toMillis();

		int numOfNodesExpanded = 0;

		initialState.setMethod(method);

		nodes.add(initialState);

		System.out.println();
		System.out.println("Start Searching....");
		System.out.println("Using algorithm: " + method);
		System.out.println();

		while (!nodes.isEmpty() && !solutionFound && timeElapsed < 120000) {

			currentNode = nodes.pollFirst();

			if(!visited.contains(currentNode)){
				visited.add(currentNode);
			}else{
				continue;
			}

			if (currentNode.isSolved()) {
				solutionFound = true;
				new Solution(currentNode, outputFile);	
				continue;
			} else {
				nodes.addAll(currentNode.getChildrenOfState(currentNode.getMethod()));
				numOfNodesExpanded++;
			}

			timeElapsed = Duration.between(start, Instant.now()).toMillis();
		}

		System.out.println();

		if (!solutionFound) {
			System.out.println("Solution not found");
		}else{
			System.out.println("Solution found");
		}

		System.out.println("Time elapsed: " + timeElapsed + "ms");
		System.out.println("Nodes expanded: " + numOfNodesExpanded);
		System.out.println("Nodes in frontier: " + nodes.size());

	}

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
