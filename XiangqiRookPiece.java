import java.util.ArrayList;

/*
 * A class of xiangqi chess rook piece
 * @author: Nhien Phan
 */

public class XiangqiRookPiece extends ChessPiece implements VerticalMove, HorizontalMove {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  // possibleVerticalMoves stores all possible vertical moves for the rook
  private final ArrayList<Cell> possibleVerticalMoves = new ArrayList<>();
  // possibleHorizontalMoves stores all possible horizontal moves for the rook
  private final ArrayList<Cell> possibleHorizontalMoves = new ArrayList<>();
  
  //constructor
  public XiangqiRookPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /*
   * Return the array list of possible vertical moves (implement the Vertical Move interface)
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  public ArrayList<Cell> moveVertical(ChessBoard board, int row, int column) {
    possibleVerticalMoves.clear();
    int tempRow = row - 1;
    // check from the rook to the top of the board
    while (tempRow >= 0) {
      // if cell is empty
      if (board.getPiece(tempRow, column) == null)
        possibleVerticalMoves.add(new Cell(tempRow, column));
      // if cell has a piece of the same side
      else if (board.getPiece(tempRow, column).getSide() == this.getSide())
        break;
      // if cell has piece of the other side
      else {
        possibleVerticalMoves.add(new Cell(tempRow, column));
        break;
      }
      tempRow--;
    }
    // check from the rook to the bottom of the board
    tempRow = row + 1;
    while (tempRow < board.numRows()) {
      // if cell is empty
      if (board.getPiece(tempRow, column) == null)
        possibleVerticalMoves.add(new Cell(tempRow, column));
      // if cell has piece of the same side
      else if (board.getPiece(tempRow, column).getSide() == this.getSide())
        break;
      // if cell has a piece of the other side
      else {
        possibleVerticalMoves.add(new Cell(tempRow, column));
        break;
      }
      tempRow++;
    }
    return possibleVerticalMoves;
  }
  
  /*
   * Return the array list of possible horizontal moves (implement the Horizontal Move interface)
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  public ArrayList<Cell> moveHorizontal(ChessBoard board, int row, int column) {
    possibleHorizontalMoves.clear();
    int tempColumn = column - 1;
    // check from the piece to the left border of the board
    while (tempColumn >= 0) {
      // if the cell is empty
      if (board.getPiece(row, tempColumn) == null)
        possibleHorizontalMoves.add(new Cell(row, tempColumn));
      // if the cell has a piece of the same side
      else if (board.getPiece(row, tempColumn).getSide() == this.getSide())
        break;
      // if the cell has piece of the different side
      else {
        possibleHorizontalMoves.add(new Cell(row, tempColumn));
        break;
      }
      tempColumn--;
    }
    tempColumn = column + 1;
    // check from the piece to the right border of the board
    while (tempColumn < board.numColumns()) {
      // if the cell is empty
      if (board.getPiece(row, tempColumn) == null)
        possibleHorizontalMoves.add(new Cell(row, tempColumn));
      // if the cell has a piece of the same side
      else if (board.getPiece(row, tempColumn).getSide() == this.getSide())
        break;
      // if the cell has a piece of the other side
      else {
        possibleHorizontalMoves.add(new Cell(row, tempColumn));
        break;
      }
      tempColumn++;
    }
    return possibleHorizontalMoves;
  }
  
  /** Return all possible moves */
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.addAll(this.moveVertical(board, row, column));
    possibleMoves.addAll(this.moveHorizontal(board, row, column));
    return possibleMoves;
  }
  
  /*
   * Check if this move is a legal move (return true if it is legal (false if illegal)
   * @param toRow: the destination's row
   * @param toColumn: the destination's column
   */
  public boolean isLegalMove(int toRow, int toColumn) {
    Cell targetSquare = new Cell(toRow, toColumn);
    move(this.getChessBoard(), this.getRow(), this.getColumn());
    for (Cell possibleMove : possibleMoves) {
      if (possibleMove.compareTo(targetSquare) == 0 && super.isLegalMove(toRow, toColumn)) return true;
    }
    return false;
  }
  
  /*
   * Check if this move is a legal non-capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public boolean isLegalNonCaptureMove(int row, int column) {
    return this.isLegalMove(row, column)
      && !getChessBoard().hasPiece(row, column);
  }
  
  /*
   * Check if this move is a legal capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public boolean isLegalCaptureMove(int row, int column) {
    return this.isLegalMove(row, column)
      && getChessBoard().squareThreatened(row, column, getChessBoard().getPiece(row, column));
  }
  
  /* 
   * Handle details after a move is done by deleting the piece at the old location and add a new piece at the new location
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public void moveDone(int row, int column) {
    if (this.isLegalMove(row, column) && this.getChessBoard().hasPiece(row, column)
          && this.getChessBoard().getPiece(row, column).getSide() != this.getSide())
      super.getChessBoard().removePiece(row, column);
    this.getChessBoard().addPiece(
                                  new XiangqiRookPiece(this.getSide(), XiangQiPieceType.R.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
