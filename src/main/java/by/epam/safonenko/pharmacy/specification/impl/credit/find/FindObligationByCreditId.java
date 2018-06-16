package by.epam.safonenko.pharmacy.specification.impl.credit.find;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.specification.impl.credit.CreditParameter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindObligationByCreditId implements FindValueSpecification {
    public final String REQUEST = "SELECT obligation FROM pharmacy.credit WHERE credit_id = ?";
    private int creditId;

    public FindObligationByCreditId(int creditId){
        this.creditId = creditId;
    }

    @Override
    public String find(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setInt(1, creditId);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                BigDecimal amount = resultSet.getBigDecimal(CreditParameter.OBLIGATION.name().toLowerCase());
                return String.valueOf(amount);
            }else{
                throw new RepositoryException("Something went wrong while finding credit obligation.");
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
