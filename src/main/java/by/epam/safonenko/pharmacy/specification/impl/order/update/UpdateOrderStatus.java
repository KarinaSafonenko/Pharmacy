package by.epam.safonenko.pharmacy.specification.impl.order.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateOrderStatus implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.order SET status = 'CLOSED' WHERE order_id = ?";
    private int orderId;

    public UpdateOrderStatus(int orderId){
        this.orderId = orderId;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, orderId);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating order status.");
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
