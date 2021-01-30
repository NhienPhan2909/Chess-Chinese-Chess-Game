/*
 * A class of the cell on the chess board
 * @author: Nhien Phan
 */
public class Cell implements Comparable<Cell> {
  
  // the row of the cell  
  private int row;
  // teh column of the cell
  private int col;
  
  // constructor
  public Cell() {
  }
  
  // constructor
  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }
  
  /** Return the row of the cell */
  public int getRow() {
    return row;
  }
  
  /* 
   * Set the new row for the cell
   * @param row: new row of cell
   */
  public void setRow(int row) {
    this.row = row;
  }
  
  /** Return the column of the cell */
  public int getCol() {
    return col;
  }
  
  /* 
   * Set the new column for the cell
   * @param column: new column of cell
   */
  public void setCol(int col) {
    this.col = col;
  }
  
  /** Print out the row and column of this cell */
  @Override
  public String toString() {
    return "Cell{" +
      "row=" + row +
      ", col=" + col +
      '}';
  }
  
  /** 
   * Implement compareTo -  compare another cell with this cell based on the value of row and column
   * return 0 if two cell is identical
   * return 1 if this cell has bigger row and column
   * return -1 if this cell has smaller row and column
   * @param cell: a new cell to compare with this cell
   */
  public int compareTo(Cell cell) {
    if ((cell.getRow() == this.getRow()) && (cell.getCol() == this.getCol())) return 0;
    else if ((cell.getRow() > this.getRow()) && (cell.getCol() > this.getCol())) return 1;
    else return -1;
  }
}
