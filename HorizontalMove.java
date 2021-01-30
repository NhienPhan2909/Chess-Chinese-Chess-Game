import java.util.ArrayList;
/*
 * Interface for horizontal move of pieces
 * @author: Nhien Phan
 */
public interface HorizontalMove {
  /*
   * Return the array list of possible horizontal moves
   * @param board: The board the piece is on
   * @param row: the row the piece is on
   * @param column: the column the piece is on
   */
  
  ArrayList<Cell> moveHorizontal(ChessBoard board, int row, int column);
}
