import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Frontier {

	private TreeSet<State> nodes;
	private State currentNode;

	public Frontier() {
		nodes = new TreeSet<>();
	}

	public void search(State initialState, String method) {

		initialState.setMethod(method);

		nodes.add(initialState);
		int i = -1;

		while (nodes.size() > 0) {

			currentNode = nodes.pollFirst();
			
			i++;

			System.out.println("***************************");
			System.out.println(currentNode.getMove());
			currentNode.printState();

			if (currentNode.isSolved()) {
				System.out.println("Solution n found");
				ArrayList<State> solution = new ArrayList<State>();

				State parent = currentNode;

				while (parent != null) {
					solution.add(parent);
					parent = parent.getParent();
				}

				Collections.reverse(solution);
				solution.remove(0);
				System.out.println(solution.size());
				solution.forEach(x -> System.out.println(x.getMove()));
				System.out.println("frontier nodes: " + i);
				break;
			} else {
				nodes.addAll(currentNode.getChildrenOfState(currentNode.getMethod()));
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
