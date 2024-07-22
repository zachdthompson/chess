package chess;

import chess.moves.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

  private final ChessGame.TeamColor teamColor;
  private final ChessPiece.PieceType pieceType;
  private final Collection<ChessMove> moves = new ArrayList<>();

  private final BishopMoves bishopMoves = new BishopMoves();
  private final RookMoves rookMoves = new RookMoves();
  private final KnightMoves knightMoves = new KnightMoves();
  private final PawnMoves pawnMoves = new PawnMoves();
  private final QueenMoves queenMoves = new QueenMoves();
  private final KingMoves kingMoves = new KingMoves();

  public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
    this.teamColor = pieceColor;
    this.pieceType = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (o == null || getClass() != o.getClass()) {
        return false;
    }
    ChessPiece that = (ChessPiece) o;
    return teamColor == that.teamColor && pieceType == that.pieceType && Objects.equals(moves, that.moves);
  }

  @Override
  public int hashCode() {
    return Objects.hash(teamColor, pieceType, moves);
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
    return teamColor;
  }

  /**
   * @return which type of chess piece this piece is
   */
  public PieceType getPieceType() {
    return pieceType;
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

        switch (pieceType) {

            case KING:
                moves.addAll(kingMoves.generateMoves(board, myPosition));
                break;
            case PAWN:
                moves.addAll(pawnMoves.generateMoves(board, myPosition));
                break;
            case KNIGHT:
                moves.addAll(knightMoves.generateMoves(board, myPosition));
                break;
            case BISHOP:
                moves.addAll(bishopMoves.generateMoves(board, myPosition));
                break;
            case ROOK:
                moves.addAll(rookMoves.generateMoves(board, myPosition));
                break;
            case QUEEN:
                moves.addAll(queenMoves.generateMoves(board, myPosition));
                break;
        }

        return moves;
    }


}
