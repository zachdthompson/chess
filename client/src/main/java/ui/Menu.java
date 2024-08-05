package ui;

import chess.ChessGame;
import helper.ServerFacade;
import model.AuthData;
import model.GameData;

import static java.lang.System.exit;
import static ui.EscapeSequences.*;

public class Menu {

    private enum UserState {
        LOGGED_OUT,
        LOGGED_IN,
        WHITE,
        BLACK,
        OBSERVER
    }

    // Needed Backend
    private final ServerFacade server;
    private GameData gameData;
    private GameData[] gameList;
    private final DrawBoard drawBoard = new DrawBoard();

    // User Data
    private String username;
    private String authToken;
    private UserState currentState = UserState.LOGGED_OUT;


    private String failure = "Couldn't process your command: ";

    public Menu(String hostname, int portNumber) {
        server = new ServerFacade(hostname, portNumber);
    }

    public void printInputPrompt() {
        if (currentState.equals(UserState.LOGGED_OUT)) {
            System.out.print("[LOGGED_OUT] >> ");
        }
        else {
            System.out.print("[LOGGED_IN] >> ");
        }
    }

    public void handleInput(String input) throws Exception {
        String[] params = input.split(" ");
        String command = params[0].toLowerCase();

        switch (currentState) {
            case LOGGED_OUT:
            switch (command) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "help" -> printHelp();
                case "quit" -> quit();
                default -> System.out.println("Invalid command: " + command);
            }
            break;
            case LOGGED_IN, WHITE, BLACK, OBSERVER:
                switch (command) {
                    case "logout" -> logout();
                    case "list" -> list();
                    case "create" -> createGame(params);
                    case "join" -> join(params);
                    case "observe" -> observe(params);
                    case "help" -> printHelp();
                    case "quit" -> quit();
                    default -> System.out.println("Invalid command: " + command);
                }
                break;
        }


    }

    private void printHelp() {
        StringBuilder returnString = new StringBuilder();
        // Check for login status

        switch (currentState) {
            case LOGGED_OUT:
                returnString.append(SET_TEXT_COLOR_MAGENTA + "login <USERNAME> <PASSWORD>" + RESET_TEXT_COLOR)
                        .append( " - Login to an existing account." + "\n");
                returnString.append(SET_TEXT_COLOR_MAGENTA + "register <USERNAME> <PASSWORD> <EMAIL>" + RESET_TEXT_COLOR)
                        .append( " - Register a new account." + "\n");
                break;
            case LOGGED_IN, WHITE, BLACK, OBSERVER:
                returnString.append(SET_TEXT_COLOR_MAGENTA).append("logout").append(RESET_TEXT_COLOR)
                        .append(" - Log out of ").append(username).append(".").append("\n");
                returnString.append(SET_TEXT_COLOR_MAGENTA).append("create <Name>").append(RESET_TEXT_COLOR).append(" - Creates a new game.\n");
                returnString.append(SET_TEXT_COLOR_MAGENTA).append("list").append(RESET_TEXT_COLOR).append(" - Lists all active games.\n");
                returnString.append(SET_TEXT_COLOR_MAGENTA).append("join <ID> [WHITE|BLACK]").append(RESET_TEXT_COLOR)
                        .append(" - Joins a game with the given ID.\n");

        }

        returnString.append(SET_TEXT_COLOR_MAGENTA + "help" + RESET_TEXT_COLOR + " - List possible operations." + "\n");
        returnString.append(SET_TEXT_COLOR_MAGENTA + "quit" + RESET_TEXT_COLOR + " - Quit the program." + "\n");
        System.out.print(returnString);
    }


    private void login(String[] params) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RESET_TEXT_COLOR + RESET_BG_COLOR);

        // Early return if args aren't long enough
        if (params.length < 3) {
            System.out.println(SET_TEXT_COLOR_RED + failure + "You dont have the right number of arguments. See Help.");
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
            return;
        }

        try{
            username = params[1];
            String password = params[2];

            AuthData loginData = server.login(username, password);

            authToken = loginData.authToken();
            currentState = UserState.LOGGED_IN;

            stringBuilder.append(SET_TEXT_COLOR_GREEN + "Logged in as ").append(username);
            stringBuilder.append(RESET_TEXT_COLOR).append(".").append("\n");
        }
        catch (Exception e) {
            System.out.println(failure + e.getMessage());
        }

        System.out.print(stringBuilder);
    }

    private void register(String[] params) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RESET_TEXT_COLOR + RESET_BG_COLOR);

        // Early return if args aren't long enough
        if (params.length < 4) {
            System.out.println(SET_TEXT_COLOR_RED +failure + "You dont have the right number of arguments. See Help.");
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
            return;
        }

        try{
            username = params[1];
            String password = params[2];
            String email = params[3];

            server.register(username, password, email);

            stringBuilder.append(SET_TEXT_COLOR_GREEN + "Registered user ").append(username);
            stringBuilder.append(RESET_TEXT_COLOR).append(". Please login!").append("\n");
        }
        catch (Exception e) {
            System.out.println(failure + e.getMessage());
        }

        System.out.print(stringBuilder);
    }

    private void logout() throws Exception {
        server.logout(authToken);
        currentState = UserState.LOGGED_OUT;
        username = null;
        authToken = null;

        System.out.println("Logged out successfully.");
    }

    private void quit() throws Exception {
        if (currentState.equals(UserState.LOGGED_IN)) {
            logout();
        }
        System.out.println(SET_TEXT_COLOR_BLUE + "Goodbye! :)");
        exit(0);
    }

    private void list() throws Exception {
        gameList = server.listGames(authToken);

        StringBuilder stringBuilder = new StringBuilder();

        for (GameData gameData : gameList) {
            stringBuilder.append(SET_TEXT_UNDERLINE + "Game Name:" + RESET_TEXT_UNDERLINE + " ").append(gameData.gameName())
                    .append(" " + SET_TEXT_UNDERLINE + "Game ID:" + RESET_TEXT_UNDERLINE + " ").append(gameData.gameID())
                    .append(" " + SET_TEXT_UNDERLINE + "White Player:"+ RESET_TEXT_UNDERLINE + " ")
                        .append(gameData.whiteUsername() != null ? gameData.whiteUsername() : "Empty")
                    .append(" " + SET_TEXT_UNDERLINE + "Black Player:" + RESET_TEXT_UNDERLINE + " ")
                        .append(gameData.blackUsername() != null ? gameData.blackUsername() : "Empty")
                    .append("\n");
        }

        System.out.print(stringBuilder);
    }

    private void createGame(String[] params) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RESET_TEXT_COLOR + RESET_BG_COLOR);

        // Early return if args aren't long enough
        if (params.length < 2) {
            System.out.println(SET_TEXT_COLOR_RED +failure + "You dont have the right number of arguments. See Help.");
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
            return;
        }

        try{
            StringBuilder nameBuilder = new StringBuilder();

            // Concat all params
            for (int i = 1; i < params.length; i++) {
                nameBuilder.append(params[i]).append(" ");
            }

            String gameName = nameBuilder.toString();
            gameData = server.createGame(gameName, authToken);

            stringBuilder.append(SET_TEXT_COLOR_GREEN + "Created game ").append(gameName);
            stringBuilder.append(RESET_TEXT_COLOR).append(" with ID ").append(gameData.gameID()).append("\n");
        }
        catch (Exception e) {
            System.out.println(failure + e.getMessage());
        }

        System.out.print(stringBuilder);
    }

    private void join(String[] params) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RESET_TEXT_COLOR + RESET_BG_COLOR);

        if (params.length < 3) {
            System.out.println(SET_TEXT_COLOR_RED +failure + "You dont have the right number of arguments. See Help.");
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
            return;
        }

        String teamColor = params[2];

        if (!teamColor.equals("WHITE") && !teamColor.equals("BLACK")) {
            System.out.println(SET_TEXT_COLOR_RED +failure + "Please either select WHITE or BLACK!");
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
            return;
        }

        try{
            int gameID = Integer.parseInt(params[1]);
            ChessGame.TeamColor team = ChessGame.TeamColor.valueOf(teamColor);

            // Save team color
            if (team == ChessGame.TeamColor.BLACK) {
                currentState = UserState.BLACK;
            }
            else {
                currentState = UserState.WHITE;
            }

            // Save the current game in memory
            gameData = server.joinGame(gameID, team, authToken);

            // Draw the board
            drawBoard.printBothBoards();

            stringBuilder.append(SET_TEXT_COLOR_GREEN + "Joined game ").append(gameID)
                    .append(RESET_TEXT_COLOR + " as ").append(team).append("\n");
            System.out.print(stringBuilder);
        }
        catch (Exception e) {
            System.out.println(failure + e.getMessage());
        }

    }

    private void observe(String[] params) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RESET_TEXT_COLOR + RESET_BG_COLOR);

        if (params.length < 2) {
            System.out.println(SET_TEXT_COLOR_RED +failure + "You dont have the right number of arguments. See Help.");
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
            return;
        }
        int gameID = Integer.parseInt(params[1]);

        // list all current games
        gameList = server.listGames(authToken);

        // Find the game in the list
        for (GameData game : gameList) {
            if (game.gameID() == gameID) {
                gameData = game;
                drawBoard.printBothBoards();
                currentState = UserState.OBSERVER;
            }
        }
    }
}
