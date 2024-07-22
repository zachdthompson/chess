package service;

import dataaccess.*;
import dataaccess.dao.MemoryUserDAO;
import model.*;

public class UserService {

    private final MemoryUserDAO userDAO;

    public UserService(MemoryUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Creates a user given the user data
     * @param user The data object parsed from the request
     * @return AuthData object of the authenticated user
     * @throws UserExistsException Thrown if the username exists already
     * @throws BadRequestException Thrown if password is empty
     */
    public UserData createUser(UserData user) throws UserExistsException, BadRequestException {

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
            return userDAO.getUser(user.username());
        }
    }

    /**
     * Validates a user login
     * @param user The user to try logging in
     * @throws UnauthorizedException Thrown when the passwords don't match
     */
    public void validateUser(UserData user) throws UnauthorizedException {
        // Create hashed user
        String hashedPassword = MemoryUserDAO.hashPassword(user.password());
        UserData hashedUser = new UserData(user.username(), hashedPassword, user.email());

        UserData validateUser = userDAO.getUser(hashedUser.username());

        // If the user does not exist or the passwords dont match
        if (validateUser == null || !validateUser.password().equals(hashedUser.password())) {
            throw new UnauthorizedException("unauthorized");
        }
    }

    /**
     * Clears both users and authentications
     */
    public void clear() {
        userDAO.clear();
    }

}
