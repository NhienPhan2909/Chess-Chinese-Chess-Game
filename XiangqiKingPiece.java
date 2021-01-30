import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * A class of xiangqi chess king piece
 * @author: Nhien Phan
 */

public class XiangqiKingPiece extends ChessPiece {
  // possibleMoves stores all possible move for the piece
  private final ArrayList<Cell> possibleMoves = new ArrayList<>();
  
  // constructor
  public XiangqiKingPiece(ChessGame.Side side, String label, ChessBoard board) {
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
    // create a list of four cells around the king
    List<Cell> posCell = new ArrayList<>(
                                         Arrays.asList(
                                                       new Cell(row - 1, column),
                                                       new Cell(row + 1, column),
                                                       new Cell(row, column - 1),
                                                       new Cell(row, column + 1)
                                                      )
                                        );
    // check each cell
    for (Cell cell : posCell) {
      // if king is South side
      if ((this.getSide() == ChessGame.Side.SOUTH)
            // check  if cell creates facing kings situation
            && (!checkFacingKing(cell))
            // check if the cell is inside the three center columns and three top rows
            && (cell.getRow() >= 0)
            && (cell.getRow() < 3)
            && (cell.getCol() >= 3)
            && (cell.getCol() < 6)
            // check if there is a piece of the same side at that cell
            && (((board.getPiece(cell.getRow(), cell.getCol())) == null)
                  // check if there is an enemy piece at that cell
                  || (board.getPiece(cell.getRow(), cell.getCol()).getSide() != this.getSide()))) {
        possibleMoves.add(new Cell(cell.getRow(), cell.getCol()));
      }
      // if king is North side
      if ((this.getSide() == ChessGame.Side.NORTH)
            // check if cell creates facing kings situation
            && (!checkFacingKing(cell))
            // check if the cell is inside the three center columns and three bottom rows
            && (cell.getRow() >= 7)
            && (cell.getRow() < 10)
            && (cell.getCol() >= 3)
            && (cell.getCol() < 6)
            // check if there is a piece of the same side at that cell
            && (((board.getPiece(cell.getRow(), cell.getCol())) == null)
                  // check if there is an enemy piece at that cell
                  || (board.getPiece(cell.getRow(), cell.getCol()).getSide() != this.getSide()))) {
        possibleMoves.add(new Cell(cell.getRow(), cell.getCol()));
      }
    }
    return possibleMoves;
  }
  
  /*
   * Check if the moving the xiangqi to that cell will create facing kings situation
   * @param cell: the cell to check
   */
  public boolean checkFacingKing(Cell cell) {
    // count the empty cells and the king in column
    int countEmpty = 0;
    int countKing = 0;
    // check each cell in column
    // if king is south side
    if (this.getSide() == ChessGame.Side.SOUTH) {
      for (int i = this.getRow(); i < getChessBoard().numRows(); i++) {
        // check if other king is in the same column
        if (!getChessBoard().hasPiece(i, cell.getCol())) {
          countEmpty++;
        }
        // count empty cells in the same column
        if ((getChessBoard().getPiece(i, cell.getCol()) instanceof XiangqiKingPiece)
              && (getChessBoard().getPiece(i, cell.getCol()).getSide() != this.getSide())) {
          countKing++;
        }
        // return true if there is a king of other side and all other cells are empty
        if ((countKing == 1) && (countEmpty == getChessBoard().numRows() - this.getRow() - 1)) {
          return true;
        }
      }
    }
    // if king is north side
    if (this.getSide() == ChessGame.Side.NORTH) {
      for (int k = this.getRow(); k >= 0; k--) {
        // check if other king is in the same column
        if (!getChessBoard().hasPiece(k, cell.getCol())) {
          countEmpty++;
        }
        // count empty cells in the same column
        if ((getChessBoard().getPiece(k, cell.getCol()) instanceof XiangqiKingPiece)
              && (getChessBoard().getPiece(k, cell.getCol()).getSide() != this.getSide())) {
          countKing++;
        }
        // return true if there is a king of other side and all other cells are empty
        if ((this.getSide() == ChessGame.Side.NORTH) && (countKing == 1) && (countEmpty == this.getRow())) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  /*
   * Check if this move is a legal move (return true if it is legal (false if illegal)
   * @param row: the destination's row
   * @param column: the destination's column
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
    this.getChessBoard().addPiece(new XiangqiKingPiece(this.getSide(), XiangQiPieceType.X.name, this.getChessBoard()), row, column);
    this.getChessBoard().removePiece(this.getRow(), this.getColumn());
  }
}
