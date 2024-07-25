package service;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import dataaccess.dao.IntAuthDAO;
import dataaccess.dao.MemoryAuthDAO;
import model.AuthData;
import model.UserData;

public class AuthService {

    private final IntAuthDAO authDAO;

    public AuthService(IntAuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    /**
     * Logs in a user
     * @param user The UserData object to log in
     * @return The AuthData object of the given user
     * @throws UnauthorizedException Thrown when the passwords do not match
     */
    public AuthData loginUser(UserData user) throws UnauthorizedException, DataAccessException {
        return authDAO.createAuth(user.username());
    }

    /**
     * Logs out a user given their auth token
     * @param authToken The token of the user to log out
     * @throws UnauthorizedException Thrown when the token does not exist
     */
    public void logoutUser(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData authData = authDAO.getAuthByToken(authToken);
        if (authData == null) {
            throw new UnauthorizedException("unauthorized");
        }
        else {
            authDAO.deleteAuth(authToken);
        }
    }

    /**
     * Gets user information based on their given token
     * @param authToken The token of the user to get
     * @return The AuthData object of that user
     * @throws UnauthorizedException Thrown when the token does not exist
     */
    public AuthData getUserByAuthToken(String authToken) throws UnauthorizedException, DataAccessException {
        return authDAO.getAuthByToken(authToken);
    }

    /**
     * Validates that a given auth token exists
     * @param authToken The auth token to check
     * @throws UnauthorizedException Thrown when that token does not exist
     */
    public void validateAuthToken(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData authData = authDAO.getAuthByToken(authToken);
        if (authData == null || !authData.authToken().equals(authToken)) {
            throw new UnauthorizedException("unauthorized");
        }
    }

    /**
     * Clears all auth data
     */
    public void clear() throws BadRequestException {
        authDAO.clear();
    }
}
