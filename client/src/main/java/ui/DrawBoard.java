package ui;

import chess.ChessGame;

import java.util.Arrays;

public class DrawBoard {

    // Board is 10x10 to include the borders
    private String[][] chessBoard = new String[10][10];

    private String borderSquare = EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK;


    public DrawBoard() {
        resetBoard();
    }


    public void printBothBoards() {
        String[][] reverseBoard = reverseBoard();
        printBoard(reverseBoard);
        printBoard(chessBoard);
    }


    private void printBoard(String[][] boardToPrint) {

        for (String[] row : boardToPrint) {
            for (String cell : row) {
                System.out.print(cell + EscapeSequences.RESET_BG_COLOR);
            }
            System.out.println(); // Move to the next line after printing all columns in a row
        }
        System.out.println();

    }


    private void resetBoard() {

        // Draw empty board
        for (int row = 0; row < chessBoard.length; row++) {
            for (int col = 0; col < chessBoard[row].length; col++) {
                if (row == 0 || row == chessBoard.length - 1) {
                    chessBoard[row][col] = borderSquare + "   ";
                }
                else if (col == 0 || col == chessBoard[0].length - 1) {
                    chessBoard[row][col] = borderSquare + "   ";
                }
                else if ((row + col) % 2 == 0) {
                    chessBoard[row][col] = EscapeSequences.SET_BG_COLOR_WHITE + "   ";
                }
                else {
                    chessBoard[row][col] = EscapeSequences.SET_BG_COLOR_BLACK + "   ";
                }
            }
        }
        placePieces();
    }

    private void placePieces() {


        String whiteSquareWhitePiece = EscapeSequences.SET_BG_COLOR_WHITE + EscapeSequences.SET_TEXT_COLOR_RED;
        String blackSquareWhitePiece = EscapeSequences.SET_BG_COLOR_BLACK + EscapeSequences.SET_TEXT_COLOR_RED;
        String whiteSquareBlackPiece = EscapeSequences.SET_BG_COLOR_WHITE + EscapeSequences.SET_TEXT_COLOR_BLUE;
        String blackSquareBlackPiece = EscapeSequences.SET_BG_COLOR_BLACK + EscapeSequences.SET_TEXT_COLOR_BLUE;

        int whitePawnRow = chessBoard.length - 3;
        int whiteBackRow = chessBoard.length - 2;
        for (int col = 1 ; col < chessBoard[whitePawnRow].length - 1; col++) {
            if (col % 2 == 0) {
                chessBoard[whitePawnRow][col] = blackSquareWhitePiece + EscapeSequences.WHITE_PAWN;
            }
            else {
                chessBoard[whitePawnRow][col] = whiteSquareWhitePiece + EscapeSequences.WHITE_PAWN;
            }
        }

        // White Back Pieces
        chessBoard[whiteBackRow][1] = blackSquareWhitePiece + EscapeSequences.WHITE_ROOK;
        chessBoard[whiteBackRow][2] = whiteSquareWhitePiece + EscapeSequences.WHITE_KNIGHT;
        chessBoard[whiteBackRow][3] = blackSquareWhitePiece + EscapeSequences.WHITE_BISHOP;
        chessBoard[whiteBackRow][4] = whiteSquareWhitePiece + EscapeSequences.WHITE_QUEEN;
        chessBoard[whiteBackRow][5] = blackSquareWhitePiece + EscapeSequences.WHITE_KING;
        chessBoard[whiteBackRow][6] = whiteSquareWhitePiece + EscapeSequences.WHITE_BISHOP;
        chessBoard[whiteBackRow][7] = blackSquareWhitePiece + EscapeSequences.WHITE_KNIGHT;
        chessBoard[whiteBackRow][8] = whiteSquareWhitePiece + EscapeSequences.WHITE_ROOK;



        //Black Pieces

        int blackPawnRow = chessBoard.length - 8;
        int blackBackRow = chessBoard.length - 9;
        for (int col = 1 ; col < chessBoard[blackPawnRow].length - 1; col++) {
            if (col % 2 == 0) {
                chessBoard[blackPawnRow][col] = whiteSquareBlackPiece + EscapeSequences.BLACK_PAWN;
            }
            else {
                chessBoard[blackPawnRow][col] = blackSquareBlackPiece + EscapeSequences.BLACK_PAWN;
            }
        }

        // White Back Pieces
        chessBoard[blackBackRow][1] = whiteSquareBlackPiece + EscapeSequences.BLACK_ROOK;
        chessBoard[blackBackRow][2] = blackSquareBlackPiece + EscapeSequences.BLACK_KNIGHT;
        chessBoard[blackBackRow][3] = whiteSquareBlackPiece + EscapeSequences.BLACK_BISHOP;
        chessBoard[blackBackRow][4] = blackSquareBlackPiece + EscapeSequences.BLACK_KING;
        chessBoard[blackBackRow][5] = whiteSquareBlackPiece + EscapeSequences.BLACK_QUEEN;
        chessBoard[blackBackRow][6] = blackSquareBlackPiece + EscapeSequences.BLACK_BISHOP;
        chessBoard[blackBackRow][7] = whiteSquareBlackPiece + EscapeSequences.BLACK_KNIGHT;
        chessBoard[blackBackRow][8] = blackSquareBlackPiece + EscapeSequences.BLACK_ROOK;

    }

    private String[][] reverseBoard() {
        String[][] reversedBoard = new String[chessBoard.length][];
        for (int row = 0; row < chessBoard.length; row++) {
            reversedBoard[row] = chessBoard[chessBoard.length - 1 - row];
        }
        return reversedBoard;
    }
}
