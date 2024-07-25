package dataaccess.dao;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntUserDAOTest {

    private IntUserDAO intUserDAO;

    @BeforeEach
    void setUp() throws DataAccessException, BadRequestException {
        (intUserDAO = new IntUserDAO()).clear();
    }

    @Test
    void createUser() {
        UserData user = new UserData("testUser", "Password1", "email@email.com");
        assertDoesNotThrow(() -> intUserDAO.createUser(user));
    }

    @Test
    void createInvalidUser() {
        UserData user = new UserData("testUser", "Password1", "email@email.com");
        assertDoesNotThrow(() -> intUserDAO.createUser(user));
        assertThrows(DataAccessException.class, () -> intUserDAO.createUser(user));
    }

    @Test
    void getUser() {
        UserData user = new UserData("testUser", "Password1", "email@email.com");
        assertDoesNotThrow(() -> intUserDAO.createUser(user));
        try {
            UserData user1 = intUserDAO.getUser("testUser");
            assertNotNull(user1);
        }
        catch (DataAccessException e) {
            assertDoesNotThrow(() -> intUserDAO.getUser("testUser"));
        }

    }

    @Test
    void getInvalidUser() throws DataAccessException {
        UserData user = new UserData("testUser", "Password1", "email@email.com");
        assertDoesNotThrow(() -> intUserDAO.createUser(user));
        assertNull(intUserDAO.getUser("user1"));
    }

    @Test
    void clear() {
        assertDoesNotThrow(() -> {intUserDAO.clear();});
    }
}