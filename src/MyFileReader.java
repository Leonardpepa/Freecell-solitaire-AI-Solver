import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyFileReader {

	public static State readFile(String filename) throws IOException {
		
		State state = new State();
		
		File file = new File(filename);

		FileReader reader = null;
		BufferedReader input = null;

		try {
			reader = new FileReader(file.getCanonicalFile());
			input = new BufferedReader(reader);
			
			String line = input.readLine();
			
			int i=0;

			while(line != null) {
				String[] cards = line.split(" ");
				for(String c: cards) {
					char suit = c.charAt(0);
					int value = Integer.valueOf(c.substring(1));
					state.getHand().get(i).add(new Card(suit, value));
				}
				i++;
				line = input.readLine();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
			input.close();
		}

		return state;
	}

}
