import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		String test1 = "little.txt";

		State stateTest1 = null;
		
		try {
			stateTest1 = MyFileReader.readFile(test1);
			Game game = new Game(stateTest1);
			game.gameLoop();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
