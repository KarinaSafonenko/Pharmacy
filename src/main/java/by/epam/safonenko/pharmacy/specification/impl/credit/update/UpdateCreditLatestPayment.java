package by.epam.safonenko.pharmacy.specification.impl.credit.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateCreditLatestPayment implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.credit SET latest_deposit = ?, obligation = ? WHERE credit_id = ?";
    private int creditId;
    private Date latestDeposite;
    private BigDecimal obligation;

    public UpdateCreditLatestPayment(int creditId, Date latestDeposite, BigDecimal obligation){
        this.creditId = creditId;
        this.latestDeposite = latestDeposite;
        this.obligation = obligation;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setDate(1, latestDeposite);
            current.setBigDecimal(2, obligation);
            current.setInt(3, creditId);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating credit latest payment.");
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
