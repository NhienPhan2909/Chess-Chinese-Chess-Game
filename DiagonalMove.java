import java.util.ArrayList;

/*
 * Interface for diagonal move of pieces
 * @author: Nhien Phan
 */
public interface DiagonalMove {
  /*
   * Return the array list of possible moves for bishop 
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  ArrayList<Cell> moveDiagonal(ChessBoard board, int row, int column);
}
