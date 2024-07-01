package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves extends MoveGenerator {

    private int[] UP_LEFT = {2, -1};
    private int[] DOWN_LEFT = {-2, -1};
    private int[] UP_RIGHT = {2, 1};
    private int[] DOWN_RIGHT = {-2, 1};

    private int[] LEFT_UP = {1, -2};
    private int[] RIGHT_UP = {1, 2};
    private int[] LEFT_DOWN = {-1, -2};
    private int[] RIGHT_DOWN = {-1, 2};

    private int[][] validKnightMoves = {UP_LEFT, DOWN_LEFT, UP_RIGHT, DOWN_RIGHT,
    LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN};

    @Override
    // Default generator, will be overridden
    public Collection<ChessMove> generateMoves(
            ChessBoard board,
            ChessPosition startingPosition
    ) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        for (int[] move : validKnightMoves) {
            moves.addAll(findAllValidMoves(board, startingPosition, null, move, false));
        }

        return moves;
    }


}
