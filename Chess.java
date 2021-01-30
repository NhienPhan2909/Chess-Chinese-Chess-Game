/*
 * A parent class of both european and xiangqi chess
 * @author: Nhien Phan
 */
public class Chess implements ChessGame {
  // store the side to move this turn
  ChessGame.Side properSide = ChessGame.Side.NORTH;
  
  /*
   * Check if it is legal to place a given piece (the correct turn for that side to play)
   * @param piece: the piece to check the side
   * @param row: the row of the piece's location
   * @param column: the column of the piece's location
   */
  public boolean legalPieceToPlay(ChessPiece piece, int row, int column) {
    return  piece.getSide() == properSide;
  }
  
  /*
   * Handle the move of a piece
   * return true if a move is made
   * return false if no move is made
   * @param piece: the piece to check the side
   * @param row: the row of the piece's destination
   * @param column: the column of the piece's destination
   */
  public boolean makeMove(ChessPiece piece, int toRow, int toColumn) {
    
    if (legalPieceToPlay(piece, piece.getRow(), piece.getColumn())) {
      // Allow the european chess king piece to make the castle move
      // check if this a european chess king piece
      if (piece instanceof KingPiece
            // check whether the king has moved
            && piece.getFirstMoveStatus()
            // check if the castle move for the king is legitimate
            && ((KingPiece) piece).isMoveToCastleCell(toRow, toColumn)
         ) {
        // if a castle move was made   
        if (((KingPiece) piece).processCastleMove(toRow, toColumn)) {
          changeTurn();
          return true;
        } else return false;
      }
      // process normal move if the piece is not doing castle move
      else if (piece.isLegalMove(toRow, toColumn)) {
        piece.moveDone(toRow, toColumn);
        changeTurn();
        return true;
      }
    }
    return false;
  }
  
  /*
   * Check if the player can change selection of piece
   * return true in this case since assumption is not tournament chess (which you have to move the piece you touch)
   * @param piece: the piece to check
   * @param row: the row of the piece's location
   * @param column: the column of the piece's location
   */
  public boolean canChangeSelection(ChessPiece piece, int row, int column) {
    return true;
  }
  
  /**
   * Return the number of rows
   */
  public int getNumRows() {
    return 0;
  }
  
  ;
  
  /**
   * Return the number of columns
   */
  public int getColumns() {
    return 0;
  }
  
  ;
  
  /**
   * Place all pieces on the board and start the game
   *
   * @param board: the chessboard to place pieces on
   */
  public void startGame(ChessBoard board) {
  }
  
  ;
  
  /** Change the side which is allowed to play after a move is made */
  private void changeTurn() {
    if (properSide == ChessGame.Side.SOUTH) {
      properSide = ChessGame.Side.NORTH;
    } else {
      properSide = ChessGame.Side.SOUTH;
    }
  }
}
