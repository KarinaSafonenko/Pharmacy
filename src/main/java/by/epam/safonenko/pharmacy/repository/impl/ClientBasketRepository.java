package by.epam.safonenko.pharmacy.repository.impl;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.Repository;
import by.epam.safonenko.pharmacy.repository.util.UpdateByClientAndPackId;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientBasketRepository implements Repository<Basket>, UpdateByClientAndPackId {
    private static final String INSERT_IN_BASKET = "INSERT INTO pharmacy.client_basket (client, pack_id, amount) values(?, ?, ?)";
    private static final String DELETE_FROM_BASKET = "DELETE FROM pharmacy.client_basket WHERE client = ? AND pack_id = ?";

    public void add(String client, int packId, int amount) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(INSERT_IN_BASKET)) {
            current.setInt(3, amount);
            executeUpdate(current, client, packId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    public void delete(String client, int packId) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(DELETE_FROM_BASKET)) {
            executeUpdate(current, client, packId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
