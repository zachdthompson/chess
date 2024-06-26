package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import chess.moves.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;

    // This is only needed for pawns, to allow for extra rules on the first turn
    private boolean hasMoved = false;

    // Move objects
    private static final OneSpaceMoves oneSpaceMoves = new OneSpaceMoves();
    private static final RecursiveMoves recursiveMoves = new RecursiveMoves();
    private static final PawnMoves pawnMoves = new PawnMoves();

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType pieceType) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }


    /**
     * @return Returns boolean status if a pieces has moved yet
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     *
     * @param hasMoved Boolean to update move status
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        ArrayList<ChessMove> moves = new ArrayList<>();

        // Send the different moves where they need to go
        switch (pieceType) {
            case KING:
            case KNIGHT:
                moves.addAll(oneSpaceMoves.getValidMoves(board, myPosition, pieceType));
                break;
            case QUEEN:
            case BISHOP:
            case ROOK:
                moves.addAll(recursiveMoves.getValidMoves(board, myPosition, pieceType));
                break;
            case PAWN:
                //moves.addAll(pawnMoves.getValidMoves(board, myPosition, pieceType));
                break;
        }

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return hasMoved == that.hasMoved && pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType, hasMoved);
    }
}
