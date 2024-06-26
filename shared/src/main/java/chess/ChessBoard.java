package chess;

import chess.ChessPiece.PieceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private final ChessPiece[][] square = new ChessPiece[8][8];

    // Because there are so many pieces, its easier to just manually make an array of their start locations
    private final int[][] blackPawnPositions = {{6,0}, {6,1}, {6,2}, {6,3}, {6,4}, {6,5}, {6,6},{6,7}};
    private final int[][] blackRookPositions = {{7,0}, {7,7}};
    private final int[][] blackBishopPositions = {{7,2}, {7,5}};
    private final int[][] blackKnightPositions = {{7,1}, {7,6}};
    private final int[][] blackQueenPosition = {{7,3}};
    private final int[][] blackKingPosition = {{7,4}};

    private final int[][] whitePawnPositions = {{1,0}, {1,1}, {1,2}, {1,3}, {1,4}, {1,5}, {1,6}, {1,7}};
    private final int[][] whiteRookPositions = {{0,0}, {0,7}};
    private final int[][] whiteBishopPositions = {{0,2}, {0,5}};
    private final int[][] whiteKnightPositions = {{0,1}, {0,6}};
    private final int[][] whiteQueenPositions = {{0,3}};
    private final int[][] whiteKingPositions = {{0,4}};


    /*
    What a board looks like

       0 1 2 3 4 5 6 7
    7 |r|n|b|q|k|b|n|r| 7
    6 |p|p|p|p|p|p|p|p| 6
    5 | | | | | | | | | 5
    4 | | | | | | | | | 4
    3 | | | | | | | | | 3
    2 | | | | | | | | | 2
    1 |P|P|P|P|P|P|P|P| 1
    0 |R|N|B|Q|K|B|N|R| 0
       0 1 2 3 4 5 6 7
     */



    public ChessBoard() {
    }

    public ChessBoard(ChessBoard importBoard) {
        for (int i = 0; i < importBoard.square.length; i++) {
            // Idk what this does, IntelliJ recommended I use this instead of a double for loop...
            System.arraycopy(importBoard.square[i], 0, this.square[i], 0, 8);
        }
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {

        // Extract positions and subtract 1 due to indexing by 0
        int col = position.getColumn() - 1;
        int row = position.getRow() - 1;

        // Place it on the board

        this.square[row][col] = piece;
    }

    public void removePiece(ChessPosition position, ChessPiece piece) {

        // Extract positions and subtract 1 due to indexing by zero
        int col = position.getColumn() - 1;
        int row = position.getRow() - 1;
        this.square[row][col] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {

        // Extract positions and subtract 1 due to indexing by 0
        int col = position.getColumn() - 1;
        int row = position.getRow() - 1;

        return this.square[row][col];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        // Wipe the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.square[i][j] = null;
            }
        }

        // Place pieces where they need to go

        // Black Pieces
        // Pawns
        for (int[] position : blackPawnPositions) {
            addPieceOnSquare(position, PieceType.PAWN, ChessGame.TeamColor.BLACK);
        }
        // Rooks
        for (int[] position : blackRookPositions) {
            addPieceOnSquare(position, PieceType.ROOK, ChessGame.TeamColor.BLACK);
        }
        // Bishops
        for (int[] position : blackBishopPositions) {
            addPieceOnSquare(position, PieceType.BISHOP, ChessGame.TeamColor.BLACK);
        }
        // Knights
        for (int[] position : blackKnightPositions) {
            addPieceOnSquare(position, PieceType.KNIGHT, ChessGame.TeamColor.BLACK);
        }
        // King
        for (int[] position : blackKingPosition) {
            addPieceOnSquare(position, PieceType.KING, ChessGame.TeamColor.BLACK);
        }
        // Queen
        for (int[] position : blackQueenPosition) {
            addPieceOnSquare(position, PieceType.QUEEN, ChessGame.TeamColor.BLACK);
        }


        // White pieces
        // Pawns
        for (int[] position : whitePawnPositions) {
            addPieceOnSquare(position, PieceType.PAWN, ChessGame.TeamColor.WHITE);
        }
        // Rooks
        for (int[] position : whiteRookPositions) {
            addPieceOnSquare(position, PieceType.ROOK, ChessGame.TeamColor.WHITE);
        }
        // Bishops
        for (int[] position : whiteBishopPositions) {
            addPieceOnSquare(position, PieceType.BISHOP, ChessGame.TeamColor.WHITE);
        }
        // Knights
        for (int[] position : whiteKnightPositions) {
            addPieceOnSquare(position, PieceType.KNIGHT, ChessGame.TeamColor.WHITE);
        }
        // King
        for (int[] position : whiteKingPositions) {
            addPieceOnSquare(position, PieceType.KING, ChessGame.TeamColor.WHITE);
        }
        // Queen
        for (int[] position : whiteQueenPositions) {
            addPieceOnSquare(position, PieceType.QUEEN, ChessGame.TeamColor.WHITE);
        }

    }

    /**
     * Adds a piece on to a given square
     * Using a seperate function as my setup gives coords indexed by 0
     * @param position The position of the square to place the piece
     * @param pieceType The type of piece we are placing
     * @param teamColor The color of the team of the piece
     */
    private void addPieceOnSquare(int[] position, PieceType pieceType, ChessGame.TeamColor teamColor) {
        this.square[position[0]][position[1]] = new ChessPiece(teamColor, pieceType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(square, that.square);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(square);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "square=" + Arrays.toString(square) +
                '}';
    }
}
