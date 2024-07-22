package service;

import dataaccess.*;
import dataaccess.dao.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private static final Logger Log = LoggerFactory.getLogger(AuthServiceTest.class);
    private AuthService authService;
    private MemoryAuthDAO memoryAuthDAO;
    private MemoryUserDAO memoryUserDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        memoryAuthDAO = new MemoryAuthDAO();
        memoryUserDAO = new MemoryUserDAO();
        authService = new AuthService(memoryAuthDAO);
    }

    @Test
    void loginUser() throws DataAccessException, UnauthorizedException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        assertNotNull(authData);

    }

    @Test
    void logoutUser() throws UnauthorizedException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();
        authService.logoutUser(authToken);
    }

    @Test
    void getUserByAuthToken() throws UnauthorizedException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);

        AuthData authData = authService.loginUser(checkUser);

        String authToken = authData.authToken();

        AuthData findUser = authService.getUserByAuthToken(authToken);

        assertNotNull(findUser);
    }

    @Test
    void validateAuthToken() throws UnauthorizedException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);

        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();

        // This will throw if not found
        authService.validateAuthToken(authToken);
    }

    @Test
    void clear() throws UnauthorizedException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();

        authService.clear();

        assertNull(authService.getUserByAuthToken(authToken));
    }
}