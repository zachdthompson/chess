package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;


public class OneSpaceMoves {

    private final int[][] possibleKnightMoves = {{2, -1}, {2, 1}, {-2, -1}, {-2, 1}};
    private final int[][] possibleKingMoves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};


    /**
     *
     * @param board The board of the game being played.
     * @param myPosition The current position of the piece to calculate moves for
     * @param type The piece type, to dictate what moves to calculate
     * @return An array of all possible moves for the given piece
     */
    public Collection<ChessMove> getValidMoves(ChessBoard board, ChessPosition myPosition, ChessPiece.PieceType type) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();

        if (type == ChessPiece.PieceType.KNIGHT) {

            validMoves.addAll(findAllMoves(myPosition, possibleKnightMoves));

        }
        else if (type == ChessPiece.PieceType.KING) {

            validMoves.addAll(findAllMoves(myPosition, possibleKingMoves));
        }

        return validMoves;
    }

    /**
     *
     * @param myPosition The position on the board to begin calculating from
     * @param possiblePieceMoves The 2D array of possible moves for that piece
     * @return An array of valid moves for the given piece
     */
    private ArrayList<ChessMove> findAllMoves(ChessPosition myPosition, int[][] possiblePieceMoves) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        for (int[] move : possiblePieceMoves) {
            int row = move[0];
            int col = move[1];

            int newRow = myPosition.getRow() + row;
            int newCol = myPosition.getColumn() + col;

            // If the move is out of bounds of the board, dont add it
            if (newRow > 8 || newRow < 0 || newCol > 8 || newCol < 0) {
                continue;
            }

            // If its valid, make a new position and add it.
            ChessPosition newPosition = new ChessPosition(row, col);
            ChessMove newMove = new ChessMove(myPosition, newPosition, null);
            validMoves.add(newMove);
        }
        return validMoves;
    }

}