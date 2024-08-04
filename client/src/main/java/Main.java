import chess.*;

import ui.DrawBoard;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        DrawBoard board = new DrawBoard();

        board.printBothBoards();
    }
}