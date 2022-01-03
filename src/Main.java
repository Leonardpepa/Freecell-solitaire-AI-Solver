import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		try {
			State initialState = FileHandler.readFile("small.txt");

			Frontier frontier = new Frontier();

			frontier.search(initialState, "best", "solution.txt");
			Game game = new Game(initialState);
			game.playFromFile(initialState, "solution.txt");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
