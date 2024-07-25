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

    private AuthService authService;
    private IntAuthDAO authDAO;
    private MemoryUserDAO memoryUserDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new IntAuthDAO();
        memoryUserDAO = new MemoryUserDAO();
        authService = new AuthService(authDAO);
    }

    @Test
    void loginUser() throws DataAccessException, UnauthorizedException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        assertNotNull(authData);

    }

    @Test
    void logoutUser() throws UnauthorizedException, DataAccessException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();
        authService.logoutUser(authToken);
    }

    @Test
    void getUserByAuthToken() throws UnauthorizedException, DataAccessException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);

        AuthData authData = authService.loginUser(checkUser);

        String authToken = authData.authToken();

        AuthData findUser = authService.getUserByAuthToken(authToken);

        assertNotNull(findUser);
    }

    @Test
    void validateAuthToken() throws UnauthorizedException, DataAccessException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);

        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();

        // This will throw if not found
        authService.validateAuthToken(authToken);
    }

    @Test
    void clear() throws UnauthorizedException, DataAccessException, BadRequestException {
        UserData checkUser = new UserData("user1", "password", null);
        memoryUserDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();

        authService.clear();

        assertNull(authService.getUserByAuthToken(authToken));
    }
}