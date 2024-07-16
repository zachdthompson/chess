package dataaccess;

/**
 * Indicates that the URL requested does not exist
 */
public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
