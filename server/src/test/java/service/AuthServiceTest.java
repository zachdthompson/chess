package service;

import dataaccess.*;
import dataaccess.dao.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;
    private IntAuthDAO authDAO;
    private IntUserDAO userDAO;

    @BeforeEach
    public void setup() throws DataAccessException, BadRequestException {
        authDAO = new IntAuthDAO();
        userDAO = new IntUserDAO();
        authService = new AuthService(authDAO);

        authDAO.clear();
        userDAO.clear();
    }

    @Test
    void loginUser() throws DataAccessException, UnauthorizedException {
        UserData checkUser = new UserData("user1", "password", null);
        userDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        assertNotNull(authData);

    }

    @Test
    void logoutUser() throws UnauthorizedException, DataAccessException {
        UserData checkUser = new UserData("user1", "password", null);
        userDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();
        authService.logoutUser(authToken);
    }

    @Test
    void getUserByAuthToken() throws UnauthorizedException, DataAccessException {
        UserData checkUser = new UserData("user1", "password", null);
        userDAO.createUser(checkUser);

        AuthData authData = authService.loginUser(checkUser);

        String authToken = authData.authToken();

        AuthData findUser = authService.getUserByAuthToken(authToken);

        assertNotNull(findUser);
    }

    @Test
    void validateAuthToken() throws UnauthorizedException, DataAccessException {
        UserData checkUser = new UserData("user1", "password", null);
        userDAO.createUser(checkUser);

        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();

        // This will throw if not found
        authService.validateAuthToken(authToken);
    }

    @Test
    void clear() throws UnauthorizedException, DataAccessException, BadRequestException {
        UserData checkUser = new UserData("user1", "password", null);
        userDAO.createUser(checkUser);
        AuthData authData = authService.loginUser(checkUser);
        String authToken = authData.authToken();

        authService.clear();

        assertNull(authService.getUserByAuthToken(authToken));
    }
}