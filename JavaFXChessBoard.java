import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

/*
 * An implementation of european and xiangqi chess by Java FX
 * @author: Nhien Phan
 */
public class JavaFXChessBoard extends Application implements ChessBoard {
  // number of rows in the game
  private int numRows;
  // number of column in the game
  private int numColumns;
  // the chess game display
  private JavaFXChessBoardDisplay display;
  // the chess game to play
  private Chess chess;
  // the chess pieces of game
  private ChessPiece[][] pieces;
  // the buttons on board
  private Button[][] squares;
  
  /**
   * The method to launch the program.
   *
   * @param args The command line arguments.  The arguments are passed on to the JavaFX application.
   */
  public static void main(String[] args) {
    Application.launch(args);
  }
  
  /**
   * Overrides the start method of Application to create the GUI with one button in the center of the main window.
   *
   * @param primaryStage the JavaFX main window
   */
  public void start(Stage primaryStage) {
    // check if this is european chess or xiangqi
    List<String> chessType = this.getParameters().getRaw();
    // european chess comparison
    List<String> compareEuropeanChessType = new ArrayList<String>();
    compareEuropeanChessType.add("chess");
    // xiangqi chess comparison
    List<String> compareXiangqiChessType = new ArrayList<String>();
    compareXiangqiChessType.add("xiangqi");
    // european chess case
    if (chessType.equals(compareEuropeanChessType)) {
      chess = new EuropeanChess();
      display = new JavaFXEuropeanChessDisplay();
      numRows = 8;
      numColumns = 8;
    }
    // xiangqi case
    else if (chessType.equals(compareXiangqiChessType)) {
      chess = new XiangqiChess();
      display = new JavaFXXiangqiDisplay();
      numRows = 10;
      numColumns = 9;
    }
    
    // create the gridpane
    GridPane gridPane = new GridPane();
    //BorderPane borderPane = new BorderPane();
    
    // array of pieces
    pieces = new ChessPiece[numRows][numColumns];
    // array of buttons
    squares = new Button[numRows][numColumns];
    // how button react when click
    ButtonAction responder = new ButtonAction();
    // creat each button, put it in the array, and put it on the grid pane
    for (int i = 0; i < numRows; i++) {
      for (int k = 0; k < numColumns; k++) {
        squares[i][k] = new Button();
        squares[i][k].setOnAction(responder);
        
        squares[i][k].prefWidthProperty().bind(gridPane.widthProperty());
        squares[i][k].prefHeightProperty().bind(gridPane.heightProperty());
                
        display.displayEmptySquare(squares[i][k], k, i);
        gridPane.add(squares[i][k], k, i);
      }
    }
    
    // Create a "scene" that contains the grid pane area
    Scene scene = new Scene(gridPane);    
    // start the game
    chess.startGame(this);
    
    primaryStage.setTitle("Chess Game");
    // Add the "scene" to the main window
    primaryStage.setScene(scene);
    // Display the window
    primaryStage.show();                     
  }
  
  /**
   * Returns the rules of the game.
   *
   * @return the rules of the game
   */
  public ChessGame getGameRules() {
    return chess;
  }
  
  /**
   * Returns the number of rows in the board.
   *
   * @return the number of rows
   */
  public final int numRows() {
    return squares.length;
  }
  
  /**
   * Returns the number of columns in the board.
   *
   * @return the number of columns
   */
  public final int numColumns() {
    return squares[0].length;
  }
  
  /**
   * Adds a piece to the board at the desired location.  Any piece currently
   * at that location is lost.
   *
   * @param piece the piece to add
   * @param row   the row for the piece
   * @param col   the column for the piece
   */
  public void addPiece(final ChessPiece piece, final int row, final int col) {
    // set the piece on the board, tell the piece where it is, and then use the display rules to display the square
    pieces[row][col] = piece;
    piece.setLocation(row, col);
    display.displayFilledSquare(squares[row][col], row, col, piece);
  }
  
  /**
   * Removes a piece from the board
   *
   * @param row the row of the piece
   * @param col the column of the piece
   * @return the piece removed of null if there was no piece at that square
   */
  public ChessPiece removePiece(final int row, final int col) {
    // remove the piece from the board, use the display rules to show an empty square,
    ChessPiece save = pieces[row][col];
    pieces[row][col] = null;
    display.displayEmptySquare(squares[row][col], row, col);
    return save;
  }
  
