package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves extends MoveGenerator {

    @Override
    public Collection<ChessMove> generateMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        ChessGame.TeamColor teamColor = board.getPiece(position).getTeamColor();

        // Setup for white
        int forward = 1;
        int startingRow = 2;
        int promotionRow = 8;

        // Setup for black
        if (teamColor == ChessGame.TeamColor.BLACK) {
            forward = -1;
            startingRow = 7;
            promotionRow = 1;
        }

        // Generate the forward one position
        int forwardOneRow = position.getRow() + forward;
        ChessPosition forwardOnePosition = new ChessPosition(forwardOneRow, position.getColumn());
        ChessMove forwardOne = new ChessMove(position, forwardOnePosition, null);

        // Cant move at all if we are on the ending rows
        if (outOfBounds(forwardOne)) {
            return moves;
        }

        // Forward Moves
        if (!collisionDetected(board, forwardOne)) {

            // Check for promotions
            if (forwardOneRow == promotionRow) {
                moves.addAll(getPromotions(position, forwardOnePosition));
            }
            else {
                moves.add(forwardOne);
            }

            // Check if we can move twice (first turn)
            if (position.getRow() == startingRow) {
                int forwardTwoRows = forwardOneRow + forward;
                ChessPosition forwardTwoPosition = new ChessPosition(forwardTwoRows, position.getColumn());
                ChessMove forwardTwo = new ChessMove(position, forwardTwoPosition, null);

                if (!collisionDetected(board, forwardTwo)) {
                    moves.add(forwardTwo);
                }
            }

        }



        // Diagonal attacks
        ChessPosition attackLeftPosition = new ChessPosition(forwardOneRow, position.getColumn() - 1);
        ChessPosition attackRightPosition = new ChessPosition(forwardOneRow, position.getColumn() + 1);

        ChessMove attackLeft = new ChessMove(position, attackLeftPosition, null);
        ChessMove attackRight = new ChessMove(position, attackRightPosition, null);

        ArrayList<ChessMove> attacks = new ArrayList<>();
        attacks.add(attackRight);
        attacks.add(attackLeft);

        // Check left and right attack
        for (ChessMove attack : attacks) {

            if (outOfBounds(attack)) {
                continue;
            }

            if (attackDetected(board, attack)) {

                // Check for promotions
                if (forwardOneRow == promotionRow) {
                    moves.addAll(getPromotions(attack.getStartPosition(), attack.getEndPosition()));
                }
                else {
                    moves.add(attack);
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> getPromotions(ChessPosition startingPosition, ChessPosition endingPosition) {
        ArrayList<ChessMove> promotions = new ArrayList<>();


        promotions.add(new ChessMove(startingPosition, endingPosition, ChessPiece.PieceType.QUEEN));
        promotions.add(new ChessMove(startingPosition, endingPosition, ChessPiece.PieceType.ROOK));
        promotions.add(new ChessMove(startingPosition, endingPosition, ChessPiece.PieceType.BISHOP));
        promotions.add(new ChessMove(startingPosition, endingPosition, ChessPiece.PieceType.KNIGHT));

        return promotions;
    }
}
