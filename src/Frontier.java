import java.util.TreeSet;
public class Frontier {

	private TreeSet<State> nodes;
	private State currentNode;

	public Frontier() {
		nodes = new TreeSet<>();
	}

	public void search(State initialState, String method, String outputFile) {

		initialState.setMethod(method);

		nodes.add(initialState);

		while (!nodes.isEmpty()) {

			currentNode = nodes.pollFirst();
			System.out.println(currentNode.getG());
			if (currentNode.isSolved()) {
				new Solution(currentNode, outputFile);
				break;
			} else {
				if(method.equals(MyUtils.BREADTH) && nodes.size() == 0){
					nodes.addAll(currentNode.getChildrenOfState(currentNode.getMethod()));
				}else{
					nodes.addAll(currentNode.getChildrenOfState(currentNode.getMethod()));
				}
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