  /**
   * Returns true if there is a piece at a specific location of the board.
   *
   * @param row the row to examine
   * @param col the column to examine
   * @return true if there is a piece a this row and column and false
   * if the square is empty
   */
  public boolean hasPiece(int row, int col) {
    return (pieces[row][col] != null);
  }
  
  /**
   * Returns the chess piece at a specific location on the board.
   *
   * @param row the row for the piece
   * @param col the column for the piece
   * @return the piece at the row and column or null if there is no piece there.
   */
  public ChessPiece getPiece(int row, int col) {
    return pieces[row][col];
  }
  
  /**
   * Returns true if a particular square is threatened by an opposing piece.
   *
   * @param row    the row of the square
   * @param column the column of the square
   * @param piece  a piece of the game
   * @return true if the square can be attacked by a piece of an opposing side as the parameter piece
   */
  public boolean squareThreatened(int row, int column, ChessPiece piece) {
    for (int i = 0; i < squares.length; i++) {
      for (int j = 0; j < squares[i].length; j++) {
        if (hasPiece(i, j) && getPiece(i, j).getSide() != piece.getSide() &&
            getPiece(i, j).isLegalMove(row, column))
          return true;
      }
    }
    return false;
  }
  
  /*
   * The code the responds when the user clicks on the game board
   */
  private class ButtonAction implements EventHandler<ActionEvent> {
    // if true, we a selecting a piece  
    private boolean firstPick = true;
    // remember row of selected piece
    private int pieceRow;
    // remember column of selected piece
    private int pieceCol;              
    
    public void handle(ActionEvent e) {
      Button b = (Button) e.getSource();
      int col = -1;
      int row = -1;
      
      // first find which button (board square) was clicked.
      for (int i = 0; i < squares.length; i++) {
        for (int j = 0; j < squares[i].length; j++) {
          if (squares[i][j] == b) {
            row = i;
            col = j;
          }
        }
      }
      
      // if there is no piece at on the cell that is first-clicked
      if (firstPick && (!hasPiece(row, col) || !getGameRules().legalPieceToPlay(getPiece(row, col), row, col))) {}
      // if there is piece at on the cell that is first-clicked
      else if (firstPick && hasPiece(row, col)) {
        firstPick = false;
        // get piece location
        pieceRow = row;
        pieceCol = col;
        // highlight the cell the piece is on
        display.highlightSquare(true, squares[row][col], col, row, pieces[row][col]);
      }
      // if this the second click
      else if (hasPiece(row, col)
                 // if the piece belongs to the side whose turn to play is legitimate
                   && getGameRules().legalPieceToPlay(getPiece(row, col), row, col)
                 // if the player can change piece selection
                   && getGameRules().canChangeSelection(getPiece(row, col), row, col)) {
        // process pick another piece
        // check if choose the square same as first pick
        if (row == pieceRow && col == pieceCol)
          return;
        
        // unhighlight the cell of the old piece
        display.highlightSquare(false, squares[pieceRow][pieceCol], pieceCol, pieceRow, pieces[pieceRow][pieceCol]);
        // hightlight the square of the new piece
        display.highlightSquare(true, squares[row][col], col, row, pieces[row][col]);
        // get piece location
        pieceRow = row;
        pieceCol = col;
      } else {
        // process move
        boolean isMoved = getGameRules().makeMove(getPiece(pieceRow, pieceCol), row, col);
        
        // if a legitimate move was made
        if (isMoved && (row >= 3 && row <= 5) && ((col <= 2) || (col >= 7))) {
          // unhighlight the old cell
          display.highlightSquare(false, squares[pieceRow][pieceCol], pieceCol, pieceRow, pieces[pieceRow][pieceCol]);
          display.highlightSquare(false, squares[row][col], col, row, pieces[row][col]);
          firstPick = true;
        } else if (isMoved) {
          // unhighlight the old cell
          display.highlightSquare(false, squares[pieceRow][pieceCol], pieceCol, pieceRow, pieces[pieceRow][pieceCol]);
          firstPick = true;
        }
      }
    }
  }
  
}
