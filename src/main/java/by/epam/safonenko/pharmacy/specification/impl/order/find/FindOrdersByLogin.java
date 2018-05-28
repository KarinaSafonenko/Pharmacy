package by.epam.safonenko.pharmacy.specification.impl.order.find;

import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.impl.order.OrderParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindOrdersByLogin implements FindSpecification<Order> {
    private static String REQUEST = "SELECT order_id, address, payment, date FROM pharmacy.order WHERE client = ?";
    private String login;

    public FindOrdersByLogin(String login){
        this.login = login;

    }

    @Override
    public List<Order> execute(Statement statement) throws RepositoryException {
        List<Order> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(OrderParameter.ORDER_ID.name().toLowerCase()));
                order.setAddress(resultSet.getString(OrderParameter.ADDRESS.name().toLowerCase()));
                order.setPaymentType(Order.PaymentType.valueOf(resultSet.getString(OrderParameter.PAYMENT.name().toLowerCase())));
                order.setStartDate(resultSet.getDate(OrderParameter.DATE.name().toLowerCase()));
                result.add(order);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
