import java.util.ArrayList;

/* author: Nhien Phan
 * A class for the european chess bishop
 */
public class BishopPiece extends ChessPiece implements DiagonalMove {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  //constructor
  public BishopPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /*
   * Return the array list of possible moves for bishop (implement the Diagonal Move interface)
   * @param board: The board the bishop is on
   * @param row: the row the bishop is on
   * @param column: the column the bishop is on
   */
  public ArrayList<Cell> moveDiagonal(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    int tempRow = row + 1, tempColumn = column - 1;
    // check the bottom left section of the board
    while (tempRow < 8 && tempColumn >= 0) {
      // if the cell is empty
      if (board.getPiece(tempRow, tempColumn) == null) {
        possibleMoves.add(new Cell(tempRow, tempColumn));
      }
      // if the cell has a piece of the same side
      else if (board.getPiece(tempRow, tempColumn).getSide() == this.getSide()) {
        break;
      }
      // if the cell has a piece of the other side
      else {
        possibleMoves.add(new Cell(tempRow, tempColumn));
        break;
      }
      tempRow++;
      tempColumn--;
    }
    
    tempRow = row - 1;
    tempColumn = column + 1;
    // check the top right section of the board
    while (tempRow >= 0 && tempColumn < 8) {
      // if the cell is empty
      if (board.getPiece(tempRow, tempColumn) == null) {
        possibleMoves.add(new Cell(tempRow, tempColumn));
      }
      // if the cell has a piece of the same side
      else if (board.getPiece(tempRow, tempColumn).getSide() == this.getSide()) {
        break;
      }
      // if the cell has a piece of the other side
      else {
        possibleMoves.add(new Cell(tempRow, tempColumn));
        break;
      }
      tempRow--;
      tempColumn++;
    }
    
    tempRow = row - 1;
    tempColumn = column - 1;
    // check the top left section of the board
    while (tempRow >= 0 && tempColumn >= 0) {
      // if the cell is empty
      if (board.getPiece(tempRow, tempColumn) == null) {
        possibleMoves.add(new Cell(tempRow, tempColumn));
      }
      // if the cell has a piece of the same side
      else if (board.getPiece(tempRow, tempColumn).getSide() == this.getSide()) {
        break;
      }
      // if the cell has a piece of the other side
      else {
        possibleMoves.add(new Cell(tempRow, tempColumn));
        break;
      }
      tempRow--;
      tempColumn--;
    }
    
    tempRow = row + 1;
    tempColumn = column + 1;
    // check the bottom right section of the board
    while (tempRow < 8 && tempColumn < 8) {
      if (board.getPiece(tempRow, tempColumn) == null) {
        possibleMoves.add(new Cell(tempRow, tempColumn));
      } 
      // if the cell has a piece of the same side
      else if (board.getPiece(tempRow, tempColumn).getSide() == this.getSide()) {
        break;
      }
      // if the cell has a piece of the other side
      else {
        possibleMoves.add(new Cell(tempRow, tempColumn));
        break;
      }
      tempRow++;
      tempColumn++;
    }
    return possibleMoves;
  }
  
  /** Return all possible moves */
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    return moveDiagonal(this.getChessBoard(), row, column);
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
      if ((possibleMove.compareTo(targetSquare) == 0) && (super.isLegalMove(toRow, toColumn))) return true;
    }
    return false;
  }
  
  /*
   * Check if this move is a legal non-capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public boolean isLegalNonCaptureMove(int row, int column) {
    return (this.isLegalMove(row, column)) && (!getChessBoard().squareThreatened(row, column, getChessBoard().getPiece(row, column)));
  }
  
  /*
   * Check if this move is a legal capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public boolean isLegalCaptureMove(int row, int column) {
    return (this.isLegalMove(row, column)) && (getChessBoard().squareThreatened(row, column, getChessBoard().getPiece(row, column)));
  }
  
  /* 
   * Handle details after a move is done by deleting the piece at the old location and add a new piece at the new location
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public void moveDone(int row, int column) {
    if ((this.isLegalMove(row, column)) && (this.getChessBoard().hasPiece(row, column)) && (this.getChessBoard().getPiece(row, column).getSide() != this.getSide()))
      super.getChessBoard().removePiece(row, column);
    this.getChessBoard().addPiece(new BishopPiece(this.getSide(), EuropeanPieceType.B.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
