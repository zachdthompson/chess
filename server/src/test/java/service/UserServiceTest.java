package service;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import dataaccess.UserExistsException;
import dataaccess.dao.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private IntUserDAO userDAO;
    private IntGameDAO gameDAO;
    private IntAuthDAO authDAO;
    private UserService userService;

    @BeforeEach
    void setUp() throws BadRequestException {
        userDAO = new IntUserDAO();
        gameDAO = new IntGameDAO();
        authDAO = new IntAuthDAO();

        userService = new UserService(userDAO);

        userDAO.clear();

    }

    @Test
    void createValidUser() throws UserExistsException, BadRequestException, UnauthorizedException, DataAccessException {
        UserData userData = new UserData("bob", "password", "email@email.com");
        userService.createUser(userData);

        // This will throw if it doesn't work
        userService.validateUser(userData);
    }

    @Test
    void createInvalidUser() throws UserExistsException, BadRequestException {
        UserData userData = new UserData("bob", null, "email@email.com");
        assertThrows(BadRequestException.class, () -> userService.createUser(userData));
    }

    @Test
    void createDuplicateUser() throws UserExistsException, BadRequestException, UnauthorizedException, DataAccessException {
        UserData userData = new UserData("bob", "password", "email@email.com");
        // Create it
        userService.createUser(userData);
        assertThrows(UserExistsException.class, () -> userService.createUser(userData));

    }

    @Test
    void clear() throws UserExistsException, BadRequestException, UnauthorizedException, DataAccessException {
        UserData userData = new UserData("bob", "password", "email@email.com");
        userService.createUser(userData);
        userService.clear();

        assertThrows(UnauthorizedException.class, () -> userService.validateUser(userData));
    }
}