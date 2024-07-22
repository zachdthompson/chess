package server.handlers;

import com.google.gson.Gson;
import dataaccess.UnauthorizedException;
import service.AuthService;
import service.UserService;
import spark.Request;
import spark.Response;

public class LogoutHandler {

    private final Gson serializer = new Gson();
    private final ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Logs out the user of a given token
     * @param request The request body
     * @param response The response body
     * @param authService The AuthService object to interact with
     * @return JSON serialized empty body
     * @throws UnauthorizedException Thrown when the user is not authorized to access something, IE the token is invalid
     */
    public Object logout(Request request, Response response, AuthService authService) throws UnauthorizedException {

        response.type("application/json");
        try {
            String authToken = request.headers("Authorization");
            authService.logoutUser(authToken);
            response.status(200);
            // Return is empty on logout
            return serializer.toJson(new Object());
        }
        catch (UnauthorizedException e) {
            return errorHandler.handleError(e, response, 401);
        }
        catch (Exception e) {
            return errorHandler.handleError(e, response, 500);
        }
    }

}
