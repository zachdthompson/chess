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
    void getAuthByToken() {

    }

    @Test
    void getAuthByBadToken() {
    }

    @Test
    void deleteValidAuth() {
    }

    @Test
    void deleteInvalidAuth() {
    }

    @Test
    void clear() {
        assertDoesNotThrow(() -> {intAuthDAO.clear();});
    }
}