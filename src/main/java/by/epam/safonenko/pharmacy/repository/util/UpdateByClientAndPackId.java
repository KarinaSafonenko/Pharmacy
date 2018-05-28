package by.epam.safonenko.pharmacy.repository.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UpdateByClientAndPackId {
    default void executeUpdate(PreparedStatement statement, String client, int packId) throws SQLException {
        statement.setString(1,client);
        statement.setInt(2,packId);
        statement.executeUpdate();
    }
}
