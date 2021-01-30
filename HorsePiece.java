import java.util.ArrayList;

/*
 * A class of horse piece of xiangqi chess
 * @author: Nhien Phan
 */
public class HorsePiece extends KnightPiece {
  // possibleMoves stores all possible move for the piece
  private ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  // constructor
  public HorsePiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /*
   * Return the array list of eight cells that the horse might move to
   * @param board: The board the horse is on
   * @param row: the row the horse is on
   * @param column: the column the horse is on
   */
  @Override
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    
    // check if this is the only piece between two xiangqi king
    if (super.kingFacingKing()) {
      return possibleMoves;
    }
    
    // check possible move similar to european chess
    possibleMoves = super.move(board, row, column);
    
    return possibleMoves;
  }
  
  /**
   * This checks if Horse has any pieces in its path
   *
   * @param row    is the row the piece is to be moved to
   * @param column is the column the piece is to be moved to
   * @return a boolean representing if the path is clear
   */
  public boolean isClearPath(int row, int column) {
    
    // checks the path for two squares on the far right
    if (column - this.getColumn() == 2) {
      return getChessBoard().getPiece(getRow(), getColumn() + 1) == null;
    }
    
    // checks the path for two squares on the far left
    else if (column - this.getColumn() == -2) {
      return getChessBoard().getPiece(getRow(), getColumn() - 1) == null;
    }
    
    // checks the path for two squares on the top
    else if (row - this.getRow() == 2) {
      return getChessBoard().getPiece(getRow() + 1, getColumn()) == null;
    }
    
    // checks the path for two squares at the bottom
    else if (row - this.getRow() == -2) {
      return getChessBoard().getPiece(getRow() - 1, getColumn()) == null;
    } else {
      return false;
    }
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
      if (possibleMove.compareTo(targetSquare) == 0
            && isClearPath(toRow, toColumn)
            && super.isLegalMove(toRow, toColumn)) return true;
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
    return this.isLegalMove(row, column)
      && !getChessBoard().hasPiece(row, column);
  }
  
  /*
   * Check if this move is a legal capture move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
   */
  @Override
  public boolean isLegalCaptureMove(int row, int column) {
    return this.isLegalMove(row, column)
      && getChessBoard().squareThreatened(row, column, getChessBoard().getPiece(row, column));
  }
  
  /* 
   * Handle details after a move is done by deleting the piece at the old location and add a new piece at the new location
   * @param row: the destination's row
   * @param column: the destination's column
   */
  @Override
  public void moveDone(int row, int column) {
    if (this.isLegalMove(row, column)
          && this.getChessBoard().hasPiece(row, column)
          && this.getChessBoard().getPiece(row, column).getSide() != this.getSide())
      super.getChessBoard().removePiece(row, column);
    this.getChessBoard().addPiece(new HorsePiece(this.getSide(), XiangQiPieceType.H.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
