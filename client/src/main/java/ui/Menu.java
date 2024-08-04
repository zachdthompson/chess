package ui;

import helper.ServerFacade;
import model.AuthData;
import model.GameData;
import model.UserData;

import static java.lang.System.exit;
import static ui.EscapeSequences.*;

public class Menu {

    private enum userState {
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
    private Menu.userState currentState = Menu.userState.LOGGED_OUT;


    private String failure = "Couldn't process your command: ";

    public Menu(String hostname, int portNumber) {
        server = new ServerFacade(hostname, portNumber);
    }

    public void printInputPrompt() {
        if (currentState.equals(userState.LOGGED_OUT)) {
            System.out.print("[LOGGED_OUT] >> ");
        }
        else {
            System.out.print("[LOGGED_IN] >> ");
        }
    }

    public void handleInput(String input) throws Exception {
        input = input.toLowerCase();
        String[] params = input.split(" ");
        String command = params[0];


        switch (command) {
            case "login":
                login(params);
                break;
            case "register":
                register(params);
                break;
            case "logout":
                logout();
                break;
            case "help":
                printHelp();
                break;
            case "quit":
                quit();
                break;
        }


    }

    private void printHelp() {
        StringBuilder returnString = new StringBuilder();
        // Check for login status

        switch (currentState) {
            case LOGGED_OUT:
                returnString.append(SET_TEXT_COLOR_MAGENTA + "login <USERNAME> <PASSWORD>" + RESET_TEXT_COLOR + " - Login to an existing account." + "\n");
                returnString.append(SET_TEXT_COLOR_MAGENTA + "register <USERNAME> <PASSWORD> <EMAIL>" + RESET_TEXT_COLOR + " - Register a new account." + "\n");
                break;
            case LOGGED_IN:
                returnString.append(SET_TEXT_COLOR_MAGENTA).append("logout").append(RESET_TEXT_COLOR)
                        .append(" - Log out of").append(username).append(".").append("\n");
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
            currentState = Menu.userState.LOGGED_IN;

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
        currentState = Menu.userState.LOGGED_OUT;
        username = null;
        authToken = null;

        System.out.println("Logged out successfully.");
    }

    private void quit() throws Exception {
        if (currentState.equals(userState.LOGGED_IN)) {
            logout();
        }
        System.out.println(SET_TEXT_COLOR_BLUE + "Goodbye! :)");
        exit(0);
    }
}
