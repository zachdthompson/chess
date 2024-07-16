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

    public Object register(Request req, Response response, UserService userService) {
        response.type("application/json");
        try {
            AuthData newAuth = userService.createUser(serializer.fromJson(req.body(), UserData.class));
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