package service;

import dataaccess.dao.MemoryAuthDAO;
import dataaccess.dao.MemoryGameDAO;
import dataaccess.dao.MemoryUserDAO;

public class GameService {

    private final MemoryGameDAO gameDAO;
    private final MemoryAuthDAO authDAO;

    public GameService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public void clearGames() {
        gameDAO.clear();
    }
}
