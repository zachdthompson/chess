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

    public ChessBoard(ChessBoard boardToCopy) {
        for (int i = 0; i < 8; i++) {
            System.arraycopy(boardToCopy.board[i], 0, board[i], 0, 8);
        }
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

    public void movePiece(ChessMove move) {

        // Where the piece starts
        int startRow = move.getStartPosition().getRow() - 1;
        int startColumn = move.getStartPosition().getColumn() - 1;

        // Where it will end up
        int endRow = move.getEndPosition().getRow() - 1;
        int endColumn = move.getEndPosition().getColumn() - 1;

        // Make the move
        ChessPiece startPiece = getPiece(move.getStartPosition());
        // Check for pawn promotion
        if (move.getPromotionPiece() != null) {
            ChessPiece promotionPiece = new ChessPiece(startPiece.getTeamColor(), move.getPromotionPiece());
            board[endRow][endColumn] = promotionPiece;
        }
        else {
            board[endRow][endColumn] = startPiece;
        }

        // Delete the old piece
        board[startRow][startColumn] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    public ChessPosition getPiecePosition(ChessPiece piece) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == piece) {
                    return new ChessPosition(i + 1, j + 1);
                }
            }
        }
        return null;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        // Better method for resetting board
        for (ChessPiece[] row : board) {
            Arrays.fill(row, null);
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

        // Create pieces
        ChessPiece pawn = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
        ChessPiece rook = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
        ChessPiece knight = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        ChessPiece bishop = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        ChessPiece queen = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

}
