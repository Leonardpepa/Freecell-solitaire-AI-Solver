import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		String test1 = "test1.txt";

		State stateTest1 = null;
		try {
			stateTest1 = MyFileReader.readFile(test1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stateTest1.printState();
	}

}
