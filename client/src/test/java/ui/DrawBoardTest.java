package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawBoardTest {

    private static DrawBoard drawBoard;

    @BeforeAll
    static void setUp() {
        drawBoard = new DrawBoard();
    }

    @Test
    void printBothBoards() {
        System.out.println("Printing Both Boards:");
        drawBoard.printBothBoards();
    }

    @Test
    void printWhite() {
        System.out.println("Printing White:");
        drawBoard.printTeamBoard(ChessGame.TeamColor.WHITE);
    }

    @Test
    void printBlack() {
        System.out.println("Printing Black:");
        drawBoard.printTeamBoard(ChessGame.TeamColor.BLACK);
    }

    @Test
    void printCustom() throws InvalidMoveException {
        ChessGame customBoard = new ChessGame();

        ChessPosition startingPawn = new ChessPosition(2, 1);
        ChessPosition endingPawn = new ChessPosition(4, 1);

        ChessMove makePawnMove = new ChessMove(startingPawn, endingPawn, null);

        customBoard.makeMove(makePawnMove);

        drawBoard = new DrawBoard(customBoard);

        drawBoard.printTeamBoard(ChessGame.TeamColor.WHITE);
    }
}