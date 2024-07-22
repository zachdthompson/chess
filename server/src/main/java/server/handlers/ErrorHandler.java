package server.handlers;

import com.google.gson.Gson;
import spark.Response;

import java.util.Map;

public class ErrorHandler {

    private final Gson serializer = new Gson();

    /**
     * Easy way to handle all errors without repeating code
     * @param e the exception being thrown
     * @param res The response body to send it to
     * @param statusCode The HTTP status code to send
     * @return Serialized JSON object to send to HTTP body
     */
    public Object handleError(Exception e, Response res, int statusCode) {
        String body = serializer.toJson(Map.of("message", "Error: " + e.getMessage(), "success", false));
        res.type("application/json");
        res.status(statusCode);
        return body;
    }
}