package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves extends MoveGenerator {

    private final int[] kUpLeft = {2, -1};
    private final int[] kDownLeft = {-2, -1};
    private final int[] kUpRight = {2, 1};
    private final int[] kDownRight = {-2, 1};

    private final int[] leftUp = {1, -2};
    private final int[] rightUp = {1, 2};
    private final int[] leftDown = {-1, -2};
    private final int[] rightDown = {-1, 2};

    private final int[][] validKnightMoves = {kUpLeft, kDownLeft, kUpRight, kDownRight,
            leftUp, rightUp, leftDown, rightDown};

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
