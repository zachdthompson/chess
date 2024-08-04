package helper;

import com.google.gson.Gson;
import model.AuthData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ServerFacade {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    private final String serverAddress;

    public ServerFacade(String host, int port) {
        serverAddress = String.format("http://%s:%d/", host, port);
    }

    public AuthData register(String username, String password, String email) throws Exception {
        Map<String, String> body = Map.of(
                "username", username,
                "password", password,
                "email", email
        );
        return sendRequest("POST", "/user", body, null, AuthData.class);
    }

    public AuthData login(String username, String password) throws Exception {
        Map<String, String> body = Map.of(
                "username", username,
                "password", password
        );
        return sendRequest("POST", "/session", body, null, AuthData.class);
    }

    public void logout(String authToken) throws Exception {
        sendRequest("DELETE", "/session", null, authToken, AuthData.class);
    }

    public void clear() throws Exception {
        sendRequest("DELETE", "/db", null, null, null);
    }



    public <T> T sendRequest(String httpMethod, String endpoint, Object body, String authToken, Class<T> classType) throws Exception {

        // Initialize builder
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(new URI(serverAddress + endpoint))
                .method(httpMethod, HttpRequest.BodyPublishers.noBody());

        // Add Authorization header if authToken is provided
        if (authToken != null) {
            requestBuilder.header("Authorization", authToken);
        }

        // If there is a body, set it as the request body
        if (body != null) {
            String bodyJson = gson.toJson(body);
            requestBuilder.header("Content-Type", "application/json");
            requestBuilder.method(httpMethod, HttpRequest.BodyPublishers.ofString(bodyJson, StandardCharsets.UTF_8));
        }

        HttpRequest request = requestBuilder.build();

        // Send the request and handle the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Get the response code
        int statusCode = response.statusCode();
        if (statusCode == 200 ) {
            // Successful response, parse body if a classType is provided
            if (classType != null) {
                return gson.fromJson(response.body(), classType);
            }
            return null;
        } else {
            // Handle HTTP error responses
            throw new RuntimeException("HTTP request failed with status code: " + statusCode + " and body: " + response.body());
        }
    }
}
