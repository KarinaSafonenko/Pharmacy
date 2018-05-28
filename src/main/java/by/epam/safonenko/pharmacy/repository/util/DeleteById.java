package by.epam.safonenko.pharmacy.repository.util;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DeleteById {

    default void deleteBtId(PreparedStatement current, int medicineId) throws SQLException {
            current.setInt(1, medicineId);
            current.executeUpdate();
    }
}
