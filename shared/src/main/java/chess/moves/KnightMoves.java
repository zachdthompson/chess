package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves extends MoveGenerator {

    private final int[] K_UP_LEFT = {2, -1};
    private final int[] K_DOWN_LEFT = {-2, -1};
    private final int[] K_UP_RIGHT = {2, 1};
    private final int[] K_DOWN_RIGHT = {-2, 1};

    private final int[] LEFT_UP = {1, -2};
    private final int[] RIGHT_UP = {1, 2};
    private final int[] LEFT_DOWN = {-1, -2};
    private final int[] RIGHT_DOWN = {-1, 2};

    private final int[][] validKnightMoves = {K_UP_LEFT, K_DOWN_LEFT, K_UP_RIGHT, K_DOWN_RIGHT,
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
