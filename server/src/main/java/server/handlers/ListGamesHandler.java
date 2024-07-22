package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataaccess.UnauthorizedException;
import model.GameData;
import service.AuthService;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class ListGamesHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Lists all active games
     * @param req The request body
     * @param res The response body
     * @param gameService The GameService object to interact with
     * @param authService The AuthService object to interact with
     * @return The serialized JSON list of game objects
     */
    public Object listGames(Request req, Response res, GameService gameService, AuthService authService) {

        res.type("application/json");
        try {
            // Authenticate user
            String authToken = req.headers("Authorization");
            authService.validateAuthToken(authToken);

            // Create a collection of games
            Collection<GameData> games = gameService.listAllGames();

            // Convert the collection into GSON
            Gson gson = new GsonBuilder().serializeNulls().create();
            JsonArray jsonArray = gson.toJsonTree(games).getAsJsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("games", jsonArray);

            // Return body
            res.status(200);
            return serializer.toJson(jsonObject);
        }
        catch (UnauthorizedException e) {
            return errorHandler.handleError(e, res, 401);
        }
        catch (Exception e) {
            return errorHandler.handleError(e, res, 500);
        }
    }
}
