package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class MoveGenerator {

    // Vertical Moves
    private final int[] up = {1, 0};
    private final int[] down = {-1, 0};
    private final int[] left = {0, -1};
    private final int[] right = {0, 1};
    protected final int[][] verticalMoves = {up, left, down, right};

    // Diagonal Moves
    private final int[] upLeft = {1, -1};
    private final int[] upRight = {1, 1};
    private final int[] downLeft = {-1, -1};
    private final int[] downRight = {-1, 1};
    protected final int[][] diagonalMoves = {upLeft, upRight, downLeft, downRight};

    // All Straight moves
    protected final int[][] allStraightMoves = {up, left, down, right, upLeft, upRight, downLeft, downRight};


    // Default generator, will be overridden
    public Collection<ChessMove> generateMoves(
            ChessBoard board,
            ChessPosition startingPosition
    ) {
        return new ArrayList<>();
    }

    protected boolean collisionDetected(ChessBoard board, ChessMove move) {
        return board.getPiece(move.getEndPosition()) != null;
    }


    protected boolean outOfBounds(ChessMove move) {
        int col = move.getEndPosition().getColumn();
        int row = move.getEndPosition().getRow();

        return row > 8 || col > 8 || row < 1 || col < 1;
    }

    protected boolean attackDetected(ChessBoard board, ChessMove move) {
        if (collisionDetected(board, move)) {
            ChessPiece startPiece = board.getPiece(move.getStartPosition());
            ChessPiece endPiece = board.getPiece(move.getEndPosition());

            return startPiece.getTeamColor() != endPiece.getTeamColor();
        }
        return false;
    }


    protected Collection<ChessMove> findAllValidMoves(
            ChessBoard board,
            ChessPosition startingPosition,
            ChessPosition currentPosition,
            int[] direction,
            boolean recursive
    ) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        // First iteration
        if (currentPosition == null) {
            currentPosition = startingPosition;
        }

        // Create the move
        int newRow = currentPosition.getRow() + direction[0];
        int newCol = currentPosition.getColumn() + direction[1];
        ChessPosition newPosition = new ChessPosition(newRow, newCol);

        ChessMove newMove = new ChessMove(startingPosition, newPosition, null);

        // Check if it's out of bounds
        if (outOfBounds(newMove)) {
            return moves;
        }

        // Check for collision
        if (collisionDetected(board, newMove)) {
            // Check if its enemy
            if (attackDetected(board, newMove)) {
                moves.add(newMove);
            }
            return moves;
        }

        // If no collision, add the move and go again
        moves.add(newMove);
        if (recursive) {
            moves.addAll(findAllValidMoves(board, startingPosition, newPosition, direction, true));
        }

        return moves;
    }

}
