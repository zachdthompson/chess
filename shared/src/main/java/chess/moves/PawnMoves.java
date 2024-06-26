package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessGame;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves {

    /**
     * Handles all the dumb moves a pawn can do
     * @param board The current game board
     * @param myPosition The starting position of the pawn
     * @return ArrayList of all moves
     */
    public Collection<ChessMove> getValidMoves(ChessBoard board, ChessPosition myPosition) {

        // Needed Variables
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(myPosition);
        int verticalDirection;
        int startRow;

        // Set variables based on which color the piece is
        switch (piece.getTeamColor()) {
            case WHITE:
                verticalDirection = 1;
                startRow = 2;
                break;
            case BLACK:
                verticalDirection = -1;
                startRow = 7;
                break;
            // This should not be hit, but its here to make the compiler happy.
            default:
                verticalDirection = 0;
                startRow = 0;
                break;
        }


        // Check if we can capture
        ChessPosition leftPosition = new ChessPosition(myPosition.getRow() + verticalDirection, myPosition.getColumn() - 1);
        ChessPosition rightPosition = new ChessPosition(myPosition.getRow() + verticalDirection, myPosition.getColumn() + 1);

        // Check left capture
        if (diagonalAttackDetection(board, leftPosition, piece.getTeamColor())) {
            // Check for promotions
            moves.addAll(promotionChecker(myPosition, leftPosition));
        }
        // Check right capture
        if (diagonalAttackDetection(board, rightPosition, piece.getTeamColor())) {
            // Check for promotions
            moves.addAll(promotionChecker(myPosition, rightPosition));
        }



        // Then try moving forward
        ChessPosition newPosition = new ChessPosition(myPosition.getRow() + verticalDirection, myPosition.getColumn());

        // If we can move forward, add the move
        if (!verticalCollisionDetection(board, newPosition)) {

           // Check if we can move twice on the first move
           if (myPosition.getRow() == startRow) {
               ChessPosition secondMove = new ChessPosition(newPosition.getRow() + verticalDirection, newPosition.getColumn());
               // Check if the move is out of bounds
               if (!isOutOfBounds(secondMove)) {
                   if (!verticalCollisionDetection(board, secondMove)) {
                       moves.add(new ChessMove(myPosition, secondMove, null));
                   }
               }
           }
           // Add moves
           moves.addAll(promotionChecker(myPosition, newPosition));
        }
        return moves;
    }

    /**
     * Checks if we need to add all the possible promotion moves or not
     * @param myPosition The starting position of the pawn
     * @param newPosition The pawns next position
     * @return A list of all valid moves
     */
    private ArrayList<ChessMove> promotionChecker(ChessPosition myPosition, ChessPosition newPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        // If the new position is out of bounds, return nothing.
        if (isOutOfBounds(newPosition)) {
            return moves;
        }

        if (newPosition.getRow() == 8 || newPosition.getRow() == 1) {
            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
        } else {
            moves.add(new ChessMove(myPosition, newPosition, null));
        }
        return moves;
    }

    /**
     * Checks if a piece can move forward without colliding.
     * This does not check for team color, as it doesnt matter for vertical moves as we cant capture
     * @param board The current game board
     * @param checkPosition The space to check for collision
     * @return Boolean value if a collision is detected
     */
    private boolean verticalCollisionDetection(ChessBoard board, ChessPosition checkPosition) {
        if(isOutOfBounds(checkPosition)) {
            return false;
        }
        return board.getPiece(checkPosition) != null;
    }

    /**
     * Checks if we can capture a piece diagonally.
     * Unlike vertical detection, this does check if the piece is our own color or not
     * @param board The current game board
     * @param checkPosition The position we want to check
     * @param color The color of the piece that is moving
     * @return Boolean if we can capture or not
     */
    private boolean diagonalAttackDetection(ChessBoard board, ChessPosition checkPosition, ChessGame.TeamColor color) {
        if(isOutOfBounds(checkPosition)) {
            return false;
        }
        // Check if the space is occupied
        if (board.getPiece(checkPosition) != null) {
            // If the colors dont match, return true
            if (board.getPiece(checkPosition).getTeamColor() != color) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the space we are looking at is going to be out of bounds
     * @param checkPosition The chess board position to check
     * @return Boolean if the space is valid or not
     */
    private boolean isOutOfBounds(ChessPosition checkPosition) {
        int row = checkPosition.getRow();
        int column = checkPosition.getColumn();

        return row < 1 || row > 8 || column < 1 || column > 8;
    }
}
