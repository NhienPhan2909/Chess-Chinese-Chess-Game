import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * A class of european chess king piece
 * @author: Nhien Phan
 */

public class KingPiece extends ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  // dummy piece use to check if a square is threatened
  private final ChessPiece dummy = new ChessPiece(this.getSide(), "Dummy", this.getChessBoard());
  // firstMove check if this is the first move of the piece
  private boolean firstMove = true;
  
  // constructor
  public KingPiece(ChessGame.Side side, String label, ChessBoard board) {
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
   * Make the right castle move if possible and check if the right castle move
   * @param toRow: the row to move to
   * @param toCol: the column to move to
   */
  public boolean processRightCastleMoveLegal(int toRow, int toCol) {
    // check if the king is checked
    if (this.getChessBoard().squareThreatened(toRow, this.getColumn(), this))
      return false;
    // check if there are anything between king and rook
    if ((!super.getChessBoard().hasPiece(toRow, toCol))
          && (!super.getChessBoard().hasPiece(toRow, toCol - 1))
          && (super.getChessBoard().hasPiece(toRow, toCol + 1))
          && (super.getChessBoard().getPiece(toRow, toCol + 1) instanceof RookPiece)
          && (super.getChessBoard().getPiece(toRow, toCol + 1).getFirstMoveStatus())) {
      
      // check if king moved square to the right is threatened
      this.getChessBoard().addPiece(dummy, toRow, toCol);
      if (this.getChessBoard().squareThreatened(toRow, toCol, dummy))
        return false;
      this.getChessBoard().removePiece(toRow, toCol);
      
      // check if rook moved square to the right is threatened
      this.getChessBoard().addPiece(dummy, toRow, toCol - 1);
      if (this.getChessBoard().squareThreatened(toRow, toCol - 1, dummy))
        return false;
      this.getChessBoard().removePiece(toRow, toCol - 1);
      
      // process moveDone for King
      this.getChessBoard().addPiece(new KingPiece(this.getSide(), EuropeanPieceType.K.name, this.getChessBoard()), toRow, toCol);
      ((KingPiece) this.getChessBoard().getPiece(toRow, toCol)).setFirstMoveStatus(false);
      this.getChessBoard().removePiece(toRow, toCol - 2);
      
      // process moveDone for Rook
      getChessBoard().addPiece(new RookPiece(getSide(), EuropeanPieceType.R.name, getChessBoard()), toRow, toCol - 1);
      ((RookPiece) getChessBoard().getPiece(toRow, toCol + 1)).setFirstMoveStatus(false);
      getChessBoard().removePiece(toRow, toCol + 1);
      
      return true;
    }
    return false;
  }
  
  /*
   * Make the left castle move if possible and check if the left castle move has been made
   * @param toRow: the row to move to
   * @param toCol: the column to move to
   */
  public boolean processLeftCastleMoveLegal(int toRow, int toCol) {
    // check if the king is checked
    if (this.getChessBoard().squareThreatened(toRow, this.getColumn(), this))
      return false;
    // check if there are anything between king and rook
    if ((!super.getChessBoard().hasPiece(toRow, toCol - 1))
          && (!super.getChessBoard().hasPiece(toRow, toCol))
          && (!super.getChessBoard().hasPiece(toRow, toCol + 1))
          && (super.getChessBoard().hasPiece(toRow, toCol - 2))
          && (super.getChessBoard().getPiece(toRow, toCol - 2) instanceof RookPiece)
          && (super.getChessBoard().getPiece(toRow, toCol - 2).getFirstMoveStatus())) {
      
      // check if first square to the left is threatened
      this.getChessBoard().addPiece(dummy, toRow, toCol - 1);
      if (this.getChessBoard().squareThreatened(toRow, toCol - 1, dummy))
        return false;
      this.getChessBoard().removePiece(toRow, toCol - 1);
      
      // check if second square to the left is threatened
      this.getChessBoard().addPiece(dummy, toRow, toCol);
      if (this.getChessBoard().squareThreatened(toRow, toCol, dummy))
        return false;
      this.getChessBoard().removePiece(toRow, toCol);
      
      // check if third square to the left is threatened
      this.getChessBoard().addPiece(dummy, toRow, toCol + 1);
      if (this.getChessBoard().squareThreatened(toRow, toCol + 1, dummy))
        return false;
      this.getChessBoard().removePiece(toRow, toCol + 1);
      
      // process moveDone for King
      this.getChessBoard().addPiece(new KingPiece(this.getSide(), EuropeanPieceType.K.name, this.getChessBoard()), toRow, toCol);
      ((KingPiece) this.getChessBoard().getPiece(toRow, toCol)).setFirstMoveStatus(false);
      this.getChessBoard().removePiece(toRow, toCol + 2);
      
      // process moveDone for Rook
      getChessBoard().addPiece(new RookPiece(getSide(), EuropeanPieceType.R.name, getChessBoard()), toRow, toCol + 1);
      ((RookPiece) getChessBoard().getPiece(toRow, toCol + 1)).setFirstMoveStatus(false);
      getChessBoard().removePiece(toRow, toCol - 2);
      
      return true;
    }
    return false;
  }
  
  /*
   * Make a castle move if possible and check if a castle move has been made
   * @param toRow: the row to move to
   * @param toCol: the column to move to
   */
  public boolean processCastleMove(int toRow, int toCol) {
    if (isMoveToRightCastleCell(toRow, toCol)) return processRightCastleMoveLegal(toRow, toCol);
    else return processLeftCastleMoveLegal(toRow, toCol);
  }
  
  /*
   * Check if the destination is the destination of a catle move
   * @param toRow: the row to move to
   * @param toCol: the column to move to
   */
  public boolean isMoveToCastleCell(int toRow, int toCol) {
    return isMoveToLeftCastleCell(toRow, toCol) || isMoveToRightCastleCell(toRow, toCol);
  }
  
  /*
   * Check if the destination is the destination of the right catle move
   * @param toRow: the row to move to
   * @param toCol: the column to move to
   */
  private boolean isMoveToRightCastleCell(int toRow, int toCol) {
    // For North side
    if (this.getSide() == ChessGame.Side.NORTH) {
      return toRow == 7 && toCol == 6;
    } 
    // For South Side
    else if (this.getSide() == ChessGame.Side.SOUTH) {
      return toRow == 0 && toCol == 6;
    }
    return false;
  }
  
  /*
   * Check if the destination is the destination of the left catle move
   * @param toRow: the row to move to
   * @param toCol: the column to move to
   */
  private boolean isMoveToLeftCastleCell(int toRow, int toCol) {
    // For North side
    if (this.getSide() == ChessGame.Side.NORTH) {
      return toRow == 7 && toCol == 2;
    } 
    // For South side
    else if (this.getSide() == ChessGame.Side.SOUTH) {
      return toRow == 0 && toCol == 2;
    }
    return false;
  }
  
  /*
   * Return the array list of possible moves
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    // consider eight cells around the king
    List<Cell> posCell = new ArrayList<>(
                                         Arrays.asList(
                                                       new Cell(row, column - 1),
                                                       new Cell(row, column + 1),
                                                       new Cell(row + 1, column - 1),
                                                       new Cell(row + 1, column),
                                                       new Cell(row + 1, column + 1),
                                                       new Cell(row - 1, column - 1),
                                                       new Cell(row - 1, column),
                                                       new Cell(row - 1, column + 1)
                                                      )
                                        );
    // check each of those eight cells
    for (Cell cell : posCell) {
      // if the cell is within the board border
      if ((cell.getRow() >= 0)
            && (cell.getRow() < 8)
            && (cell.getCol() >= 0)
            && (cell.getCol() < 8)
            // if the cell is empty or there is an enemy piece at that cell
            && (((board.getPiece(cell.getRow(), cell.getCol())) == null)
                  || (board.getPiece(cell.getRow(), cell.getCol()).getSide() != this.getSide())))
        possibleMoves.add(new Cell(cell.getRow(), cell.getCol()));
    }
    return possibleMoves;
  }
  
  /*
   * Check if this move is a legal move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
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
  public void moveDone(int row, int column) {
    if ((this.isLegalMove(row, column))
          && (this.getChessBoard().hasPiece(row, column))
          && (this.getChessBoard().getPiece(row, column).getSide() != this.getSide()))
      super.getChessBoard().removePiece(row, column);
    
    this.getChessBoard().addPiece(new KingPiece(this.getSide(), EuropeanPieceType.K.name, this.getChessBoard()), row, column);
    
    ((KingPiece) this.getChessBoard().getPiece(row, column)).setFirstMoveStatus(false);
    
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
