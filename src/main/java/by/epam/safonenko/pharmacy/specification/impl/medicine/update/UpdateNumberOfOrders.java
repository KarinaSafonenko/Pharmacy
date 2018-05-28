package by.epam.safonenko.pharmacy.specification.impl.medicine.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateNumberOfOrders implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.medicine SET number_of_orders = ? WHERE medicine_id = ?";
    private int medicineId;
    private int numberOfOrders;

    public UpdateNumberOfOrders(int medicineId, int numberOfOrders) {
        this.medicineId = medicineId;
        this.numberOfOrders = numberOfOrders;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, numberOfOrders);
            current.setInt(2, medicineId);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while medicine number of orders.");
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
