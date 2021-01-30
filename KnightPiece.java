import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * A class of european chess knight piece
 * @author: Nhien Phan
 */

public class KnightPiece extends ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  //constructor
  public KnightPiece(ChessGame.Side side, String label, ChessBoard board) {
    super(side, label, board);
  }
  
  /*
   * Return the array list of possible moves for piece 
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  public ArrayList<Cell> move(ChessBoard board, int row, int column) {
    possibleMoves.clear();
    // create a list of eight cells that the knight can move to
    List<Cell> posCell = new ArrayList<>(
                                         Arrays.asList(
                                                       new Cell(row + 1, column - 2),
                                                       new Cell(row + 1, column + 2),
                                                       new Cell(row + 2, column - 1),
                                                       new Cell(row + 2, column + 1),
                                                       new Cell(row - 1, column - 2),
                                                       new Cell(row - 1, column + 2),
                                                       new Cell(row - 2, column - 1),
                                                       new Cell(row - 2, column + 1)
                                                      )
                                        );
    // check each cell in that list
    for (Cell cell : posCell) {
      // check if the cell is in the border of the board
      if ((cell.getRow() >= 0)
            && (cell.getRow() < 8)
            && (cell.getCol() >= 0)
            && (cell.getCol() < 8)
            // check if the cell is empty or if there is a piece of the other side
            && (((board.getPiece(cell.getRow(), cell.getCol())) == null)
                  || (board.getPiece(cell.getRow(), cell.getCol()).getSide() != this.getSide())))
        possibleMoves.add(new Cell(cell.getRow(), cell.getCol()));
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
  public void moveDone(int row, int column) {
    if ((this.isLegalMove(row, column)) && (this.getChessBoard().hasPiece(row, column)) && (this.getChessBoard().getPiece(row, column).getSide() != this.getSide()))
      super.getChessBoard().removePiece(row, column);
    this.getChessBoard().addPiece(new KnightPiece(this.getSide(), EuropeanPieceType.N.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
