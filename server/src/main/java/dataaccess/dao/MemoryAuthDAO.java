package dataaccess.dao;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO {

    private final Map<AuthData, String> authentications = new HashMap<>();

    public MemoryAuthDAO() {
    }

    /**
     * Creates an AuthData model for a given username
     * @param username The username of the user we are authenticating
     * @return AuthData object of the user and auth token
     */
    public AuthData createAuth(String username) {
        AuthData auth = new AuthData(AuthData.createToken(), username);
        authentications.put(auth, username);
        return auth;
    }

    /**
     * Gets auth data from a token
     * @param token The authToken of the user
     * @return The AuthData object of the token
     */
    public AuthData getAuthByToken(String token) {
        for (AuthData auth : authentications.keySet()) {
            if (auth.authToken().equals(token)) {
                return auth;
            }
        }
        return null;
    }

    /**
     * Deletes the auth token from the list of valid authentications
     * @param authToken The token to remove
     */
    public void deleteAuth(String authToken) {
        AuthData auth = getAuthByToken(authToken);
        authentications.remove(auth);
    }

    /**
     * Clears all authentications
     */
    public void clear() {
        authentications.clear();
    }

}
