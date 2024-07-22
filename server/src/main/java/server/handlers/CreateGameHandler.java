package server.handlers;

import com.google.gson.Gson;
import dataaccess.BadRequestException;
import dataaccess.UnauthorizedException;
import model.GameData;
import service.AuthService;
import service.GameService;
import spark.Request;
import spark.Response;

import javax.swing.text.DefaultStyledDocument;
import java.util.Map;

public class CreateGameHandler {

    private final Gson serializer = new Gson();
    ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Creates a game after authenticating the user
     * @param request The request body
     * @param response The response body
     * @param gameService The GameService object to interact with
     * @param authService The AuthService object to interact with
     * @return Serialized JSON game object
     */
    public Object createGame(Request request, Response response, GameService gameService, AuthService authService) {

        response.type("application/json");
        try {
            // Authenticate the user
            String authToken = request.headers("Authorization");
            authService.validateAuthToken(authToken);

            // Create a new game
            GameData newGameData = serializer.fromJson(request.body(), GameData.class);
            int newGameID = gameService.createGame(newGameData).gameID();
            response.status(200);
            return serializer.toJson(Map.of("gameID", newGameID));
        }
        catch (UnauthorizedException exception) {
            return errorHandler.handleError(exception, response, 401);
        }
        catch (BadRequestException exception) {
            return errorHandler.handleError(exception, response, 400);
        }
        catch (Exception exception) {
            return errorHandler.handleError(exception, response, 500);
        }
    }
}
