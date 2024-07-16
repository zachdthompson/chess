package dataaccess.dao;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO {

    private final Map<String, AuthData> authentications = new HashMap<>();

    public MemoryAuthDAO() {
    }

    /**
     * Creates an AuthData model for a given username
     * @param username The username of the user we are authenticating
     * @return AuthData object of the user and auth token
     */
    public AuthData createAuth(String username) {
        AuthData auth = new AuthData(AuthData.createToken(), username);
        authentications.put(username, auth);
        return auth;
    }

    /**
     * Gets the auth token for the given username
     * @param username The username of the user we want the token from
     * @return String of the AuthToken
     */
    public AuthData getAuth(String username) {
        return authentications.get(username);
    }

    /**
     * Clears all authentications
     */
    public void clear() {
        authentications.clear();
    }

}
