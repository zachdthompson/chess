package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Map;

public class DrawBoard {

    // Board is 10x10 to include the borders
    private String[][] chessBoard = new String[10][10];

    private final String borderSquare = EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK;

    private final ChessGame chessGame;

    public DrawBoard() {
        resetBoard();
        chessGame = new ChessGame();
        placePieces(chessGame);
    }

    /**
     * Will print the board in both team orientations
     */
    public void printBothBoards() {
        String[][] reverseBoard = reverseBoard();
        printBoard(reverseBoard);
        printBoard(chessBoard);
        System.out.println(EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR);
    }


    /**
     * Prints a given 2D array board
     */
    private void printBoard(String[][] boardToPrint) {

        System.out.println();

        for (String[] row : boardToPrint) {
            for (String cell : row) {
                System.out.print(cell + EscapeSequences.RESET_BG_COLOR);
            }
            System.out.println(); // Move to the next line after printing all columns in a row
        }
    }

    /**
     * This will reset to a blank board with no pieces on it
     */
    private void resetBoard() {

        // Initialize labels for columns and rows
        String[] colLabels = {EscapeSequences.EMPTY, " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", EscapeSequences.EMPTY};
        String[] rowLabels = {EscapeSequences.EMPTY, " 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 ", EscapeSequences.EMPTY};

        // Draw empty board
        for (int row = 0; row < chessBoard.length; row++) {
            for (int col = 0; col < chessBoard[row].length; col++) {
                // Top and bottom borders
                if (row == 0 || row == chessBoard.length - 1) {
                    chessBoard[row][col] = borderSquare + colLabels[col];
                }
                // Edge borders
                else if (col == 0 || col == chessBoard[0].length - 1) {
                    chessBoard[row][col] = borderSquare + rowLabels[row];
                }
                // Checkerboard pattern white
                else if ((row + col) % 2 == 0) {
                    chessBoard[row][col] = EscapeSequences.SET_BG_COLOR_WHITE + EscapeSequences.EMPTY;
                }
                // Everything else black
                else {
                    chessBoard[row][col] = EscapeSequences.SET_BG_COLOR_BLACK + EscapeSequences.EMPTY;
                }
            }
        }
    }

    /**
     * Takes a chess game
     * Finds all the pieces
     * Maps their location
     * Puts them in their place on the board
     * @param game The game we want to draw
     */
    private void placePieces(ChessGame game) {

        ChessBoard board = game.getBoard();
        Map<ChessPosition, ChessPiece> pieceMap = new java.util.HashMap<>(Map.of());

        // Get a map of all the pieces and their locations
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition currPosition = new ChessPosition(row + 1, col + 1);
                ChessPiece currPiece = board.getPiece(currPosition);
                if (currPiece != null) {
                    pieceMap.put(currPosition, currPiece);
                }
            }
        }

        // Place them on the grid
        for (Map.Entry<ChessPosition, ChessPiece> entry : pieceMap.entrySet()) {

            // Extracted data
            ChessPiece piece = entry.getValue();
            ChessPosition currPosition = entry.getKey();
            ChessPiece.PieceType pieceType = piece.getPieceType();
            ChessGame.TeamColor teamColor = piece.getTeamColor();
            int row = currPosition.getRow();
            int col = currPosition.getColumn();

            // Get piece string
            String pieceString = getPieceString(teamColor, pieceType);

            //Get square color
            String backgroundColor = "";
            if ((row + col) % 2 == 0) {
                backgroundColor = EscapeSequences.SET_BG_COLOR_WHITE;
            }
            else {
                backgroundColor = EscapeSequences.SET_BG_COLOR_BLACK;
            }

            // set the piece
            if (teamColor == ChessGame.TeamColor.BLACK) {
                chessBoard[row][col] =  backgroundColor + EscapeSequences.SET_TEXT_COLOR_BLUE + pieceString;
            }
            else {
                chessBoard[row][col] = backgroundColor + EscapeSequences.SET_TEXT_COLOR_RED + pieceString;
            }
        }
    }

    /**
     * Helper to prevent deep nesting
     * Returns the escape sequence of the team color and piece type
     * @param teamColor The color of the team
     * @param pieceType The type of piece
     * @return EscapeSequence of the color + piece
     */
    private String getPieceString(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        // this could be replaced with nested maps... But idk
        String pieceString = "";
        switch (pieceType) {
            case KING:
                if (teamColor == ChessGame.TeamColor.BLACK) {
                    pieceString = EscapeSequences.BLACK_KING;
                }
                else {
                    pieceString = EscapeSequences.WHITE_KING;
                }
                break;
            case QUEEN:
                if (teamColor == ChessGame.TeamColor.BLACK) {
                    pieceString = EscapeSequences.BLACK_QUEEN;
                }
                else {
                    pieceString = EscapeSequences.WHITE_QUEEN;
                }
                break;
            case ROOK:
                if (teamColor == ChessGame.TeamColor.BLACK) {
                    pieceString = EscapeSequences.BLACK_ROOK;
                }
                else {
                    pieceString = EscapeSequences.WHITE_ROOK;
                }
                break;
            case BISHOP:
                if (teamColor == ChessGame.TeamColor.BLACK) {
                    pieceString = EscapeSequences.BLACK_BISHOP;
                }
                else {
                    pieceString = EscapeSequences.WHITE_BISHOP;
                }
                break;
            case KNIGHT:
                if (teamColor == ChessGame.TeamColor.BLACK) {
                    pieceString = EscapeSequences.BLACK_KNIGHT;
                }
                else {
                    pieceString = EscapeSequences.WHITE_KNIGHT;
                }
                break;
            case PAWN:
                if (teamColor == ChessGame.TeamColor.BLACK) {
                    pieceString = EscapeSequences.BLACK_PAWN;
                }
                else {
                    pieceString = EscapeSequences.WHITE_PAWN;
                }
                break;
        }
        return pieceString;
    }

    /**
     * Takes the chessBoard variable and rotates it 180 degrees for the other view
     * @return a new String[][] 2D array to print
     */
    private String[][] reverseBoard() {
        int size = chessBoard.length;
        String[][] reversedBoard = new String[size][size];

        // I found this code on stack overflow for rotating a 2d array 180 degrees
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                reversedBoard[row][col] = chessBoard[size - 1 - row][size - 1 - col];
            }
        }
        return reversedBoard;
    }
}
