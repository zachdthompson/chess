package chess;

import chess.moves.KingMoves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard chessBoard;

    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;

        this.chessBoard = new ChessBoard();
        chessBoard.resetBoard();

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece checkPiece = chessBoard.getPiece(startPosition);
        return new ArrayList<>(checkPiece.pieceMoves(chessBoard, startPosition));
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        // Check if starting piece exists
        if (chessBoard.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException("Invalid move, no valid piece found");
        }

        // Check if it's the current teams turn
        if (chessBoard.getPiece(move.getStartPosition()).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Invalid move, wait your turn!");
        }

        ArrayList<ChessMove> possibleMoves = new ArrayList<>(validMoves(move.getStartPosition()));

        // Check if the desired move is valid
        if (!possibleMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move: " + move);
        }

        //If it' s the king, make sure the move won't put them in check
        if (chessBoard.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.KING) {
            if (moveWillEndangerKing(move)) {
                throw new InvalidMoveException("That move puts your king in check!");
            }
        }

        // Do move here
        chessBoard.movePiece(move);

        // Change turn
        if (teamTurn == TeamColor.BLACK) {
            teamTurn = TeamColor.WHITE;
        }
        else {
            teamTurn = TeamColor.BLACK;
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        // Set opposing team color
        TeamColor opposingTeam = TeamColor.WHITE;

        if (teamColor == TeamColor.WHITE) {
            opposingTeam = TeamColor.BLACK;
        }

        // Get all their remaining pieces
        Collection<ChessPiece> opposingPieces = getTeamPieces(opposingTeam);

        // TODO: Get a list of all the opposite team valid moves
        ArrayList<ChessMove> validOpponentMoves = new ArrayList<>();

        for (ChessPiece opposingPiece : opposingPieces) {
            ChessPosition currentPiecePosition =
            validOpponentMoves.addAll(opposingPiece.pieceMoves(chessBoard, ))
        }
        // TODO: Check if those moves correspond to our king

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        // TODO: Check if king is currently in check
        // TODO: Get list of all king moves
        // TODO: See if any valid moves are left that wouldn't put them in check

        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        // TODO: Get list of all valid moves for that team


        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, chessBoard);
    }

    private boolean moveWillEndangerKing(ChessMove move) {

        // Make a local copy of the board
        ChessBoard copyBoard = chessBoard.clone();

        // Make the move on the copy and check if it puts the king in check
        copyBoard.movePiece(move);

        return isInCheckmate(teamTurn);
    }

    /**
     * Gets all the pieces for the given team
     * @param teamColor The team color you want all the pieces for
     * @return ArrayList of pieces
     */
    private Collection<ChessPiece> getTeamPieces(TeamColor teamColor) {
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = chessBoard.getPiece(new ChessPosition(row, col));
                if (piece.getTeamColor() == teamColor) {
                    pieces.add(piece);
                }
            }
        }

        return pieces;
    }
}
