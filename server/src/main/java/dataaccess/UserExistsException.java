package dataaccess;

/**
 * Indicates that the user to register already exists
 */
public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
}
