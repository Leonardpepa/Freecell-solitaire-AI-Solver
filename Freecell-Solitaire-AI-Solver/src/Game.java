import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * @author Leonard Pepa ICS20033
 *
 */

// CLASS GAME
// This class is created for testing and playing purposes
// it has no use for the assignment

// USAGE:
// Instantiate a Game object
// if you want to play from the command line typing the move you want to make
// call the gameLoop() function
//
// if you want to play the moves the solution file generated 
// fist initialise the state with the deck that generated the solution
// then call the playFromFile(String filename) pass it the file that contains the solution moves
// format of the file:
// K the number of moves new line
// each move on new line
// example:
// 2
// freecell D0
// newstack F1

public class Game {
	private State state;

	public Game(State state) {
		this.state = state;
	}

	// function so a user can play from the terminal
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

			} catch (NumberFormatException e) {
				System.err.println("Wrong input pls use the format specified here");
				Auxiliary.moveHelperMessage();
				continue;
			}
		}

		System.out.println();
		System.out.println("You Win congrats!!");
		scan.close();
	}

	// function that plays the moves you give it
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

			} catch (NumberFormatException e) {
				System.err.println("Wrong input pls use the format specified here");
				Auxiliary.moveHelperMessage();
				continue;
			}
		}

		System.out.println();
		System.out.println("You Win congrats!!");
	}

	// function that read the moves from a file and plays them
	public void playFromFile(State state, String filename) {
		ArrayList<String> moves = FileHandler.readMoves(filename);
		gameLoop(moves);
	}

	// plays a move on the deck
	public void playMove(String[] move) {
		Card cardToMove = MyUtils.getCardByIdentifier(state, move[1].charAt(0), Integer.valueOf(move[1].substring(1)));

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
