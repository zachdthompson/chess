package dataaccess;

import java.sql.*;

public class SQLParent {


    protected void createTable(String[] createTableStatement) throws BadRequestException, RuntimeException {
        try {
            DatabaseManager.createDatabase();
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try (java.sql.Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createTableStatement) {
                try (java.sql.PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException | DataAccessException e) {
            try {
                throw new BadRequestException(String.format("Unable to configure database: %s", e.getMessage()));
            }
            catch (BadRequestException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
