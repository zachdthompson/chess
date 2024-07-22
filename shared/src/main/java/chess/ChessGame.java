package chess;

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

    public ChessGame getCurrentGame() {
        return this;
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
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // Skip empty pieces
        if (checkPiece != null) {
            Collection<ChessMove> movesToCheck = checkPiece.pieceMoves(chessBoard, startPosition);

            // Remove any that endanger the king
            for (ChessMove move : movesToCheck) {
                if (!moveWillEndangerKing(move)){
                    validMoves.add(move);
                }
            }
        }

        return validMoves;
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

        // Before any move can be made, we have to make sure the king is not in check
        if (isInCheck(chessBoard.getPiece(move.getStartPosition()).getTeamColor())) {
            // Check if the new move is going to remove check
            if (!moveWillEndangerKing(move)) {
                throw new InvalidMoveException("Your king is in check, you must resolve that!");
            }
        }

        ArrayList<ChessMove> possibleMoves = new ArrayList<>(validMoves(move.getStartPosition()));

        // Check if the desired move is valid
        if (!possibleMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move: " + move);
        }

        // Check if moving will endanger the king
        if (moveWillEndangerKing(move)) {
            throw new InvalidMoveException("That move puts your king in check!");
        }

        // Do move here
        chessBoard.movePiece(move);

        // Change turn
        toggleTeamTurn();
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

        // Get all our remaining pieces
        Collection<ChessPiece> ourPieces = getTeamPieces(teamColor);

        // Find our king
        ChessPiece ourKing = null;
        for (ChessPiece piece : ourPieces) {
            if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                ourKing = piece;
                break;
            }
        }

        // If no king is found, it cant be in danger
        if (ourKing == null) {
            return false;
        }

        ChessPosition ourKingPosition = chessBoard.getPiecePosition(ourKing);


        // check if the king is under attack

        return !attackersAtSpace(ourKingPosition).isEmpty();
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        // If king isn't in check, no checkmate
        if (!isInCheck(teamColor)) {
            return false;
        }

        // Get a list of all our teams moves
        Collection<ChessMove> validTeamMoves = getValidTeamMoves(teamColor);

        // Check if we have any moves that can save us
        for (ChessMove move : validTeamMoves) {
            if (!moveWillEndangerKing(move)){
                return false;
            }
        }

        // If no moves are found, checkmate
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        // Check if king is in danger, cant be stalemate
        if (isInCheck(teamColor)) {
            return false;
        }

        // Get list of our moves
        Collection<ChessMove> validTeamMoves = getValidTeamMoves(teamColor);

        return validTeamMoves.isEmpty();
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

    public boolean moveWillEndangerKing(ChessMove move) {

        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece startPiece = chessBoard.getPiece(start);
        ChessPiece endPiece = chessBoard.getPiece(end);

        // Make a board copy
        ChessBoard originalBoard = new ChessBoard(chessBoard);

        // Make the move temporarily
        chessBoard.movePiece(move);

        // Check if this puts the king in check
        boolean endangersKing = isInCheck(startPiece.getTeamColor());

        // Revert the move
        chessBoard = originalBoard;

        return endangersKing;
    }

    private Collection<ChessPiece> attackersAtSpace(ChessPosition target) {
        TeamColor targetTeam = chessBoard.getPiece(target).getTeamColor();
        Collection<ChessPiece> attackingPieces = new ArrayList<>();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = chessBoard.getPiece(currentPosition);
                if (piece != null && !piece.getTeamColor().equals(targetTeam)) {
                    if (canAttackPosition(piece, currentPosition, target)) {
                        attackingPieces.add(piece);
                    }
                }
            }
        }

        return attackingPieces;
    }

    private boolean canAttackPosition(ChessPiece piece, ChessPosition currentPosition, ChessPosition targetPosition) {
        Collection<ChessMove> validMoves = piece.pieceMoves(chessBoard, currentPosition);
        for (ChessMove move : validMoves) {
            if (move.getEndPosition().equals(targetPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all the pieces for the given team
     * @param teamColor The team color you want all the pieces for
     * @return ArrayList of pieces
     */
    private Collection<ChessPiece> getTeamPieces(TeamColor teamColor) {
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece piece = chessBoard.getPiece(new ChessPosition(row, col));
                if (piece != null && piece.getTeamColor() == teamColor) {
                    pieces.add(piece);
                }
            }
        }

        return pieces;
    }

    private Collection<ChessMove> getValidTeamMoves(TeamColor teamColor) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        Collection<ChessPiece> pieces = getTeamPieces(teamColor);

        for (ChessPiece piece : pieces) {
            validMoves.addAll(validMoves(chessBoard.getPiecePosition(piece)));
        }

        return validMoves;
    }

    private void toggleTeamTurn() {
        if (teamTurn == TeamColor.BLACK) {
            teamTurn = TeamColor.WHITE;
        }
        else {
            teamTurn = TeamColor.BLACK;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, chessBoard);
    }
}
