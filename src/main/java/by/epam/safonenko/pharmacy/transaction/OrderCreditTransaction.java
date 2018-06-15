package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public class OrderCreditTransaction extends OrderTransaction{
    private static final String INSERT_CREDIT = "INSERT INTO pharmacy.credit (order_id, client, start_date, obligation) values(?, ?, ?, ?)";

    public OrderCreditTransaction(String client, Basket basket, String address, BigDecimal sum, Date date){
        super(client, basket, address, sum, date);
        this.paymentType = Order.PaymentType.CREDIT;
    }

    @Override
    protected void makePayment(int orderId) throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(INSERT_CREDIT);
        preparedStatement.setInt(1, orderId);
        preparedStatement.setString(2, client);
        preparedStatement.setDate(3, new java.sql.Date(date.getTime()));
        preparedStatement.setBigDecimal(4, sum);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while forming client credit.");
        }
    }
}
