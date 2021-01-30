import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Rules for how we want a board to display for a game of Xiangqi chess
 *
 * @author Nhien Phan
 */
public class JavaFXXiangqiDisplay implements JavaFXChessBoardDisplay {
  /**
   * The primary color of the checkerboard
   */
  public static Color greyColor = Color.LIGHTGREY;
  /**
   * The secondary color of the checkerboard for top and bottom three centered squares
   */
  public static Color darkerGreyColor = Color.DARKGREY.darker();
  /* The color of the SOUTH player */
  public static Color southPlayerColor = Color.LIGHTGREEN;
  /* The color of the NORTH player */
  public static Color northPlayerColor = Color.RED;
  /* The color of the EAST player */
  public static Color eastPlayerColor = Color.WHITE;
  /* The color of the WEST player */
  public static Color westPlayerColor = Color.GRAY;
  /**
   * The color used to highlight a square
   */
  public static Color highlightColor = Color.BLUE;
  // Radius of the piece
  double cornerRad = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 80;
  
  /**
   * Display a square that has no piece on it.  Will produce a red/black checkerboard.
   *
   * @param button the button that is used for the chessboard square
   * @param row    the row of this square on the board
   * @param column the column of this square on the board
   */
  public void displayEmptySquare(Button button, int row, int column) {
    // if the square is one of the top and bottom three centered squares
    if ((row >= 3 && row <= 5) && ((column <= 2) || (column >= 7))) {
      button.setBackground(new Background(new BackgroundFill(darkerGreyColor, CornerRadii.EMPTY, new Insets(0.5))));
    } else {
      button.setBackground(new Background(new BackgroundFill(greyColor, CornerRadii.EMPTY, new Insets(0.5))));
    }
    button.setText(null);
  }
  
  /**
   * Display a square that has a piece on it.
   *
   * @param button the button that is used for the chessboard square
   * @param row    the row of this square on the board
   * @param column the column of this square on the board
   * @param piece  the piece that is on this square
   */
  public void displayFilledSquare(Button button, int row, int column, ChessPiece piece) {
    Color pieceColor;
    
    switch (piece.getSide()) {
      case NORTH:
        pieceColor = northPlayerColor;
        break;
      case SOUTH:
        pieceColor = southPlayerColor;
        break;
      case EAST:
        pieceColor = eastPlayerColor;
        break;
      default:
        pieceColor = westPlayerColor;
    }
    // array of color for square background and color of piece
    BackgroundFill[] backgroundFillArray = new BackgroundFill[]{button.getBackground().getFills().get(0),
      new BackgroundFill(pieceColor, new CornerRadii(cornerRad), new Insets(5.0))};
    // set the color for the cell
    button.setBackground(new Background(backgroundFillArray));
    // display name of the piece
    button.setText(piece.getLabel());
  }
  
    /**
   * Highlight a square of the board.
   *
   * @param highlight do we want the highlight on (true) or off (false)?
   * @param button    the button that is used for the chessboard square
   * @param row       the row of this square on the board
   * @param column    the column of this square on the board
   * @param piece     the piece (if any) that is on this square
   */
  public void highlightSquare(boolean highlight, Button button, int row, int column, ChessPiece piece) {
    // color of the piece depends on side
    Color pieceColor;
    // if the cell needs to be highlighted
    if (highlight) {
      // if there is a piece in that cell
      if (piece != null) {
        switch (piece.getSide()) {
          case NORTH:
            pieceColor = northPlayerColor;
            break;
          case SOUTH:
            pieceColor = southPlayerColor;
            break;
          case EAST:
            pieceColor = eastPlayerColor;
            break;
          default:
            pieceColor = westPlayerColor;
        }
        // array of color for square background and color of piece
        BackgroundFill[] backgroundFillArray = new BackgroundFill[]{new BackgroundFill(highlightColor,
                                                                                       CornerRadii.EMPTY, new Insets(0.5)), new BackgroundFill(pieceColor,
                                                                                                                                               new CornerRadii(cornerRad), new Insets(5.0))};
        // highlight the cell
        button.setBackground(new Background(backgroundFillArray));
        // display the piece on that cell
        displayFilledSquare(button, row, column, piece);
      } 
      // if the cell is empty
      else {
        displayEmptySquare(button, row, column);
      }
    } 
    // if the cell needs to be unhighlighted
    else {
      // if there are pieces on that cell
      if (piece != null) {
        switch (piece.getSide()) {
          case NORTH:
            pieceColor = northPlayerColor;
            break;
          case SOUTH:
            pieceColor = southPlayerColor;
            break;
          case EAST:
            pieceColor = eastPlayerColor;
            break;
          default:
            pieceColor = westPlayerColor;
        }
        // array of color for square background and color of piece
        Color backgroundColor;
        // if the square is one of the top and bottom three centered squares
        if ((row >= 3 && row <= 5) && ((column <= 2) || (column >= 7))) {
          backgroundColor = darkerGreyColor;
        } else {
          backgroundColor = greyColor;
        }
        backgroundColor = greyColor;
        BackgroundFill[] backgroundFillArray = new BackgroundFill[]{new BackgroundFill(backgroundColor,
                                                                                       CornerRadii.EMPTY, new Insets(0.5)), new BackgroundFill(pieceColor,
                                                                                                                                             new CornerRadii(cornerRad), new Insets(5.0))};
        // set background color
        button.setBackground(new Background(backgroundFillArray));
        // display the filled cell
        displayFilledSquare(button, row, column, piece);
      } 
      // if the cell is empty
      else {
        displayEmptySquare(button, row, column);
      }
    }
  } 
  
}
