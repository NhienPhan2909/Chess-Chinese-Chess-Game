import java.util.ArrayList;

/*
 * Interface for vertical move of pieces
 * @author: Nhien Phan
 */

public interface VerticalMove {
  /*
   * Return the array list of possible moves for bishop 
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  
  ArrayList<Cell> moveVertical(ChessBoard board, int row, int column);
}
