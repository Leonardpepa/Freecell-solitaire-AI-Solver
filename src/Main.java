import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		try {
			State initialState = FileHandler.readFile("little.txt");

			Frontier frontier = new Frontier();

			frontier.search(initialState, "best", "solution.txt");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
