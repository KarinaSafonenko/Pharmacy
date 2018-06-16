package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public class OrderCardTransaction extends OrderTransaction implements DecreaseMoneyAmount{

    public OrderCardTransaction(String client, Basket basket, String address, BigDecimal sum, Date date){
        super(client, basket, address, sum, date);
        this.paymentType = Order.PaymentType.CARD;
    }

    @Override
    protected void makePayment(int orderId) throws SQLException, TransactionException {
        decreaseMoneyAmount(preparedStatement, proxyConnection, client, sum);
    }
}
