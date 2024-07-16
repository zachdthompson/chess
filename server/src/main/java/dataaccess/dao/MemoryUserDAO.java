package dataaccess.dao;

import java.util.HashMap;
import java.util.Map;

import model.UserData;

public class MemoryUserDAO {

    private final Map<String, UserData> users = new HashMap<>();

    public MemoryUserDAO() {
    }

    /**
     * Creates and registers a user
     * @param user UserData of the user we want to register
     * @return UserData of the user
     */
    public UserData createUser(UserData user) {
        users.put(user.username(), user);
        return user;
    }

    /**
     * Gets the UserData object of the user from the map
     * @param username The username of the user we want
     * @return UserData object of the user
     */
    public UserData getUser(String username) {
        return users.get(username);
    }

    /**
     * Clears all users from the map.
     */
    public void clear() {
        users.clear();
    }
}
