package dataaccess.dao;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntGameDAOTest {

    private IntGameDAO gameDAO;

    @BeforeEach
    void setUp() throws DataAccessException, BadRequestException {
        (gameDAO = new IntGameDAO()).clear();
    }

    @Test
    void createGame() throws DataAccessException {
        GameData game = gameDAO.createGame("Testing");
        assertNotNull(game);
    }

    @Test
    void createInvalidGame() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> gameDAO.createGame(null));
    }

    @Test
    void getGame() {
    }

    @Test
    void readAllGames() {
    }

    @Test
    void updateGame() {
    }

    @Test
    void clear() {
    }
}