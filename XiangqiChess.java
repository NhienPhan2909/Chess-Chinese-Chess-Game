/*
 * A class that implements xiangqi chess using swing
 * @author: Nhien Phan
 */
public class XiangqiChess extends Chess {
  
  /** Main method to begin playing */
  public static void main(String[] args) {
    XiangqiChess xiangqi = new XiangqiChess();
    XiangqiChessDisplay ecd = new XiangqiChessDisplay();
    SwingChessBoard board = new SwingChessBoard(ecd, xiangqi);
    
    xiangqi.startGame(board);
  }
  
  /*
   * Put pieces in starting location on the chess board for one side
   * @param side: the side whose pieces are being put on board
   * @param board: the board the pieces is put on
   */
  private static void initChessEachSide(Side side, ChessBoard board) {
    int initRow = 0;
    int cannonRow = 2;
    int soldierRow = 3;
    if (side != Side.SOUTH) {
      initRow = 9;
      cannonRow = 7;
      soldierRow = 6;
    }
    for (int i = 0; i < 9; i += 2) {
      board.addPiece(new SoldierPiece(side, XiangQiPieceType.S.name, board), soldierRow, i);
    }
    board.addPiece(new CannonPiece(side, XiangQiPieceType.C.name, board), cannonRow, 1);
    board.addPiece(new CannonPiece(side, XiangQiPieceType.C.name, board), cannonRow, 7);
    board.addPiece(new XiangqiRookPiece(side, XiangQiPieceType.R.name, board), initRow, 0);
    board.addPiece(new XiangqiRookPiece(side, XiangQiPieceType.R.name, board), initRow, 8);
    board.addPiece(new HorsePiece(side, XiangQiPieceType.H.name, board), initRow, 1);
    board.addPiece(new HorsePiece(side, XiangQiPieceType.H.name, board), initRow, 7);
    board.addPiece(new ElephantPiece(side, XiangQiPieceType.E.name, board), initRow, 2);
    board.addPiece(new ElephantPiece(side, XiangQiPieceType.E.name, board), initRow, 6);
    board.addPiece(new GuardPiece(side, XiangQiPieceType.G.name, board), initRow, 3);
    board.addPiece(new GuardPiece(side, XiangQiPieceType.G.name, board), initRow, 5);
    board.addPiece(new XiangqiKingPiece(side, XiangQiPieceType.X.name, board), initRow, 4);
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
    return 10;
  }
  
  ;
  
  /**
   * Return the number of columns
   */
  public int getColumns() {
    return 9;
  }
}
