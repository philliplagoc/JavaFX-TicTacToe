import java.util.Scanner;

/*
 * This class represents Tic-Tac-Toe being played from the console.
 * Players first choose which row (not zero-based) they would like to mark, then which column (also not zero-based).
 * The console will then print a new updated board, and check for winners.
 */
public class Main {

	public static void main(String[] args) {
		boolean isGameOver = false;
		Scanner scanner = new Scanner(System.in);
		TicTacToe ttt = new TicTacToe();
		ttt.initBoard();
		ttt.printBoard();

		System.out.println("Welcome to Tic Tac Toe!");

		while (!isGameOver) {
			boolean isValidTurn = false;
			while (!isValidTurn) {
				System.out
						.println("Player " + ttt.getPlayerMarker() + ": Please enter the row you would like to mark: ");
				int row = scanner.nextInt();

				System.out.println(
						"Player " + ttt.getPlayerMarker() + ": Please enter the column you would like to mark: ");
				int col = scanner.nextInt();

				if (ttt.takeTurn(row, col)) {
					isValidTurn = true;
				} else {
					System.out.println(
							"Invalid row and/ or column. Please make sure the tile is unmarked and/ or between 1 and 3.");
					isValidTurn = false;
				}
			}

			ttt.printBoard();

			if (ttt.endGame()) {
				isGameOver = true;
			} else {
				isGameOver = false;
			}
		}
	}

}
