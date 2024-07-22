package server.handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import dataaccess.*;
import model.*;
import service.*;

public class RegisterHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Registers a user from the given information
     * @param req The request body
     * @param response The response body
     * @param userService The UserService object to interact with
     * @param authService The AuthService object to interact with
     * @return The JSON serialized user object
     */
    public Object register(Request req, Response response, UserService userService, AuthService authService) {
        response.type("application/json");
        try {
            // Register the user
            UserData user = userService.createUser(serializer.fromJson(req.body(), UserData.class));
            // Log the user in
            AuthData newAuth = authService.loginUser(user);
            response.status(200);
            return serializer.toJson(newAuth);
        }
        catch (BadRequestException error) {
            return errorHandler.handleError(error, response, 400);
        }
        catch (UserExistsException error) {
            return errorHandler.handleError(error, response, 403);
        }
        catch (Exception error) {
            return errorHandler.handleError(error, response, 500);
        }
    }
}