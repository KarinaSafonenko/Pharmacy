package by.epam.safonenko.pharmacy.repository.impl;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.Repository;
import by.epam.safonenko.pharmacy.repository.util.DeleteById;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PackRepository implements Repository<Pack>, DeleteById {
    private static final String INSERT_PACK = "INSERT INTO pharmacy.pack(medicine_id, producer_id, quantity, dosage, price, amount, image_path) values (?,?,?,?,?,?,?)";
    private static final String DELETE_PACK = "DELETE FROM pharmacy.pack WHERE pack_id = ?";

    public void add(int medicineId, int producerId, int quantity, int dosage, BigDecimal price, int amount, String imagePath) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(INSERT_PACK)) {
            current.setInt(1, medicineId);
            current.setInt(2, producerId);
            current.setInt(3, quantity);
            current.setInt(4, dosage);
            current.setBigDecimal(5, price);
            current.setInt(6, amount);
            current.setString(7, imagePath);
            current.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    public void delete(int packId) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(DELETE_PACK)) {
            deleteBtId(current, packId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
