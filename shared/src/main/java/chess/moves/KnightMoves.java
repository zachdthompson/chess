package chess.moves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class KnightMoves {

    private final int[][] possibleMoves = {{2,-1}, {2,1}, {-2, -1}, {-2, 1}};

    public Collection<ChessMove> getValidMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();

        for (int[] move : possibleMoves) {
            int row = move[0];
            int col = move[1];

            int newRow = myPosition.getRow() + row;
            int newCol = myPosition.getColumn() + col;

            // If the move is out of bounds of the board, dont add it
            if (newRow > 8 || newRow < 0 || newCol > 8 || newCol < 0) {
                continue;
            }

            // If its valid, make a new position and add it.
            ChessPosition newPosition = new ChessPosition(row, col);
            ChessMove newMove = new ChessMove(myPosition, newPosition, null);
            validMoves.add(newMove);
        }

        return validMoves;
    }

}
