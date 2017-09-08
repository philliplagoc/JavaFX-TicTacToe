import java.util.Scanner;

/*
 * This class has 3 main functions:
 * 1) Take care of the board itself
 * 2) Check for win conditions
 * 3) Reset board if necessary
 */
public class TicTacToe {
	// Fields represent the board and Player Marker
	private char[][] board;
	private char playerMarker;

	public TicTacToe() {
		board = new char[3][3];
		playerMarker = 'o';
	}
	
	// Getters and setters
	public char[][] getBoard() {
		return board;
	}

	public char getPlayerMarker() {
		return playerMarker;
	}


	/*
	 * Initializes board to have each cell with '-', representing an empty tile
	 */
	public void initBoard() {
		for (int row = 0; row < this.board.length; row++) {
			for (int col = 0; col < this.board[row].length; col++) {
				board[row][col] = '-';
			}
		}
	}

	/*
	 * Print board
	 */
	public void printBoard() {
		System.out.print("+---++---++---+" + "\n");
		for (int row = 0; row < this.board.length; row++) {
			for (int col = 0; col < this.board[row].length; col++) {
				System.out.print("| " + board[row][col] + " |");
			}
			System.out.print("\n+---++---++---+");
			System.out.println();
		}
		System.out.println();
	}

	/*
	 * Check if the board is full
	 * 
	 * @return: true if full, false otherwise
	 */
	public boolean isBoardFull() {
		for (int row = 0; row < this.board.length; row++) {
			for (int col = 0; col < this.board[row].length; col++) {
				if (board[row][col] == '-') {
					return false;
				}
			}
		}
		return true;
	}

	// Win Conditions
	/*
	 * Check for win in columns
	 * 
	 * @return: true if there is a win in the columns, false otherwise
	 */
	public boolean checkColumnForWin() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				char tile1 = board[0][col];
				char tile2 = board[1][col];
				char tile3 = board[2][col];
				if (tile1 != '-' && tile1 == tile2 && tile2 == tile3) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Check for win in rows
	 * 
	 * @return: ture if there is a win in the rows, false otherwise
	 */
	public boolean checkRowForWin() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				char tile1 = board[row][0];
				char tile2 = board[row][1];
				char tile3 = board[row][2];
				if (tile1 != '-' && tile1 == tile2 && tile2 == tile3) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Check for diagonal win
	 * 
	 * @return: true for a win, false otherwise
	 */
	public boolean checkDiagonalForWin() {
		char topLeft = board[0][0];
		char botLeft = board[2][0];
		char mid = board[1][1];
		char topRight = board[0][2];
		char botRight = board[2][2];

		if (topLeft != '-' && topLeft == mid && mid == botRight) {
			return true;
		} else if (topRight != '-' && topRight == mid && mid == botLeft) {
			return true;
		} else
			return false;
	}

	/*
	 * Switches playerMark from 'o' to 'x' and back
	 */
	public void switchPlayerMarker() {
		if (playerMarker == 'o') {
			playerMarker = 'x';
		} else if (playerMarker == 'x') {
			playerMarker = 'o';
		}
	}

	/*
	 * Places playerMark at the specified tile
	 * 
	 * @param row: The row (y-coordinate) for the tile
	 * @param col: The column (x-coordinate) for the tile
	 */
	public boolean markTile(int row, int col) {
		boolean isValidRow = row >= 0 && row < 3;
		boolean isValidCol = col >= 0 && col < 3;
		if (this.board[row][col] == '-' && isValidRow && isValidCol) {
			this.board[row][col] = playerMarker;
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Checks to see if there is a win
	 * @return: true if there is a win, false otherwise (or if there is a draw)
	 */
	public boolean checkForWin() {
		if (checkRowForWin() || checkColumnForWin() || checkDiagonalForWin()) {
			return true;
		} else if (isBoardFull()) {
			return false;
		} else {
			return false;
		}
	}
	
	/*
	 * Checks if game is over
	 * @return: true if game is over
	 */
	public boolean isGameOver() {
		if (checkRowForWin() || checkColumnForWin() || checkDiagonalForWin() || isBoardFull()) {
			return true;
		} 
		return false;
	}

	/*
	 * Resets game and prints out a clean board
	 */
	public void resetBoard(boolean playAgain) {
		if (playAgain) {
			initBoard();
			printBoard();
		} else {
			System.out.println("Thanks for playing!");
		}
	}

	/*
	 * Takes player's turn and returns true or false depending on whether or not
	 * turn was taken
	 * 
	 * @param row: Row to place marker on
	 * @param col: Column to place marker on
	 * @return: True if turn was valid, false otherwise
	 */
	public boolean takeTurn(int row, int col) {
		if (row >= 1 && row <= 3 && col >= 1 && col <= 3) {
			if (markTile(row - 1, col - 1)) {
				switchPlayerMarker();
				return true;
			}
		}
		return false;
	}

	public boolean playAgain() {
		Scanner scanner = new Scanner(System.in);
		// boolean flag = false;

		System.out.println("Play again? Y/N");
		String option = scanner.nextLine().toUpperCase();
		
		switch (option) {
		case "Y":
			resetBoard(true);
			// flag = true;
			return false;
		case "N":
			resetBoard(false);
			// flag = true;
			return true;
		default:
			System.out.println("Please enter either Y for yes, or N for no.");
			// flag = false;
			break;
		}

		return false;
	}

	/*
	 * End the game and return true if the game is over, false otherwise
	 */
	public boolean endGame() {
		if (checkForWin()) {
			System.out.println("There is a winner!");
			boolean endGame = playAgain();
			return endGame;
		} else if (isBoardFull()) {
			System.out.println("There is a draw!");
			boolean endGame = playAgain();
			return endGame;
		}
		return false;
	}
}
