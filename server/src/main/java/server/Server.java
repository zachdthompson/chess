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

    public Server() {
        MemoryAuthDAO authDao = new MemoryAuthDAO();
        MemoryGameDAO gameDao = new MemoryGameDAO();
        MemoryUserDAO userDao = new MemoryUserDAO();
        this.GameService = new GameService(gameDao, authDao);
        this.UserService = new UserService(userDao, authDao);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // POST Methods
        Spark.post("/user", (req, res) -> new RegisterHandler().register(req, res, UserService));
        Spark.post("/session", (req, res) -> new LoginHandler().login(req, res, UserService));
        Spark.post("/game", (req, res) -> new CreateGameHandler().createGame(req, res, GameService));

        // GET Methods
        Spark.get("/game", (req, res) -> new ListGamesHandler().listGames(req, res, GameService));

        // DELETE Methods
        Spark.delete("/session", (req, res) -> new LogoutHandler().logout(req, res, UserService));
        Spark.delete("/db", (req, res) -> new ClearHandler().clear(res, UserService, GameService));

        // PUT Methods
        Spark.put("/game", (req, res) -> new JoinGameHandler().joinGame(req, res, GameService));

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
