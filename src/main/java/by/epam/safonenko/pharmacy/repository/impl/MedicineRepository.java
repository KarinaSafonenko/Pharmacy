package by.epam.safonenko.pharmacy.repository.impl;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.Repository;
import by.epam.safonenko.pharmacy.repository.util.DeleteById;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicineRepository implements Repository<Medicine>, DeleteById {
    private static final String INSERT_MEDICINE = "INSERT INTO pharmacy.medicine (name, category, recipe_need, description) values(?, ?, ?, ?)";
    private static final String DELETE_MEDICINE = "DELETE FROM pharmacy.medicine WHERE medicine_id = ?";

    public void add(String name, Medicine.ProductCategory category, boolean recipeNeed, String description) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(INSERT_MEDICINE)) {
            current.setString(1, name);
            current.setString(2, category.name());
            current.setBoolean(3, recipeNeed);
            current.setString(4, description);
            current.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    public void delete(int medicineId) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(DELETE_MEDICINE)) {
            deleteBtId(current,medicineId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
