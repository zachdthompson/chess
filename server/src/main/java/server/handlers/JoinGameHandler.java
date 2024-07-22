package server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.BadRequestException;
import dataaccess.UserExistsException;
import dataaccess.UnauthorizedException;
import model.AuthData;
import model.GameData;
import service.AuthService;
import service.GameService;
import spark.Response;
import spark.Request;

public class JoinGameHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Will attempt to join a given game with a given color
     * @param req The request body
     * @param res The response body
     * @param gameService The GameService object to interact with
     * @param authService The AuthService object to interact with
     * @return Serialized JSON game object
     */
    public Object joinGame(Request req, Response res, GameService gameService, AuthService authService) {
        res.type("application/json");
        try {
            // Authenticate user
            String authToken = req.headers("Authorization");
            authService.validateAuthToken(authToken);
            AuthData auth = authService.getUserByAuthToken(authToken);
            String username = auth.username();

            // Pull data from the request body
            JsonElement bodyJsonElement = JsonParser.parseString(req.body());
            JsonObject jsonObject = bodyJsonElement.getAsJsonObject();

            // Initialize variables
            String playerColor;
            int gameId;
            try {
                // Try to pull the data from the body
                playerColor = jsonObject.get("playerColor").getAsString();
                gameId = jsonObject.get("gameID").getAsInt();
            }
            catch (Exception e) {
                return errorHandler.handleError(e, res, 400);
            }
            GameData gameData = gameService.joinGame(gameId, playerColor, username);
            return serializer.toJson(gameData);
        }
        catch (BadRequestException e) {
            return errorHandler.handleError(e, res, 400);
        }
        catch (UserExistsException e) {
            return errorHandler.handleError(e, res, 403);
        }
        catch (UnauthorizedException e) {
            return errorHandler.handleError(e, res, 401);
        }
        catch (Exception e) {
            return errorHandler.handleError(e, res, 500);
        }
    }
}
