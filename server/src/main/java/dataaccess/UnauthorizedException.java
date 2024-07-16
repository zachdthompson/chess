package dataaccess;

/**
 * Indicates the user is not authorized to do something
 */
public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}