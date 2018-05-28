package by.epam.safonenko.pharmacy.specification.impl.credit.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateCreditStatus implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.credit SET credit.status = 'CLOSED' WHERE credit_id = ?";
    private int creditId;

    public UpdateCreditStatus(int creditId){
        this.creditId = creditId;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, creditId);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating credit status.");
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
