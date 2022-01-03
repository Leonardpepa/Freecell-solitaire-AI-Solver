import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Game {
	private State state;

	public Game(State state) {
		this.state = state;
	}

	public void gameLoop() {
		Scanner scan = new Scanner(System.in);

		boolean gameOver = false;
		Auxiliary.moveHelperMessage();
		System.out.println();

		while (!gameOver) {

			try {

				System.out.println();

				state.printState();

				System.out.println();

				System.out.print("Enter your move: ");

				String line = scan.nextLine();

				String[] move = line.split(" ");

				playMove(move);
				
				if (state.isSolved()) {
					gameOver = true;
					state.printState();
				}

			} catch (Exception e) {
				System.err.println("Wrong input pls use the format specified here");
				Auxiliary.moveHelperMessage();
				continue;
			}
		}

		System.out.println();
		System.out.println("You Win congrats!!");
		scan.close();
	}

	public void gameLoop(ArrayList<String> moves) {

		boolean gameOver = false;
		Auxiliary.moveHelperMessage();
		System.out.println();

		while (!gameOver) {

			try {

				System.out.println();

				state.printState();

				System.out.println();

				for (String value : moves) {
					String[] move = value.split(" ");
					System.out.println();
					state.printState();
					playMove(move);
					System.out.println();
					System.out.println("Move Played: " + value);
				}

				if (state.isSolved()) {
					gameOver = true;
					state.printState();
				}

			} catch (Exception e) {
				System.err.println("Wrong input pls use the format specified here");
				Auxiliary.moveHelperMessage();
				continue;
			}
		}

		System.out.println();
		System.out.println("You Win congrats!!");
	}

	public void playFromFile(State state, String filename) {
		ArrayList<String> moves = FileHandler.readMoves(filename);
		gameLoop(moves);
	}

	public void playMove(String[] move) {
		Card cardToMove = MyUtils.getCardByIdentifier(state, move[1].charAt(0),
				Integer.valueOf(move[1].substring(1)));

		if (cardToMove == null) {
			return;
		}

		if (move[0].equalsIgnoreCase(MyUtils.FOUNDATION)) {

			Stack<Card> foundation = MyUtils.getFoundation(state, move[1].charAt(0));

			if (!state.foundationRule(cardToMove, foundation)) {
				Auxiliary.InvalidMoveToFoundation();
				return;
			}

			state.moveCardToFoundation(cardToMove, foundation);

		} else if (move[0].equalsIgnoreCase(MyUtils.FREECELL)) {

			if (!state.freecellRule(cardToMove)) {
				Auxiliary.freecellsFullMessage();
				return;
			}
			state.moveCardToFreecell(cardToMove);

		} else if (move[0].equalsIgnoreCase(MyUtils.STACK)) {

			Card cardOnStack = MyUtils.getCardByIdentifier(state, move[2].charAt(0),
					Integer.valueOf(move[2].substring(1)));

			Stack<Card> stack = state.getStacks().get(MyUtils.getStackIdxFromCard(state, cardOnStack));

			if (!state.stackRule(cardToMove, stack)) {
				Auxiliary.invalidMoveToStack();
				return;
			}
			state.moveCardToStack(cardToMove, stack);

		} else if (move[0].equalsIgnoreCase(MyUtils.NEWSTACK)) {

			state.getStacks().forEach(stack -> {
				if (stack.isEmpty()) {
					state.moveCardToStack(cardToMove, stack);
					return;
				}
			});

		} else {
			Auxiliary.moveHelperMessage();
		}
	}

}
