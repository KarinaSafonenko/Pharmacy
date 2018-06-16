package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DecreaseMoneyAmount {
    String DECREASE_MONEY_AMOUNT = "UPDATE pharmacy.credit_card SET money_amount = money_amount - ? WHERE login = ?";

    default void decreaseMoneyAmount(PreparedStatement preparedStatement, ProxyConnection proxyConnection, String client, BigDecimal sum) throws TransactionException, SQLException {
        preparedStatement = proxyConnection.prepareStatement(DECREASE_MONEY_AMOUNT);
        preparedStatement.setBigDecimal(1, sum);
        preparedStatement.setString(2, client);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while decreasing money amount.");
        }
    }
}
