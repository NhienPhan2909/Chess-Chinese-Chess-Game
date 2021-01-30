import java.util.ArrayList;

/*
 * A class of xiangqi chess soldier piece
 * @author: Nhien Phan
 */

public class SoldierPiece extends ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  // constructor
  public SoldierPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /*
   * Return the array list of possible moves
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  @Override
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    
    // check if this is the only piece between two xiangqi king
    if (super.kingFacingKing()) {
      if (this.getSide() == ChessGame.Side.NORTH) {
        possibleMoves.add(new Cell(row - 1, column));
      }
      if (this.getSide() == ChessGame.Side.SOUTH) {
        possibleMoves.add(new Cell(row + 1, column));
      }
      return possibleMoves;
    }
    
    switch (this.getSide()) {
      // if this piece belongs to North side
      case NORTH: {
        possibleMoves.add(new Cell(row - 1, column));
        // if the piece is on the top half of the board - move horizontally
        if (row < 5) {
          possibleMoves.add(new Cell(row, column + 1));
          possibleMoves.add(new Cell(row, column - 1));
        }
        break;
      }
      // if this piece belongs to South side
      case SOUTH: {
        possibleMoves.add(new Cell(row + 1, column));
        // if the piece is on the bottom half of the board - move horizontally
        if (row > 4) {
          possibleMoves.add(new Cell(row, column + 1));
          possibleMoves.add(new Cell(row, column - 1));
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
    if ((this.isLegalMove(row, column)) && (this.getChessBoard().hasPiece(row, column))
          && (this.getChessBoard().getPiece(row, column).getSide() != this.getSide()))
      super.getChessBoard().removePiece(row, column);
    this.getChessBoard().addPiece(new SoldierPiece(this.getSide(), XiangQiPieceType.S.name,
                                                   this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
