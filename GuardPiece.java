import java.util.ArrayList;

/*
 * A class of the guard piece in xiangqi chess
 * @author: Nhien Phan
 */

public class GuardPiece extends ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  // constructor
  public GuardPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /*
   * Return the array list of possible moves for guard
   * @param board: The board the guard is on
   * @param row: the row the guard is on
   * @param column: the column the guard is on
   */
  @Override
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    
    // check if this is the only piece between two xiangqi king
    if (super.kingFacingKing()) {
      return possibleMoves;
    }
    
    switch (this.getSide()) {
      case SOUTH: {
        // Guards of South side
        // if the guard is not at the center of its legitimate area
        if (this.getRow() != 1 && this.getColumn() != 4) {
          possibleMoves.add(new Cell(1, 4));
        }
        // if the guard is at the center of its legitimate area
        else {
          calculate4DirectionsAvailableMoves(this.getRow(), this.getColumn());
        }
        break;
      }
      case NORTH: {
        // Guards of North side
        // if the guard is not at the center of its legitimate area
        if (this.getRow() != 8 && this.getColumn() != 4) {
          possibleMoves.add(new Cell(8, 4));
        }
        // if the guard is at the center of its legitimate area
        else {
          calculate4DirectionsAvailableMoves(this.getRow(), this.getColumn());
        }
        break;
      }
      default:
        break;
    }
    return possibleMoves;
  }
  
  /*
   * Check if this move is a legal move (return true if it is legal (false if illegal)
   * @param toRow: the destination's row
   * @param toColumn: the destination's column
   */
  @Override
  public boolean isLegalMove(int toRow, int toColumn) {
    Cell targetSquare = new Cell(toRow, toColumn);
    move(this.getChessBoard(), this.getRow(), this.getColumn());
    for (Cell possibleMove : possibleMoves) {
      if ((possibleMove.compareTo(targetSquare) == 0)
            && (super.isLegalMove(toRow, toColumn))) return true;
    }
    return false;
  }
  
  /*
   * Check if this move is a legal non-capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  @Override
  public boolean isLegalNonCaptureMove(int row, int column) {
    return (this.isLegalMove(row, column))
      && (!getChessBoard().hasPiece(row, column));
  }
  
  /*
   * Check if this move is a legal capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  @Override
  public boolean isLegalCaptureMove(int row, int column) {
    return (this.isLegalMove(row, column))
      && (getChessBoard().squareThreatened(row, column, getChessBoard().getPiece(row, column)));
  }
  
  /* 
   * Handle details after a move is done by deleting the piece at the old location and add a new piece at the new location
   * @param row: the destination's row
   * @param column: the destination's column
   */
  @Override
  public void moveDone(int row, int column) {
    if ((this.isLegalMove(row, column)) && (this.getChessBoard().hasPiece(row, column)) && (this.getChessBoard().getPiece(row, column).getSide() != this.getSide()))
      super.getChessBoard().removePiece(row, column);
    this.getChessBoard().addPiece(new GuardPiece(this.getSide(), XiangQiPieceType.G.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
  
  /*
   * Add four corner cells to the possible moves of guard if the guard is at the center of its legitimate area
   * @param row: the row of guard's location
   * @param col: the column of guard's location
   */
  private void calculate4DirectionsAvailableMoves(int row, int col) {
    // check top right
    if (!super.getChessBoard().hasPiece(row - 1, col + 1)
          || super.getChessBoard().getPiece(row - 1, col + 1).getSide() != this.getSide()) {
      possibleMoves.add(new Cell(row - 1, col + 1));
    }
    // check top left
    if (!super.getChessBoard().hasPiece(row - 1, col - 1)
          || super.getChessBoard().getPiece(row - 1, col - 1).getSide() != this.getSide()) {
      possibleMoves.add(new Cell(row - 1, col - 1));
    }
    // check bottom right
    if (!super.getChessBoard().hasPiece(row + 1, col + 1)
          || super.getChessBoard().getPiece(row + 1, col + 1).getSide() != this.getSide()) {
      possibleMoves.add(new Cell(row + 1, col + 1));
    }
    // check bottom left
    if (!super.getChessBoard().hasPiece(row + 1, col - 1)
          || super.getChessBoard().getPiece(row + 1, col - 1).getSide() != this.getSide()) {
      possibleMoves.add(new Cell(row + 1, col - 1));
    }
  }
}
