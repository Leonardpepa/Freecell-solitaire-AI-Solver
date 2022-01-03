import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		try {
			State initialState = FileHandler.readFile("small.txt");

			Frontier frontier = new Frontier();

			frontier.search(initialState, "astar");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
