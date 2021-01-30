import java.util.ArrayList;

/*
 * A class of european chess pawn piece
 * @author: Nhien Phan
 */

public class PawnPiece extends ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  // firstMove check if this is the first move of the piece
  private boolean firstMove = true;
  
  //constructor
  public PawnPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /** Check first move status */
  public boolean getFirstMoveStatus() {
    return this.firstMove;
  }
  
  /*
   * Set first move status
   * @param status: determine if the king has moved
   * true - the king has not moved
   * false - the king has moved
   */
  public void setFirstMoveStatus(boolean status) {
    this.firstMove = status;
  }
  
  /* 
   * Upgrade to another piece if a pawn reach the other end of the board
   * @param toRow: the row of the piece's location
   * @param toCol: the column of the piece's location
   */
  public void upgrade(int toRow, int toCol) {
    
    // the new piece that will replace the pawn
    ChessPiece upgradePiece;
    String upgradePieceString = javax.swing.JOptionPane.showInputDialog("Enter your upgrade choice: Queen / Knight / Bishop / Rook");
    switch (upgradePieceString) {
      case "Knight":
        upgradePiece = new KnightPiece(this.getSide(), EuropeanPieceType.N.name, this.getChessBoard());
        break;
      case "Bishop":
        upgradePiece = new BishopPiece(this.getSide(), EuropeanPieceType.B.name, this.getChessBoard());
        break;
      case "Rook":
        upgradePiece = new RookPiece(this.getSide(), EuropeanPieceType.R.name, this.getChessBoard());
        break;
      default:
        upgradePiece = new QueenPiece(this.getSide(), EuropeanPieceType.Q.name, this.getChessBoard());
        break;
    }
    // remove the pawn
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
    // add the new piece to the same location
    this.getChessBoard().addPiece(upgradePiece, toRow, toCol);
  }
  
   /*
   * Return the array list of possible moves for piece
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    // check move for white side
    if (this.getSide() == ChessGame.Side.NORTH) {
      // if reach the end of board
      if (row == 0)
        return possibleMoves;
      // if the cell is empty
      if (this.getChessBoard().getPiece(row - 1, column) == null) {
        possibleMoves.add(new Cell(row - 1, column));
        // if first move
        if (row == 6) {
          if (this.getChessBoard().getPiece(4, column) == null)
            possibleMoves.add(new Cell(4, column));
        }
      }
      // if the cell has a piece of the other side
      if ((column > 0) && (this.getChessBoard().getPiece(row - 1, column - 1) != null)
            && (this.getChessBoard().getPiece(row - 1, column - 1).getSide() != this.getSide()))
        possibleMoves.add(new Cell(row - 1, column - 1));
      if ((column < 7) && (this.getChessBoard().getPiece(row - 1, column + 1) != null)
            && (this.getChessBoard().getPiece(row - 1, column + 1).getSide() != this.getSide()))
        possibleMoves.add(new Cell(row - 1, column + 1));
    }
    
    // check move for black side
    else {
      // if reach the end of the board
      if (row == 8)
        return possibleMoves;
      // if the cell is empty
      if (this.getChessBoard().getPiece(row + 1, column) == null) {
        possibleMoves.add(new Cell(row + 1, column));
        if (row == 1) {
          // if first move
          if (this.getChessBoard().getPiece(3, column) == null)
            possibleMoves.add(new Cell(3, column));
        }
      }
      // if the cell has a piece of the other side
      if ((column > 0) && (this.getChessBoard().getPiece(row + 1, column - 1) != null)
            && (this.getChessBoard().getPiece(row + 1, column - 1).getSide() != this.getSide()))
        possibleMoves.add(new Cell(row + 1, column - 1));
      if ((column < 7) && (this.getChessBoard().getPiece(row + 1, column + 1) != null)
            && (this.getChessBoard().getPiece(row + 1, column + 1).getSide() != this.getSide()))
        possibleMoves.add(new Cell(row + 1, column + 1));
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
  public boolean isLegalNonCaptureMove(int row, int column) {
    return (this.isLegalMove(row, column))
      && (!getChessBoard().squareThreatened(row, column, getChessBoard().getPiece(row, column)));
  }
  
  /*
   * Check if this move is a legal capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public boolean isLegalCaptureMove(int row, int column) {
    return (this.isLegalMove(row, column))
      && (getChessBoard().squareThreatened(row, column, getChessBoard().getPiece(row, column)));
  }
  
  /* 
   * Handle details after a move is done by deleting the piece at the old location and add a new piece at the new location
   * @param row: the destination's row
   * @param column: the destination's column
   */
  public void moveDone(int toRow, int toCol) {
    if ((this.isLegalMove(toRow, toCol))
          && (this.getChessBoard().hasPiece(toRow, toCol))
          && (this.getChessBoard().getPiece(toRow, toCol).getSide() != this.getSide()))
      super.getChessBoard().removePiece(toRow, toCol);
    
    this.getChessBoard().addPiece(new PawnPiece(this.getSide(), EuropeanPieceType.P.name, this.getChessBoard()), toRow, toCol);
    
    ((PawnPiece) this.getChessBoard().getPiece(toRow, toCol)).setFirstMoveStatus(false);
    
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
    
    if ((toRow == 0) || (toRow == 7))
      this.upgrade(toRow, toCol);
  }
}
