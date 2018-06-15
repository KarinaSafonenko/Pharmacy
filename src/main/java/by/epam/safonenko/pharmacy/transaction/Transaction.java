package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Transaction {
    protected PreparedStatement preparedStatement;
    protected ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();

    public void execute() throws TransactionException {
        try{
            performTransaction();
        } catch (SQLException e) {
            throw new TransactionException(e);
        } finally {
            release();
        }
    }

    protected abstract void performTransaction() throws SQLException, TransactionException;

    private void release() throws TransactionException {
        if (proxyConnection != null) {
            proxyConnection.close();
        }
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

}
