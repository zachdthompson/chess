package service;

import dataaccess.*;
import dataaccess.dao.IntUserDAO;
import dataaccess.dao.MemoryUserDAO;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final IntUserDAO userDAO;

    public UserService(IntUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Creates a user given the user data
     * @param user The data object parsed from the request
     * @return AuthData object of the authenticated user
     * @throws UserExistsException Thrown if the username exists already
     * @throws BadRequestException Thrown if password is empty
     */
    public UserData createUser(UserData user) throws UserExistsException, BadRequestException, DataAccessException {

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
    public void validateUser(UserData user) throws UnauthorizedException, DataAccessException {

        UserData validateUser = userDAO.getUser(user.username());

        // If the user does not exist or the passwords dont match
        if (validateUser == null || !BCrypt.checkpw(user.password(), validateUser.password())) {
            throw new UnauthorizedException("unauthorized");
        }
    }

    /**
     * Clears both users and authentications
     */
    public void clear() throws BadRequestException, DataAccessException {
        userDAO.clear();
    }

}
