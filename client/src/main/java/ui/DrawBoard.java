package ui;

public class DrawBoard {

    /**
     * These variables take strings of nonsense and make them more human-readable.
     */
    // Define squares
    private final String ansiEscape = "\\u001b[";
    private final String whiteTile = "107";
    private final String blackTile = "47";

    private final String blackOnWhite = "30;107";
    private final String whiteOnBlack = blackOnWhite + ";7";

    // Define pieces
    //White
    private final String whiteKing = "♔";
    private final String whiteQueen = "♕";
    private final String whiteRook = "♖";
    private final String whiteBishop = "♗";
    private final String whiteKnight = "♘";
    private final String whitePawn = "♙";

    //Black
    private final String blackKing = "♚";
    private final String blackQueen = "♛";
    private final String blackRook = "♜";
    private final String blackBishop = "♝";
    private final String blackKnight = "♞";
    private final String blackPawn = "♟";


    DrawBoard() {

    }


    private void resetBoard() {

    }

}
