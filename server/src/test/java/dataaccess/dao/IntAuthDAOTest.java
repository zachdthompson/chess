package dataaccess.dao;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import model.AuthData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntAuthDAOTest {

    private IntAuthDAO intAuthDAO;

    @BeforeEach
    void setUp() throws DataAccessException, BadRequestException {
        (intAuthDAO = new IntAuthDAO()).clear();
    }

    @Test
    void createValidAuth() {
        assertDoesNotThrow(() -> {
            AuthData newAuth = intAuthDAO.createAuth("testUser");});
    }


    @Test
    void createInvalidAuth() {
        assertThrows(DataAccessException.class, () -> {intAuthDAO.createAuth("");});
    }

    @Test
    void getAuthByToken() throws DataAccessException {
        AuthData newAuth = intAuthDAO.createAuth("testUser");
        assertDoesNotThrow(() -> {intAuthDAO.getAuthByToken(newAuth.authToken());});
    }

    @Test
    void getAuthByBadToken() throws DataAccessException {
        assertNull(intAuthDAO.getAuthByToken("badToken"));
    }

    @Test
    void deleteValidAuth() throws DataAccessException {
        AuthData newAuth = intAuthDAO.createAuth("testUser");
        assertDoesNotThrow(() -> {intAuthDAO.deleteAuth(newAuth.authToken());});
        assertNull(intAuthDAO.getAuthByToken(newAuth.authToken()));
    }

    @Test
    void deleteInvalidAuth() {
    }

    @Test
    void clear() {
        assertDoesNotThrow(() -> {intAuthDAO.clear();});
    }
}