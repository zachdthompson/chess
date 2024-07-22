package dataaccess.dao;

import model.GameData;
import chess.ChessGame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.security.SecureRandom;

public class MemoryGameDAO {

    private final Map<Integer, GameData> games = new HashMap<>();

    public MemoryGameDAO() {
    }

    /**
     * Creates GameData object for use.
     * Will randomly generate the ID within the max int values.
     * @param gameName What to name the game
     * @return GameData object of the new game
     */
    public GameData createGame(String gameName) {

        int newGameID = generateNewGameID();

        GameData newGameData = new GameData(
                newGameID,
                null,
                null,
                gameName,
                new ChessGame()
        );

        games.put(newGameID, newGameData);
        return newGameData;
    }

    /**
     * Will get the GameData object for the given gameID
     * @param gameID The game ID of the game to return
     * @return GameData object of the given gameID
     */
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    /**
     * Returns all currently stored games.
     * @return Collection of all GameData objects
     */
    public Collection<GameData> readAllGames() {
        return games.values();
    }

    /**
     * Updates the settings of a given game
     * @param game The game to update
     */
    public void updateGame(GameData game) {
        games.put(game.gameID(), game);
    }

    /**
     * Clears all stored games
     */
    public void clear() {
        games.clear();
    }

    /**
     * Securely generates a random game ID that doesn't overlap with others
     * @return Random int value
     */
    private int generateNewGameID() {
        SecureRandom random = new SecureRandom();
        int randomID = random.nextInt(Integer.MAX_VALUE);
        // Regenerate the number until we get one that isn't used, if we do somehow...
        while (games.containsKey(randomID)) {
            randomID = random.nextInt(Integer.MAX_VALUE);
        }
        return randomID;
    }
}
