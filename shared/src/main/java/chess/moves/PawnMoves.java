package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves extends MoveGenerator {

    private int LEFT = -1;
    private int RIGHT = 1;

    @Override
    // Default generator, will be overridden
    public Collection<ChessMove> generateMoves(
            ChessBoard board,
            ChessPosition startingPosition
    ) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        ChessPiece piece = board.getPiece(startingPosition);

        ChessGame.TeamColor teamColor = piece.getTeamColor();

        // If the team is white, "forward" is up, the starting row is 2, and the promotion row is 8
        int forward = 1;
        int startingRow = 2;
        int promotionRow = 8;

        // If its black, "forward" is down, the starting row is 7, and the promotion row is 1
        if (teamColor == ChessGame.TeamColor.BLACK) {
            forward = -1;
            startingRow = 7;
            promotionRow = 1;
        }

        int currentRow = startingPosition.getRow();
        int currentColumn = startingPosition.getColumn();

        int nextRow = startingPosition.getRow() + forward;

        // Check if we can move forward
        ChessPosition oneSpaceForward = new ChessPosition(nextRow, currentColumn);
        ChessMove forwardOne = new ChessMove(startingPosition, oneSpaceForward, null);

        // If we cant move forward one, move on to diagonal detection
        if (!outOfBounds(forwardOne) && !collisionDetected(board, forwardOne)) {

            // Check for promotions
            if (nextRow == promotionRow) {
                moves.addAll(promotionMoves(startingPosition, oneSpaceForward));
            }
            else {

                if (currentRow == startingRow) {
                    ChessPosition twoSpacesForward = new ChessPosition(nextRow + forward, currentColumn);
                    ChessMove forwardTwo = new ChessMove(startingPosition, twoSpacesForward, null);

                    if (!outOfBounds(forwardTwo) && !collisionDetected(board, forwardTwo)) {
                        moves.add(forwardTwo);
                    }
                }
                moves.add(forwardOne);
            }
        }

        // Check Diagonal Captures
        ArrayList<ChessMove> attackMoves = new ArrayList<>();

        ChessPosition attackLeftPos = new ChessPosition(nextRow, currentColumn + LEFT);
        ChessPosition attackRightPos = new ChessPosition(nextRow, currentColumn + RIGHT);

        ChessMove attackLeft = new ChessMove(startingPosition, attackLeftPos, null);
        ChessMove attackRight = new ChessMove(startingPosition, attackRightPos, null);

        attackMoves.add(attackRight);
        attackMoves.add(attackLeft);

        for (ChessMove move : attackMoves) {
            ChessPosition endPosition = move.getEndPosition();
            // If we can capture left
            if (!outOfBounds(move) && attackDetected(board, move)) {
                // Check for promotion
                if (endPosition.getRow() == promotionRow) {
                    moves.addAll(promotionMoves(startingPosition, endPosition));
                } else {
                    moves.add(move);
                }
            }
        }

        return moves;
    }


    private Collection<ChessMove> promotionMoves(ChessPosition startingPosition, ChessPosition endPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        moves.add(new ChessMove(startingPosition, endPosition, ChessPiece.PieceType.ROOK));
        moves.add(new ChessMove(startingPosition, endPosition, ChessPiece.PieceType.QUEEN));
        moves.add(new ChessMove(startingPosition, endPosition, ChessPiece.PieceType.BISHOP));
        moves.add(new ChessMove(startingPosition, endPosition, ChessPiece.PieceType.KNIGHT));

        return moves;
    }

}
