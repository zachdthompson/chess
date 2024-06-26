package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RecursiveMoves {

    private final int[][] possibleStraightMoves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private final int[][] possibleDiagonalMoves = {{1, -1}, {1, 1}, {-1, -1}, {-1, 1}};


    /**
     *
     * @param board The board of the game being played.
     * @param myPosition The current position of the piece to calculate moves for
     * @param type The piece type, to dictate what moves to calculate
     * @return An array of all possible moves for the given piece
     */
    public Collection<ChessMove> getValidMoves(
            ChessBoard board,
            ChessPosition myPosition,
            ChessPiece.PieceType type
    ) {

        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // Two if statements should allow the queen to hit both horizontal and diagonal moves
        if (type == ChessPiece.PieceType.ROOK || type == ChessPiece.PieceType.QUEEN) {
            for (int[] move : possibleStraightMoves) {
                ArrayList<ChessMove> directionMoves = recursiveFindAllMoves(board, myPosition, null, move);
            }
        }

        if (type == ChessPiece.PieceType.BISHOP || type == ChessPiece.PieceType.QUEEN) {
            for (int[] move : possibleDiagonalMoves) {

                validMoves.addAll(recursiveFindAllMoves(board, myPosition, null, move));
            }
        }

        return validMoves;
    }

    /**
     * Recursively iterates in a single direction until the piece is out of bounds.
     * This will find all valid moves along the way.
     * @param myPosition The position we are currently looking at
     * @param currentMove The direction to continue heading
     * @return An Array of all the moves in a single direction
     */
    private ArrayList<ChessMove> recursiveFindAllMoves(
            ChessBoard board,
            ChessPosition myPosition,
            ChessPosition newPosition,
            int[] currentMove
    ) {

        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>();

        if (newPosition == null) {
            newPosition = myPosition;
        }
        // Get current location
        int currRow = newPosition.getRow() - 1;
        int currCol = newPosition.getColumn() - 1;

        // Get new location
        int newRow = currRow + currentMove[0];
        int newColumn = currCol + currentMove[1];


        // Check for out of bounds
        if (newRow > 7 || newColumn > 7 || newRow < 0 || newColumn < 0) {
            return allMoves;
        }
        else {
            // Create the new position and
            newPosition = new ChessPosition(newRow + 1, newColumn + 1);
            ChessMove validMove = new ChessMove(myPosition, newPosition, null);

            // Check for collision with another friendly piece
            if (board.getPiece(newPosition) != null) {
                // If its friendly, dont add the space
                if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    return allMoves;
                }
                // If its enemy, add the space but stop path finding.
                else {
                    allMoves.add(validMove);
                    return allMoves;
                }

            }

            // Loop again
            allMoves.addAll(recursiveFindAllMoves(board, myPosition, newPosition, currentMove));
            allMoves.add(validMove);
        }


        return allMoves;
    }
}
