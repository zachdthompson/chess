package dataaccess.dao;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

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
    void getGame() throws DataAccessException {
        GameData game = gameDAO.createGame("Testing");
        int gameID = game.gameID();

        GameData getGame = gameDAO.getGame(gameID);

        assertEquals(game, getGame);
    }

    @Test
    void readAllGames() throws DataAccessException {

        GameData game = gameDAO.createGame("testing1");
        GameData game2 = gameDAO.createGame("testing2");
        GameData game3 = gameDAO.createGame("testing3");

        Collection<GameData> games = gameDAO.readAllGames();

        assertEquals(3, games.size());
    }

    @Test
    void readAllGamesAfterBadInsert() throws DataAccessException {

        GameData game1 = gameDAO.createGame("testing1");
        GameData game2 = gameDAO.createGame("testing2");
        GameData game3 = null;


        Collection<GameData> readGames = gameDAO.readAllGames();

        assertTrue(readGames.contains(game1));
        assertTrue(readGames.contains(game2));
        assertFalse(readGames.contains(game3));
    }

    @Test
    void readEmptyGame() throws DataAccessException {
        assertTrue(gameDAO.readAllGames().isEmpty());
    }


    @Test
    void updateGame() {

    }

    @Test
    void clear() {
    }
}