package service;

import dataaccess.BadRequestException;
import dataaccess.UnauthorizedException;
import dataaccess.dao.MemoryAuthDAO;
import dataaccess.dao.MemoryGameDAO;
import dataaccess.dao.MemoryUserDAO;
import model.AuthData;
import model.GameData;

public class GameService {

    private final MemoryGameDAO gameDAO;
    private final MemoryAuthDAO authDAO;

    public GameService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public GameData createGame(GameData gameData, String authToken) throws UnauthorizedException, BadRequestException {

        // Authenticate user
        AuthData authentication = authDAO.getAuth(authToken);
        if (authentication == null) {
            throw new UnauthorizedException("unauthorized");
        }
        if (gameData.gameName() == null || gameData.gameName().isEmpty()) {
            throw new BadRequestException("missing name");
        }
        else {
            gameDAO.createGame(gameData.gameName());
            return gameData;
        }
    }

    public void clearGames() {
        gameDAO.clear();
    }
}
