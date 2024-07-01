package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves extends MoveGenerator {

    @Override
    // Default generator, will be overridden
    public Collection<ChessMove> generateMoves(
            ChessBoard board,
            ChessPosition startingPosition
    ) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        for (int[] move : verticalMoves) {
            moves.addAll(findAllValidMoves(board, startingPosition, null, move, true));
        }

        return moves;
    }


}
