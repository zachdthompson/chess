package dataaccess.dao;

import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.SQLParent;
import model.AuthData;

public class IntAuthDAO extends SQLParent {

    private final String[] createStatement =
            new String[]{"""
                    CREATE TABLE IF NOT EXISTS Authentication
                    (
                      authToken VARCHAR(256) NOT NULL,
                      username VARCHAR(50) NOT NULL,
                      PRIMARY KEY (authToken),
                      FOREIGN KEY (username) REFERENCES User(username)
                    );
            """
    };

    private final String tableName = "Authentication";

    public IntAuthDAO() {
        createTable(createStatement);
    }


    public AuthData createAuth(String username) throws DataAccessException {
        AuthData newAuth = new AuthData(AuthData.createToken(), username);
        String updateStatement = "INSERT INTO Authentication (authToken, username) VALUES (?, ?)";

        updateQuery(updateStatement, newAuth.authToken(), username);

        return newAuth;
    }

    public AuthData getAuthByToken(String token) {
        // SQL HERE
        return null;
    }


    public void deleteAuth(String authToken) {
        // SQL HERE
    }

    public void clear() throws BadRequestException {
        clearTable(tableName);
    }

    public void drop() throws BadRequestException {
        dropTable(tableName);
    }


}
