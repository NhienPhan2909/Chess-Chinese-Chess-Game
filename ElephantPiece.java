import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * A class of the elephant piece in xiangqi chess
 * @author: Nhien Phan
 */
public class ElephantPiece extends ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  // constructor
  public ElephantPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
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
      return possibleMoves;
    }
    
    // consider the four cells exactly two cells diagonally away from the elephant piece
    List<Cell> posCell = new ArrayList<>(
                                         Arrays.asList(
                                                       new Cell(row - 2, column - 2),
                                                       new Cell(row - 2, column + 2),
                                                       new Cell(row + 2, column - 2),
                                                       new Cell(row + 2, column + 2)
                                                      )
                                        );
    // check each of those four cells
    for (Cell cell : posCell) {
      // check if the cell is out of board border
      if (cell.getRow() >= 0
            && cell.getRow() < board.numRows()
            && cell.getCol() >= 0
            && cell.getCol() < board.numColumns()
            // check if the cell is empty or the piece on that cell belongs to the other side
            && (board.getPiece(cell.getRow(), cell.getCol()) == null
                  || board.getPiece(cell.getRow(), cell.getCol()).getSide() != this.getSide())) {
        // If the elephant belongs to South side - do not let it pass the middle row of the board
        if (this.getSide() == ChessGame.Side.SOUTH && cell.getRow() < 5) {
          possibleMoves.add(new Cell(cell.getRow(), cell.getCol()));
        }
        // If the elephant belongs to North side - do not let it pass the middle row of the board
        if (this.getSide() == ChessGame.Side.NORTH && cell.getRow() > 4) {
          possibleMoves.add(new Cell(cell.getRow(), cell.getCol()));
        }
      }
    }
    return possibleMoves;
  }
  
  /**
   * This checks if Elephant has any pieces in its path
   *
   * @param row    is the row the piece is to be moved to
   * @param column is the column the piece is to be moved to
   * @return a boolean representing if the path is clear
   */
  public boolean isClearPath(int row, int column) {
    
    // checks the path for the top right move
    if (column - this.getColumn() == 2 && row - this.getRow() == 2) {
      return getChessBoard().getPiece(getRow() + 1, getColumn() + 1) == null;
    }
    
    // checks the path for the bottom right move
    else if (column - this.getColumn() == 2 && row - this.getRow() == -2) {
      return getChessBoard().getPiece(getRow() - 1, getColumn() + 1) == null;
    }
    
    // checks the path for the top left move
    else if (column - this.getColumn() == -2 && row - this.getRow() == 2) {
      return getChessBoard().getPiece(getRow() + 1, getColumn() - 1) == null;
    }
    
    // checks the path for the bottom left move
    else if (column - this.getColumn() == -2 && row - this.getRow() == -2) {
      return getChessBoard().getPiece(getRow() - 1, getColumn() - 1) == null;
    } else {
      return false;
    }
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
                                  new ElephantPiece(this.getSide(), XiangQiPieceType.E.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
