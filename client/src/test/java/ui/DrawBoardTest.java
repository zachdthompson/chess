package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawBoardTest {


    @Test
    void drawBoard() {
        DrawBoard drawBoard = new DrawBoard();
        assertNotNull(drawBoard);
        drawBoard.printBothBoards();
    }
}