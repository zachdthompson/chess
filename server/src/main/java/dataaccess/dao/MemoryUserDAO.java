package dataaccess.dao;

import java.util.HashMap;
import java.util.Map;

import model.UserData;

public class MemoryUserDAO {
    private final Map<String, UserData> users = new HashMap<>();

    public MemoryUserDAO() {
    }

    public UserData createUser(UserData user) {
        users.put(user.username(), user);
        return user;
    }

    public UserData getUser(String username) {
        return users.get(username);
    }

    public void clear() {
        users.clear();
    }
}
