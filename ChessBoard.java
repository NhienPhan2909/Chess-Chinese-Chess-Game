public interface ChessBoard {

    /**
     * Returns the rules of the game.
     *
     * @return the rules of the game
     */
    public ChessGame getGameRules();

    /**
     * Returns the number of rows in the board.
     *
     * @return the number of rows
     */
    public int numRows();

    /**
     * Returns the number of columns in the board.
     *
     * @return the number of columns
     */
    public int numColumns();

    /**
     * Adds a piece to the board at the desired location.  Any piece currently
     * at that location is lost.
     *
     * @param piece the piece to add
     * @param row   the row for the piece
     * @param col   the column for the piece
     */
    public void addPiece(final ChessPiece piece, final int row, final int col);

    /**
     * Removes a piece from the board
     *
     * @param row the row of the piece
     * @param col the column of the piece
     * @return the piece removed of null if there was no piece at that square
     */
    public ChessPiece removePiece(final int row, final int col);

    /**
     * Returns true if there is a piece at a specific location of the board.
     *
     * @param row the row to examine
     * @param col the column to examine
     * @return true if there is a piece a this row and column and false
     * if the square is empty
     */
    public boolean hasPiece(int row, int col);

    /**
     * Returns the chess piece at a specific location on the board.
     *
     * @param row the row for the piece
     * @param col the column for the piece
     * @return the piece at the row and column or null if there is no piece there.
     */
    public ChessPiece getPiece(int row, int col);

    /**
     * Returns true if a particular square is threatened by an opposing piece.
     *
     * @param row    the row of the square
     * @param column the column of the square
     * @param piece  a piece of the game
     * @return true if the square can be attacked by a piece of an opposing side as the parameter piece
     */
    public boolean squareThreatened(int row, int column, ChessPiece piece);
}
