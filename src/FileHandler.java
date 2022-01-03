import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

	public static State readFile(String filename) throws IOException {

		State state = new State();

		File file = new File(filename);

		FileReader reader = null;
		BufferedReader input = null;

		try {
			reader = new FileReader(file.getCanonicalFile());
			input = new BufferedReader(reader);

			String line = input.readLine();

			int i = 0;

			int cardsN = 0;

			while (line != null) {
				String[] cards = line.split(" ");
				for (String c : cards) {
					cardsN++;
					char suit = c.charAt(0);
					int value = Integer.valueOf(c.substring(1));
					Card card = new Card(suit, value);
					state.getStacks().get(i).add(card);
					state.getPair().put(card, "stack");
				}
				i++;
				line = input.readLine();
			}
			
			
			MyUtils.N = cardsN / 4;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
			input.close();
		}

		return state;
	}

}
