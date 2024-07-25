package dataaccess.dao;

import dataaccess.BadRequestException;
import dataaccess.SQLParent;

public class IntAuthDAO extends SQLParent {

    private final String[] createStatement =
            new String[]{"""
                    CREATE TABLE Authentication
                    (
                      authToken VARCHAR(256) NOT NULL,
                      username INT NOT NULL,
                      PRIMARY KEY (authToken),
                      FOREIGN KEY (username) REFERENCES User(username)
                    );
            """
    };

    public IntAuthDAO() throws BadRequestException {
        createTable(createStatement);
    }
}
