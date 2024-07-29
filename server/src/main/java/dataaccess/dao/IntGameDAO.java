package dataaccess.dao;

import chess.ChessGame;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.SQLParent;
import model.AuthData;
import model.GameData;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import com.google.gson.Gson;

public class IntGameDAO extends SQLParent {

    private final String[] createStatement =
            new String[]{"""
                    CREATE TABLE IF NOT EXISTS Games
                    (
                        gameID SERIAL,
                        whiteUsername VARCHAR(256),
                        blackUsername VARCHAR(256),
                        gameName VARCHAR(256) NOT NULL,
                        chessGame JSON NOT NULL,
                        PRIMARY KEY (gameID)
                    );
            """
            };

    private final String tableName = "Games";

    public IntGameDAO(){
        createTable(createStatement);
    }


    /**
     * Creates GameData object for use.
     * Will randomly generate the ID within the max int values.
     * @param gameName What to name the game
     * @return GameData object of the new game
     */
    public GameData createGame(String gameName) throws DataAccessException {

        ChessGame game = new ChessGame();
        int gameID = 0;

        Gson gson = new Gson();
        String gameJson = String.valueOf(gson.toJsonTree(game));

        String sql = "INSERT INTO " + tableName + "(whiteUsername, blackUsername, gameName, chessGame) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection()){
            try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, null);
                ps.setString(2, null);
                ps.setString(3, gameName);
                ps.setString(4, gameJson);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        gameID = generatedId;
                    }
                }


            }

        }
        catch (SQLException | DataAccessException e) {
            throw new DataAccessException(String.format("Unable to modify database: %s", e.getMessage()));
        }

        if (gameID == 0) {
            throw new DataAccessException(String.format("Unable to create game: %s", gameName));
        }

        GameData newGameData = new GameData(
                gameID,
                null,
                null,
                gameName,
                game
        );

        return newGameData;
    }

    /**
     * Will get the GameData object for the given gameID
     * @param gameID The game ID of the game to return
     * @return GameData object of the given gameID
     */
    public GameData getGame(int gameID) throws DataAccessException {
        String sql = "SELECT * FROM " + tableName + " WHERE gameID = ?";

        try (Connection conn = DatabaseManager.getConnection()){
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, gameID);

                // Pull data from the results
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String whiteUsername = rs.getString("whiteUsername");
                        String blackUsername = rs.getString("blackUsername");
                        String gameName = rs.getString("gameName");
                        String chessGame = rs.getString("chessGame");

                        // Rebuild game
                        Gson gson = new Gson();
                        ChessGame game = gson.fromJson(chessGame, ChessGame.class);
                        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
                    }
                }
            }
        }
        catch (SQLException | DataAccessException e) {
            throw new DataAccessException(String.format("Unable to modify database: %s", e.getMessage()));
        }
        return null;
    }

    /**
     * Returns all currently stored games.
     * @return Collection of all GameData objects
     */
    public Collection<GameData> readAllGames() throws DataAccessException {
        String sql = "SELECT gameID FROM " + tableName;
        ArrayList<GameData> games = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Execute the query to get all game IDs
                try (ResultSet rs = ps.executeQuery()) {
                    // Execute for every line
                    while (rs.next()) {
                        int gameID = rs.getInt("gameID");
                        GameData gameData = getGame(gameID);
                        if (gameData != null) {
                            games.add(gameData);
                        }
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(String.format("Unable to read all games: %s", e.getMessage()));
        }

        return games;
    }

    /**
     * Updates the settings of a given game
     * @param game The game to update
     */
    public void updateGame(GameData game) {

    }

    /**
     * Clears all stored games
     */
    public void clear() throws BadRequestException {
        clearTable(tableName);
    }

    public void drop() throws BadRequestException {
        dropTable(tableName);
    }
}
