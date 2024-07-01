package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private final ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() -1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() -1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        // Reset Board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        for (ChessGame.TeamColor color : ChessGame.TeamColor.values()) {
            placePieces(color);
        }
    }


    private void placePieces(ChessGame.TeamColor teamColor) {

        int frontRow = 1;
        int backRow = 0;

        if (teamColor == ChessGame.TeamColor.BLACK) {
            frontRow = 6;
            backRow = 7;
        }

        ChessPiece pawn = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
        ChessPiece rook = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
        ChessPiece knight = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        ChessPiece bishop = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        ChessPiece queen = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
        ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);

        // Pawns
        for (int i = 0; i < 8; i++) {
            board[frontRow][i] = pawn;
        }

        // Back row
        board[backRow][7] = rook;
        board[backRow][6] = knight;
        board[backRow][5] = bishop;
        board[backRow][4] = king;
        board[backRow][3] = queen;
        board[backRow][2] = bishop;
        board[backRow][1] = knight;
        board[backRow][0] = rook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
