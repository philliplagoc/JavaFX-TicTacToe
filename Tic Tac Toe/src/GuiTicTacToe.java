
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class uses JavaFX to extend the Tic-Tac-Toe game from the being used in
 * the console to being displayed using GUI elements.
 * 
 * @author Phillip
 *
 */
public class GuiTicTacToe extends Application {
	private final int TILE_WIDTH = 150;
	private final int BOARD_SIZE = 3;
	private final Color COLOR_O = Color.BLUE;
	private final Color COLOR_X = Color.RED;
	private final Color COLOR_EMPTY = Color.rgb(232, 198, 106);
	private final Color COLOR_TILE_NUM = Color.rgb(165, 160, 145);

	private TicTacToe ticTacToe; // Game
	private char[][] ticTacToeBoard;
	private char ticTacToePlayerMarker;

	private GridPane gridPane; // Represents tiles
	private Rectangle[][] tiles;
	private Text[][] tileTexts;

	private StackPane gameOverPane; // Represents gameOverlay
	private Text gameOverText;

	private StackPane baseStackPane; // StackPane to add to scene

	private StackPane playerDisplayPane; // Pane to place player text on
	private Text playerDisplay;

	private BorderPane borderPane; // Gui Tiles

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Init ticTacToe
		ticTacToe = new TicTacToe();
		ticTacToe.initBoard();
		ticTacToeBoard = ticTacToe.getBoard();
		ticTacToePlayerMarker = ticTacToe.getPlayerMarker();

		// Init baseStackPane
		baseStackPane = new StackPane();
		baseStackPane.setStyle("-fx-background-color: rgb(244, 230, 188)");

		// Init gridPane
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setStyle("-fx-background-color: rgb(244, 230, 188)");
		gridPane.setHgap(15);
		gridPane.setVgap(15);
		// Init tiles and tileTexts
		tiles = new Rectangle[BOARD_SIZE][BOARD_SIZE];
		tileTexts = new Text[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				tiles[i][j] = new Rectangle(TILE_WIDTH, TILE_WIDTH);
				tileTexts[i][j] = new Text();
			}
		}
		constructTiles();
		constructTileVals();

		// Add tiles and tileValues
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Rectangle tile = tiles[i][j];
				Text tileText = tileTexts[i][j];
				gridPane.add(tile, j, i + 1, 1, 1);
				GridPane.setHalignment(tile, HPos.CENTER);
				gridPane.add(tileText, j, i + 1, 1, 1);
				GridPane.setHalignment(tileText, HPos.CENTER);
			}
		}

		// Init gameOverPane
		gameOverPane = new StackPane();
		gameOverPane.setStyle("-fx-background-color: transparent");

		// Init playerDisplayPane and playerDisplay
		playerDisplayPane = new StackPane();
		playerDisplayPane.setStyle("-fx-background-color: transparent");
		playerDisplay = new Text("Player " + ticTacToePlayerMarker + "'s turn:");
		playerDisplay.setFont(Font.font("Arial Black", FontWeight.BOLD, 35));
		playerDisplayPane.getChildren().add(playerDisplay);

		// Init borderPane
		borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15, 10, 10, 10));
		borderPane.setStyle("-fx-background-color: rgb(244, 230, 188)");
		borderPane.setTop(playerDisplayPane);
		BorderPane.setAlignment(playerDisplayPane, Pos.BOTTOM_CENTER);
		borderPane.setCenter(gridPane);
		BorderPane.setAlignment(gridPane, Pos.CENTER);
		Text instructions = new Text();
		instructions.setText("Press the corresponding number to mark a tile.");
		instructions.setFont(Font.font("Arial Black", FontPosture.ITALIC, 30));
		borderPane.setBottom(instructions);
		BorderPane.setAlignment(instructions, Pos.TOP_CENTER);

		// Add everything to each other
		baseStackPane.getChildren().addAll(borderPane, gameOverPane);
		StackPane.setAlignment(borderPane, Pos.CENTER);
		StackPane.setAlignment(gameOverPane, Pos.CENTER);
		borderPane.setTop(playerDisplayPane);
		BorderPane.setAlignment(playerDisplayPane, Pos.BOTTOM_CENTER);
		borderPane.setCenter(gridPane);
		BorderPane.setAlignment(gridPane, Pos.CENTER);

		// Create scene and show stage
		Scene scene = new Scene(baseStackPane);
		scene.setOnKeyPressed(new PlayHandler());

		primaryStage.setTitle("Tic Tac Toe");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Constructs tiles ie. Fill tile color
	private void constructTiles() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Rectangle tile = tiles[i][j];
				tile.setFill(COLOR_EMPTY);
			}
		}
	}

	// Constructs tileValues
	private void constructTileVals() {
		int counter = 1;
		for (int i = BOARD_SIZE - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Text tileText = tileTexts[i][j];
				tileText.setFont(Font.font("Arial Black", FontWeight.BOLD, 50));
				char boardTileText = ticTacToeBoard[i][j];
				if (boardTileText == '-') {
					tileText.setText("" + counter);
					tileText.setFill(COLOR_TILE_NUM);
				} else if (boardTileText == 'x') {
					tileText.setText("X");
					tileText.setFill(COLOR_X);
				} else if (boardTileText == 'o') {
					tileText.setText("O");
					tileText.setFill(COLOR_O);
				}
				GridPane.setHalignment(tileText, HPos.CENTER);
				counter++;
			}
		}
	}

	// Constructs player display
	private void constructPlayerDisplay() {
		ticTacToePlayerMarker = ticTacToe.getPlayerMarker();
		playerDisplay.setText("Player " + ticTacToePlayerMarker + "'s turn:");
	}

	// Resets board
	private void resetBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				tileTexts[i][j].setText("");
				tiles[i][j].setFill(COLOR_EMPTY);
			}
		}
	}

	// Refreshes GUI
	private void refresh() {
		resetBoard();
		ticTacToeBoard = ticTacToe.getBoard();
		constructPlayerDisplay();
		constructTiles();
		constructTileVals();
	}

	// Class handles numerical input and refreshes GUI
	private class PlayHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent e) {
			if (ticTacToe.isGameOver()) {
				gameOverText = new Text("GAME OVER!");
				gameOverText.setFont(Font.font("Impact", FontWeight.BOLD, 90));
				gameOverPane.setStyle("-fx-background-color: rgba(218, 225, 238, 0.73);");
				gameOverPane.getChildren().add(gameOverText);
				baseStackPane.setStyle("-fx-background-color: rgba(218, 225, 238, 0.73);");
			} else {
				if (e.getCode() == KeyCode.NUMPAD1) {
					if (ticTacToe.markTile(2, 0)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD2) {
					if (ticTacToe.markTile(2, 1)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD3) {
					if (ticTacToe.markTile(2, 2)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD4) {
					if (ticTacToe.markTile(1, 0)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD5) {
					if (ticTacToe.markTile(1, 1)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD6) {
					if (ticTacToe.markTile(1, 2)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD7) {
					if (ticTacToe.markTile(0, 0)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD8) {
					if (ticTacToe.markTile(0, 1)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				} else if (e.getCode() == KeyCode.NUMPAD9) {
					if (ticTacToe.markTile(0, 2)) {
						ticTacToe.switchPlayerMarker();
						refresh();
					}
				}

			}
		}
	}
}
