package by.epam.safonenko.pharmacy.repository.impl;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Credit;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreditRepository implements Repository<Credit> {
    private static final String INSERT_CREDIT = "INSERT INTO pharmacy.credit (order_id, client, start_date, obligation) values(?, ?, ?, ?)";

    public void add(int orderId, String client, Date startDate, Date endDate, BigDecimal obligation) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(INSERT_CREDIT)) {
            statement.setInt(1, orderId);
            statement.setString(2, client);
            statement.setDate(3, startDate);
            statement.setBigDecimal(4, obligation);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
