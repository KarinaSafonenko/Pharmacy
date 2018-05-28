package by.epam.safonenko.pharmacy.repository.impl;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProducerRepository implements Repository<Producer> {
    private static final String INSERT_PRODUCER = "INSERT into pharmacy.producer (name, country) values(?,?)";

    public void add(String name, String country) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement current = connection.prepareStatement(INSERT_PRODUCER)) {
            current.setString(1, name);
            current.setString(2, country);
            current.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
