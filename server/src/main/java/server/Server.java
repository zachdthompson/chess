package server;

import dataaccess.BadRequestException;
import dataaccess.dao.*;
import spark.*;

import server.handlers.*;
import service.*;

public class Server {


    private final GameService gameService;
    private final UserService userService;
    private final AuthService authService;

    public Server() {
        IntAuthDAO authDAO = new IntAuthDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();
        IntUserDAO userDAO = new IntUserDAO();
        this.gameService = new GameService(gameDAO);
        this.userService = new UserService(userDAO);
        this.authService = new AuthService(authDAO);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // POST Methods
        Spark.post("/user", (req, res) -> new RegisterHandler().register(req, res, userService, authService));
        Spark.post("/session", (req, res) -> new LoginHandler().login(req, res, userService, authService));
        Spark.post("/game", (req, res) -> new CreateGameHandler().createGame(req, res, gameService, authService));

        // GET Methods
        Spark.get("/game", (req, res) -> new ListGamesHandler().listGames(req, res, gameService, authService));

        // DELETE Methods
        Spark.delete("/session", (req, res) -> new LogoutHandler().logout(req, res, authService));
        Spark.delete("/db", (req, res) -> new ClearHandler().clear(res, userService, gameService, authService));

        // PUT Methods
        Spark.put("/game", (req, res) -> new JoinGameHandler().joinGame(req, res, gameService, authService));

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
