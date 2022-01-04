import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
					Card card = new Card(suit, value, "stack");
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

	public static void writeFile(String filname, List<State> solution){
		File file = new File(filname);
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(solution.size() + System.lineSeparator());
			for(State node: solution){
				fileWriter.write(node.getMove()+ System.lineSeparator());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<String> readMoves(String filename){
		ArrayList<String> moves = new ArrayList<>();

		
		File file = new File(filename);

		FileReader reader = null;
		BufferedReader input = null;

		try {
			reader = new FileReader(file.getCanonicalFile());
			input = new BufferedReader(reader);

			String line = input.readLine().trim();
			int n = Integer.valueOf(line);
			
			for(int i=0; i<n; i++){
				line = input.readLine();
				moves.add(line);
			}
			return moves;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				reader.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		return moves;
	}

}
