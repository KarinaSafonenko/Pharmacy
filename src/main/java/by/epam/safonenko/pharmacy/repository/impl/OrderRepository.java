package by.epam.safonenko.pharmacy.repository.impl;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderRepository implements Repository<Order> {
    private static final String INSERT_ORDER = "INSERT INTO pharmacy.order (client, address, payment, date) values(?, ?, ?, ?)";

    public void add(String client, String address, Order.PaymentType paymentType, String date) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {
            statement.setString(1, client);
            statement.setString(2, address);
            statement.setString(3, paymentType.name());
            statement.setString(4, date);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
