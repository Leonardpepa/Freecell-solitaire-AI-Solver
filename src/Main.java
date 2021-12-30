import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

	static Instant start = null;
	static long timeElapsed;

	public static void main(String[] args) {

		String test1 = "tiny.txt";

		State initialState = null;

		try {
			start = Instant.now();

			initialState = MyFileReader.readFile(test1);

			Frontier gameFrontier = new Frontier();

			gameFrontier.add(initialState);

			while (gameFrontier.size() > 0) {

				State currentState = gameFrontier.poll();

				MyUtils.gameHistory.add(currentState);

				if (currentState.isSolved()) {

					printSolution(currentState);
					break;

				} else {
					Frontier children = currentState.getChildrenOfState("breadth");
					gameFrontier.addAll(children);
				}
			}

			getTimePassedToMillis();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printSolution(State lastState) {
		System.out.println("solution found");
		ArrayList<State> solution = new ArrayList<State>();
		State parent = lastState.getParent();

		while (parent != null) {
			solution.add(parent);
			parent = parent.getParent();
		}
		Collections.reverse(solution);
		solution.remove(0);
		System.err.println("Steps: " + solution.size());
		solution.forEach(x -> {
			System.out.println(x.getMove());
		});
	}

	public static void getTimePassedToMillis() {
		Instant finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Time: " + timeElapsed);
	}

}
