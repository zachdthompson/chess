package server.handlers;

import com.google.gson.Gson;
import dataaccess.UnauthorizedException;
import model.AuthData;
import model.UserData;
import service.AuthService;
import service.UserService;
import spark.Request;
import spark.Response;

public class LoginHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Authenticates a user, and then logs them in
     * @param req the request body
     * @param response the response body
     * @param userService the UserService object to interact with
     * @param authService the AuthService object to interact with
     * @return JSON serialized user object
     */
    public Object login(Request req, Response response, UserService userService, AuthService authService) {

        response.type("application/json");
        try {
            //Extract the user data
            UserData user = serializer.fromJson(req.body(), UserData.class);
            // Check the passwords match
            userService.validateUser(user);
            // Log the user in
            AuthData auth = authService.loginUser(user);
            response.status(200);
            return serializer.toJson(auth);
        }
        catch (UnauthorizedException e) {
            return errorHandler.handleError(e, response, 401);
        }
        catch (Exception e) {
            return errorHandler.handleError(e, response, 500);
        }
    }
}
