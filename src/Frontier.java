import java.util.TreeSet;

public class Frontier {

	private TreeSet<State> nodes;
	private State currentNode;

	public Frontier() {
		nodes = new TreeSet<>();
	}

	public void search(State initialState, String method, String outputFile) {
		int numOfNodesExpanded = 0;

		initialState.setMethod(method);

		nodes.add(initialState);

		System.out.println();
		System.out.println("Start Searching....");
		System.out.println("Using algorithm: " + method);
		System.out.println();
		while (!nodes.isEmpty()) {

			currentNode = nodes.pollFirst();

			if (currentNode.isSolved()) {
				new Solution(currentNode, outputFile);
				System.out.println("Number of nodes expanded: " + numOfNodesExpanded);
				System.out.println("Nodes on the frontier: " + nodes.size());
				break;
			} else {
				if (method.equals(MyUtils.BREADTH) && nodes.size() == 0) {
					nodes.addAll(currentNode.getChildrenOfState(currentNode.getMethod()));
				} else {
					nodes.addAll(currentNode.getChildrenOfState(currentNode.getMethod()));
				}
				numOfNodesExpanded++;
			}
		}

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
