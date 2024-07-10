package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable {

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

    public void movePiece(ChessMove move) {

        // Where the piece starts
        int startRow = move.getStartPosition().getRow() - 1;
        int startColumn = move.getStartPosition().getColumn() - 1;

        // Where it will end up
        int endRow = move.getEndPosition().getRow() - 1;
        int endColumn = move.getEndPosition().getColumn() - 1;
        ChessPosition endPosition = new ChessPosition(endRow, endColumn);

        // Make the move
        ChessPiece startPiece = getPiece(move.getStartPosition());
        // Check for pawn promotion
        if (move.getPromotionPiece() != null) {
            ChessPiece promotionPiece = new ChessPiece(startPiece.getTeamColor(), move.getPromotionPiece());
            promotionPiece.setCurrentPosition(endPosition);
            board[endRow][endColumn] = promotionPiece;
        }
        else {
            board[endRow][endColumn] = startPiece;
            // Update position
            startPiece.setCurrentPosition(endPosition);
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

        // Pawns
        for (int i = 0; i < 8; i++) {
            // Set their position
            ChessPosition position = new ChessPosition(frontRow, i);
            ChessPiece pawn = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
            // Place them
            board[frontRow][i] = pawn;
            pawn.setCurrentPosition(position);
        }

        // Create pieces
        ChessPiece rook1 = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
        ChessPiece rook2 = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
        ChessPiece knight1 = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        ChessPiece knight2 = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        ChessPiece bishop1 = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        ChessPiece bishop2 = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        ChessPiece queen = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);

        // Back row
        // For each piece we generate its position, then place it

        // 7
        ChessPosition rookPosition1 = new ChessPosition(backRow, 7);
        rook1.setCurrentPosition(rookPosition1);
        board[backRow][7] = rook1;

        // 6
        ChessPosition knightPosition1 = new ChessPosition(backRow, 6);
        knight1.setCurrentPosition(knightPosition1);
        board[backRow][6] = knight1;

        // 5
        ChessPosition bishopPosition1 = new ChessPosition(backRow, 5);
        bishop1.setCurrentPosition(bishopPosition1);
        board[backRow][5] = bishop1;

        // 4
        ChessPosition kingPosition = new ChessPosition(backRow, 4);
        board[backRow][4] = king;

        // 3
        ChessPosition queenPosition = new ChessPosition(backRow, 3);
        board[backRow][3] = queen;

        // 2
        ChessPosition bishopPosition2 = new ChessPosition(backRow, 2);
        bishop2.setCurrentPosition(bishopPosition2);
        board[backRow][2] = bishop2;

        //1
        ChessPosition knightPosition2 = new ChessPosition(backRow, 1);
        knight2.setCurrentPosition(knightPosition2);
        board[backRow][1] = knight2;

        // 0
        ChessPosition rookPosition2 = new ChessPosition(backRow, 0);
        rook2.setCurrentPosition(rookPosition2);
        board[backRow][0] = rook2;
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

    @Override
    public ChessBoard clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (ChessBoard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
