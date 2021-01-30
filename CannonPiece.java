import java.util.ArrayList;

/* author: Nhien Phan
 * A class for the xiangqi chess cannon
 */
public class CannonPiece extends ChessPiece implements VerticalMove, HorizontalMove {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  // possibleVerticalMoves stores all possible vertical moves for the rook
  private final ArrayList<Cell> possibleVerticalMoves = new ArrayList<>();
  
  // possibleHorizontalMoves stores all possible horizontal moves for the rook
  private final ArrayList<Cell> possibleHorizontalMoves = new ArrayList<>();
  
  //constructor
  public CannonPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /*
   * Return an array list of legal vertical non-capture move (implements Vertical Move interface)
   * @param board: The board the cannon is on
   * @param row: the row the cannon is on
   * @param column: the column the cannon is on
   */
  public ArrayList<Cell> moveVertical(ChessBoard board, int row, int column) {
    possibleVerticalMoves.clear();
    int tempRow = row - 1;
    // check from the piece to the bottom of the board
    while (tempRow >= 0) {
      if (board.getPiece(tempRow, column) == null)
        possibleVerticalMoves.add(new Cell(tempRow, column));
      else if (board.getPiece(tempRow, column).getSide() == this.getSide())
        break;
      else {
        break;
      }
      tempRow--;
    }
    tempRow = row + 1;
    // check from the piece to the top of the board
    while (tempRow < board.numRows()) {
      if (board.getPiece(tempRow, column) == null)
        possibleVerticalMoves.add(new Cell(tempRow, column));
      else if (board.getPiece(tempRow, column).getSide() == this.getSide())
        break;
      else {
        break;
      }
      tempRow++;
    }
    return possibleVerticalMoves;
  }
  
  /*
   * Return an array list of legal horizontal non-capture move (implements Horizontal Move interface)
   * @param board: The board the cannon is on
   * @param row: the row the cannon is on
   * @param column: the column the cannon is on
   */
  public ArrayList<Cell> moveHorizontal(ChessBoard board, int row, int column) {
    possibleHorizontalMoves.clear();
    int tempColumn = column - 1;
    // check from the piece to the left of the board
    while (tempColumn >= 0) {
      if (board.getPiece(row, tempColumn) == null)
        possibleHorizontalMoves.add(new Cell(row, tempColumn));
      else if (board.getPiece(row, tempColumn).getSide() == this.getSide())
        break;
      else {
        break;
      }
      tempColumn--;
    }
    tempColumn = column + 1;
    // check from the piece to the right of the board
    while (tempColumn < board.numColumns()) {
      if (board.getPiece(row, tempColumn) == null)
        possibleHorizontalMoves.add(new Cell(row, tempColumn));
      else if (board.getPiece(row, tempColumn).getSide() == this.getSide())
        break;
      else {
        break;
      }
      tempColumn++;
    }
    return possibleHorizontalMoves;
  }
  
  /*
   * Return all possible moves 
   * @param board: The board the cannon is on
   * @param row: the row the cannon is on
   * @param column: the column the cannon is on
   */
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    // check if this is the only piece between two xiangqi king
    if (super.kingFacingKing()) {
      possibleMoves.addAll(this.moveVertical(board, row, column));
      return possibleMoves;
    }
    // Includes all possible vertical moves
    possibleMoves.addAll(this.moveVertical(board, row, column));
    // Includes all possible horizontal moves
    possibleMoves.addAll(this.moveHorizontal(board, row, column));
    // check vertical capture move
    for (int i = 0; i < getChessBoard().numRows(); i++) {
      if (checkCaptureMove(i, this.getColumn())) {
        possibleMoves.add(new Cell(i, this.getColumn()));
      }
    }
    // check horizontal capture move
    for (int k = 0; k < getChessBoard().numColumns(); k++) {
      if (checkCaptureMove(this.getRow(), k)) {
        possibleMoves.add(new Cell(this.getRow(), k));
      }
    }
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
    if (this.isLegalMove(row, column)
          && this.getChessBoard().hasPiece(row, column)
          && this.getChessBoard().getPiece(row, column).getSide() != this.getSide())
      super.getChessBoard().removePiece(row, column);
    this.getChessBoard().addPiece(
                                  new CannonPiece(this.getSide(), XiangQiPieceType.C.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
  
  /*
   * Check if this is a legal capture move for cannon
   * @param row: the row the cannon is on
   * @param column: the column the cannon is on
   */
  public boolean checkCaptureMove(int row, int column) {
    // store the piece between cannon and target
    int occupiedSquare = 0;
    
    /*
     * Check if the cell is empty OR
     * whether the piece at that cell is the same side OR
     * if the cell is on different rows or columns then return FALSE
     * */
    if (!this.getChessBoard().hasPiece(row, column)
          || this.getSide() == this.getChessBoard().getPiece(row, column).getSide()
          || (Math.abs(column - this.getColumn()) > 0 && Math.abs(row - getRow()) > 0)) {
      return false;
    } else {
      // if the target cell is on the same column with the cannon
      if (Math.abs(column - this.getColumn()) > 0) {
        // check from cannon to top of board
        if (column > this.getColumn()) {
          // check if there is a piece between the cannon and the target
          for (int i = this.getColumn() + 1; i < column; i++) {
            if (getChessBoard().getPiece(getRow(), i) != null) {
              occupiedSquare += 1;
            }
          }
        }
        // check from cannon to bottom of board
        else {
          for (int i = this.getColumn() - 1; i > column; i--) {
            if (getChessBoard().getPiece(getRow(), i) != null) {
              occupiedSquare += 1;
            }
          }
        }
      }
      // if the target cell is on the same row with the cannon
      else {
        // check from cannon to the right side of board
        if (row > getRow()) {
          for (int i = getRow() + 1; i < row; i++) {
            if ((getChessBoard().getPiece(i, this.getColumn())) != null) {
              occupiedSquare += 1;
            }
          }
        }
        // check from cannon to the left side of board
        else {
          for (int i = getRow() - 1; i > row; i--) {
            if (getChessBoard().getPiece(i, this.getColumn()) != null) {
              occupiedSquare += 1;
            }
          }
        }
      }
      return occupiedSquare == 1;
    }
  }
}
