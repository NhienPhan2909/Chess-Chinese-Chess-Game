import java.util.ArrayList;

/*
 * A parent class for all european and xiangqi chess pieces
 * @author: Nhien Phan
 */
public class ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  // side stores the side of the piece
  private final ChessGame.Side side;
  // label stores the label of the piece
  private final String label;
  // board stores the chess board this piece is on
  private final ChessBoard board;
  // row and column stores the position of the piece
  private int row;
  private int column;
  
  // constructor
  public ChessPiece(ChessGame.Side side, String label, ChessBoard board) {
    this.side = side;
    this.label = label;
    this.board = board;
  }
  
  /** Check if a piece has been moved */
  public boolean getFirstMoveStatus() {
    // firstMove check if this is the first move of the piece
    return true;
  }
  
  /** Return the side of this piece */
  public ChessGame.Side getSide() {
    return this.side;
  }
  
  /** Return the label of this piece */
  public String getLabel() {
    return this.label;
  }
  
  /** Return the icon of this piece */
  public Object getIcon() {
    return null;
  }
  
  /** Return the location of this piece on chess board */
  public Cell getLocation() {
    return new Cell(this.row, this.column);
  }
  
  /*
   * Set new location for this piece on chess board
   * @param row: the row of new location
   * @param column: the column of new location
   */
  public void setLocation(int row, int column) {
    this.row = row;
    this.column = column;
  }
  
  /*
   * Check if this move is a legal move (return true if it is legal (false if illegal)
   * @param toRow: the destination's row
   * @param toColumn: the destination's column
   */
  public boolean isLegalMove(int toRow, int toColumn) {
    return !((toRow >= getChessBoard().numRows()) | (toColumn >= getChessBoard().numColumns()));
  }
  
  /** Return the chess board this piece is on */
  public ChessBoard getChessBoard() {
    return this.board;
  }
  
  /** Return the row this piece is on */
  public int getRow() {
    return this.row;
  }
  
  /** Return the column this piece is on */
  public int getColumn() {
    return this.column;
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
  }
  
  /*
   * Return the array list of possible moves for bishop (implement the Diagonal Move interface)
   * @param board: The board the bishop is on
   * @param row: the row the bishop is on
   * @param column: the column the bishop is on
   */
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    return null;
  }
  
  /*
   * Check if this is the only piece between two xiangqi kings
   */
  public boolean kingFacingKing() {
    // count the empty rows in the column of this piece
    int countRows = 0;
    // int count the number of kings in column
    int countKings = 0;
    for (int i = 0; i < getChessBoard().numRows(); i++) {
      if (board.getPiece(i, this.getColumn()) instanceof XiangqiKingPiece) {
        countKings++;
      }
      if ((board.getPiece(i, this.getColumn()) == null)
            || (board.getPiece(i, this.getColumn()) == this)) {
        countRows++;
      }
    }
    if ((countRows == getChessBoard().numRows() - 2) && (countKings == 2)) {
      return true;
    }
    return false;
  }
}
