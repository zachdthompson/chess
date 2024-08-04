import chess.*;

import ui.DrawBoard;
import ui.Menu;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {

    public static void main(String[] args) throws Exception {


        String server = "localhost";
        int port = 8080;
        Menu menu = new Menu(server, port);

        System.out.println("♕ CS240 Chess Client");
        System.out.println("Type 'Help' to get started");

        // Create an input scanner
        Scanner scanner = new Scanner(System.in);

        String input = "";

        while (!input.equalsIgnoreCase("quit")) {
            menu.printInputPrompt();
            input = scanner.nextLine();
            menu.handleInput(input);
        }
    }
}