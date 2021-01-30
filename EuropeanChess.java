/*
 * A class that implements european chess using swing
 * @author: Nhien Phan
 */
public class EuropeanChess extends Chess {
  
  /** Main method to begin playing */ 
  public static void main(String[] args) {
    EuropeanChess ec = new EuropeanChess();
    EuropeanChessDisplay ecd = new EuropeanChessDisplay();
    SwingChessBoard board = new SwingChessBoard(ecd, ec);
    
    ec.startGame(board);
  }
  
  /*
   * Put pieces in starting location on the chess board for one side
   * @param side: the side whose pieces are being put on board
   * @param board: the board the pieces is put on
   */
  private static void initChessEachSide(Side side, ChessBoard board) {
    // the row for all pieces except pawn piece
    int initRow = 0;
    // the row for pawn piece
    int pawnRow = 1;
    // change initiate row for South side
    if (side != Side.SOUTH) {
      initRow = 7;
      pawnRow = 6;
    }
    // put all pawns on the board
    for (int i = 0; i < 8; i++) {
      board.addPiece(new PawnPiece(side, EuropeanPieceType.P.name, board), pawnRow, i);
    }
    // put other piecces to board
    board.addPiece(new RookPiece(side, EuropeanPieceType.R.name, board), initRow, 0);
    board.addPiece(new RookPiece(side, EuropeanPieceType.R.name, board), initRow, 7);
    board.addPiece(new KnightPiece(side, EuropeanPieceType.N.name, board), initRow, 1);
    board.addPiece(new KnightPiece(side, EuropeanPieceType.N.name, board), initRow, 6);
    board.addPiece(new BishopPiece(side, EuropeanPieceType.B.name, board), initRow, 2);
    board.addPiece(new BishopPiece(side, EuropeanPieceType.B.name, board), initRow, 5);
    board.addPiece(new QueenPiece(side, EuropeanPieceType.Q.name, board), initRow, 3);
    board.addPiece(new KingPiece(side, EuropeanPieceType.K.name, board), initRow, 4);
  }
  
  /**
   * Place all pieces on the board and start the game
   *
   * @param board: the chessboard to place pieces on
   */
  public void startGame(ChessBoard board) {
    initChessEachSide(Side.SOUTH, board);
    initChessEachSide(Side.NORTH, board);
  }
  
  ;
  
  /**
   * Return the number of rows
   */
  public int getNumRows() {
    return 8;
  }
  
  ;
  
  /**
   * Return the number of columns
   */
  public int getColumns() {
    return 8;
  }
  
}
