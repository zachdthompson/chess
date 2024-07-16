package service;

import dataaccess.*;
import dataaccess.dao.MemoryAuthDAO;
import dataaccess.dao.MemoryUserDAO;
import model.*;
import org.eclipse.jetty.server.Authentication;

import java.lang.ref.PhantomReference;

public class UserService {

    private final MemoryUserDAO userDAO;
    private final MemoryAuthDAO authDAO;

    public UserService(MemoryUserDAO userDAO, MemoryAuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

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

    public AuthData loginUser(UserData user) throws UnauthorizedException, BadRequestException {

        // Create hashed user
        String hashedPassword = MemoryUserDAO.hashPassword(user.password());
        UserData hashedUser = new UserData(user.username(), hashedPassword, user.email());

        UserData validateUser = userDAO.getUser(hashedUser.username());

        // If the user does not exist or the passwords dont match
        if (validateUser == null || !validateUser.password().equals(hashedUser.password())) {
            throw new UnauthorizedException("unauthorized");
        }

        return authDAO.createAuth(hashedUser.username());

    }

    /**
     * Clears both users and authentications
     */
    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }

}
