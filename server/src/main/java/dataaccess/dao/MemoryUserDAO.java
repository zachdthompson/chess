package dataaccess.dao;

import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        // Store the user password as a SHA-512 hash
        String hashedPassword = hashPassword(user.password());

        UserData hashedUser = new UserData(user.username(),hashedPassword, user.email());

        users.put(user.username(), hashedUser);
        return hashedUser;
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

    /**
     * Hash the password in SHA-512 for secure storage
     * This function was taken from ChatGPT
     * @param password The password to hash
     * @return String of SHA-512 hashed password
     */
    public static String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // Convert byte array into signum representation
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            // Return complete hashed password in hex format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
