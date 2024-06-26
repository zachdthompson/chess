package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessGame;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves {

    /*
    Pawns normally may move forward one square if that square is unoccupied,
    though if it is the first time that pawn is being moved, it may be moved forward 2 squares
    (provided both squares are unoccupied). Pawns cannot capture forward, but instead capture
    forward diagonally (1 square forward and 1 square sideways). They may only move diagonally like
    this if capturing an enemy piece. When a pawn moves to the end of the board (row 8 for white and row 1 for black),
    they get promoted and are replaced with the player's choice of Rook, Knight, Bishop, or Queen (they cannot stay a Pawn or become King).
     */



    public Collection<ChessMove> getValidMoves(
            ChessBoard board,
            ChessPosition myPosition,
            ChessPiece.PieceType type
    ) {
        Collection<ChessMove> moves = new ArrayList<>();

        ChessPiece piece = board.getPiece(myPosition);

        int verticalDirection;
        int startRow;

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

        if (diagonalAttackDetection(board, leftPosition, piece.getTeamColor())) {
            // Check for promotions
            if (leftPosition.getRow() == 8 || leftPosition.getRow() == 1 ) {
                moves.add(new ChessMove(myPosition, leftPosition, ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, leftPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, leftPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, leftPosition, ChessPiece.PieceType.ROOK));
            }
            else {
                moves.add(new ChessMove(myPosition, leftPosition, null));
            }
        }
        if (diagonalAttackDetection(board, rightPosition, piece.getTeamColor())) {
            // Check for promotions
            if (rightPosition.getRow() == 8 || rightPosition.getRow() == 1 ) {
                moves.add(new ChessMove(myPosition, rightPosition, ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, rightPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, rightPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, rightPosition, ChessPiece.PieceType.ROOK));
            }
            else {
                moves.add(new ChessMove(myPosition, rightPosition, null));
            }
        }


        // Then try moving forward
        ChessPosition newPosition = new ChessPosition(myPosition.getRow() + verticalDirection, myPosition.getColumn());

        // If we can move forward, add the move
        if (!verticalCollisionDetection(board, newPosition)) {

            // Check if we can move twice on the first move
            if (myPosition.getRow() == startRow) {
                ChessPosition secondMove = new ChessPosition(newPosition.getRow() + verticalDirection, newPosition.getColumn());

                if (!verticalCollisionDetection(board, secondMove)) {
                    moves.add(new ChessMove(myPosition, secondMove, null));
                }
            }

            // Check for promotions
            if (newPosition.getRow() == 8 || newPosition.getRow() == 1 ) {
                moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
            }
            else {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }

        }

        return moves;
    }

    private boolean verticalCollisionDetection(ChessBoard board, ChessPosition checkPosition) {
        return board.getPiece(checkPosition) != null;
    }

    private boolean diagonalAttackDetection(ChessBoard board, ChessPosition checkPosition, ChessGame.TeamColor color) {
        // Check if the space is occupied
        if (board.getPiece(checkPosition) != null) {
            // If the colors dont match, return true
            if (board.getPiece(checkPosition).getTeamColor() != color) {
                return true;
            }
        }
        return false;
    }


}
