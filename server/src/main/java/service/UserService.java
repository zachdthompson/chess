package service;

import dataaccess.*;
import dataaccess.dao.MemoryAuthDAO;
import dataaccess.dao.MemoryUserDAO;
import model.*;

public class UserService {

    private final MemoryUserDAO userDAO;
    private final MemoryAuthDAO authDAO;

    public UserService(MemoryUserDAO userDAO, MemoryAuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    /**
     * Creates a user given the user data
     * @param user The data object parsed from the request
     * @return AuthData object of the authenticated user
     * @throws UserExistsException Thrown if the username exists already
     * @throws BadRequestException Thrown if password is empty
     */
    public AuthData createUser(UserData user) throws UserExistsException, BadRequestException {

        UserData checkUsername = userDAO.getUser(user.username());

        // Check if user exists
        if (checkUsername != null) {
            throw new UserExistsException("already exists");
        }
        // Check if password is empty
        else if (user.password() == null || user.password().isEmpty()) {
            throw new BadRequestException("bad request");
        }
        // Create if no errors
        else {
            userDAO.createUser(user);
            return authDAO.createAuth(user.username());
        }
    }

    public AuthData loginUser(UserData user) throws UnauthorizedException {

        // This will throw an error if it fails
        validateUser(user);

        return authDAO.createAuth(user.username());

    }

    public void logoutUser(String authToken) throws UnauthorizedException {
        AuthData authData = authDAO.getAuthByToken(authToken);
        if (authData == null) {
            throw new UnauthorizedException("unauthorized");
        }
        else {
            authDAO.deleteAuth(authToken);
        }
    }

    /**
     * Clears both users and authentications
     */
    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }

    private void validateUser(UserData user) throws UnauthorizedException {
        // Create hashed user
        String hashedPassword = MemoryUserDAO.hashPassword(user.password());
        UserData hashedUser = new UserData(user.username(), hashedPassword, user.email());

        UserData validateUser = userDAO.getUser(hashedUser.username());

        // If the user does not exist or the passwords dont match
        if (validateUser == null || !validateUser.password().equals(hashedUser.password())) {
            throw new UnauthorizedException("unauthorized");
        }
    }

}
