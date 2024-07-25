package service;

import dataaccess.DataAccessException;
import dataaccess.UserExistsException;

import dataaccess.BadRequestException;
import dataaccess.dao.IntAuthDAO;
import dataaccess.dao.IntGameDAO;
import dataaccess.dao.MemoryGameDAO;
import model.GameData;

import java.util.Collection;

public class GameService {

    private final MemoryGameDAO gameDAO;

    public GameService(MemoryGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    /**
     * Creates a game
     * @param gameData The information of the game we want to create
     * @return The new GameData object of a game
     * @throws BadRequestException Thrown when the GameName is empty
     */
    public GameData createGame(GameData gameData) throws BadRequestException {

        if (gameData.gameName() == null || gameData.gameName().isEmpty()) {
            throw new BadRequestException("missing name");
        }
        else {
            return gameDAO.createGame(gameData.gameName());
        }
    }

    /**
     * Gets a list of all active games
     * @return Collection of all active games
     * @throws DataAccessException Thrown when no games exist
     */
    public Collection<GameData> listAllGames() throws DataAccessException {
        return gameDAO.readAllGames();
    }

    /**
     * Attempts to join a given game with a given color
     * @param gameID The ID of the game to join
     * @param joinedColor The team color to join
     * @param username The username to join as
     * @return The GameData game of the new game to join
     * @throws UserExistsException Thrown if the color is taken
     * @throws BadRequestException Thrown if the game to join doesn't exist
     */
    public GameData joinGame(int gameID, String joinedColor, String username) throws UserExistsException, BadRequestException {
        GameData gameToJoin = gameDAO.getGame(gameID);

        if (gameToJoin == null) {
            throw new BadRequestException("bad request");
        }

        if ( joinedColor.equals("WHITE") && (gameToJoin.whiteUsername() == null || gameToJoin.whiteUsername().isEmpty())) {
            gameDAO.updateGame(
                    new GameData(
                            gameID,
                            username,
                            gameToJoin.blackUsername(),
                            gameToJoin.gameName(),
                            gameToJoin.game()
                    )
            );
        }
        else if (joinedColor.equals("BLACK") && (gameToJoin.blackUsername() == null || gameToJoin.blackUsername().isEmpty())) {
            gameDAO.updateGame(
                    new GameData(
                            gameID,
                            gameToJoin.whiteUsername(),
                            username,
                            gameToJoin.gameName(),
                            gameToJoin.game()
                    )
            );
        }
        else {
            throw new UserExistsException("already taken");
        }
        // Return the updated game
        return gameDAO.getGame(gameID);
    }

    /**
     * Clears all games
     */
    public void clearGames() {
        gameDAO.clear();
    }
}
