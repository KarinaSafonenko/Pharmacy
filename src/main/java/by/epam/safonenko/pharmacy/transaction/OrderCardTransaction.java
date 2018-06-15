package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public class OrderCardTransaction extends OrderTransaction{
    private static String DECREASE_MONEY_AMOUNT = "UPDATE pharmacy.credit_card SET money_amount = money_amount - ? WHERE login = ?";

    public OrderCardTransaction(String client, Basket basket, String address, BigDecimal sum, Date date){
        super(client, basket, address, sum, date);
        this.paymentType = Order.PaymentType.CARD;
    }

    @Override
    protected void makePayment(int orderId) throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(DECREASE_MONEY_AMOUNT);
        preparedStatement.setBigDecimal(1, sum);
        preparedStatement.setString(2, client);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while decreasing money amount.");
        }
    }
}
