package server;

import dataaccess.DataAccess;
import dataaccess.dao.MemoryAuthDAO;
import dataaccess.dao.MemoryGameDAO;
import dataaccess.dao.MemoryUserDAO;
import spark.*;

import server.handlers.*;
import service.*;

public class Server {


    private final GameService GameService;
    private final UserService UserService;
    private final AuthService AuthService;

    public Server() {
        MemoryAuthDAO authDao = new MemoryAuthDAO();
        MemoryGameDAO gameDao = new MemoryGameDAO();
        MemoryUserDAO userDao = new MemoryUserDAO();
        this.GameService = new GameService(gameDao);
        this.UserService = new UserService(userDao);
        this.AuthService = new AuthService(authDao);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // POST Methods
        Spark.post("/user", (req, res) -> new RegisterHandler().register(req, res, UserService, AuthService));
        Spark.post("/session", (req, res) -> new LoginHandler().login(req, res, UserService, AuthService));
        Spark.post("/game", (req, res) -> new CreateGameHandler().createGame(req, res, GameService, AuthService));

        // GET Methods
        Spark.get("/game", (req, res) -> new ListGamesHandler().listGames(req, res, GameService, AuthService));

        // DELETE Methods
        Spark.delete("/session", (req, res) -> new LogoutHandler().logout(req, res,AuthService));
        Spark.delete("/db", (req, res) -> new ClearHandler().clear(res, UserService, GameService, AuthService));

        // PUT Methods
        Spark.put("/game", (req, res) -> new JoinGameHandler().joinGame(req, res, GameService, AuthService));

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
