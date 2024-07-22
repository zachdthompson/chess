package server.handlers;

import com.google.gson.Gson;
import service.AuthService;
import spark.Response;
import service.GameService;
import service.UserService;

public class ClearHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Clears all data when called
     * @param res The response body to modify
     * @param userService The UserService object to clear
     * @param gameService The GameService object to clear
     * @param authService The AuthService object to clear
     * @return The serialized JSON body
     */
    public Object clear(Response res, UserService userService, GameService gameService, AuthService authService) {
        res.type("application/json");
        try {
            userService.clear();
            gameService.clearGames();
            authService.clear();
            res.status(200);
            res.body("");
            return serializer.toJson(new Object());
        }
        catch (Exception e) {
            return errorHandler.handleError(e, res, 500);
        }
    }
}
