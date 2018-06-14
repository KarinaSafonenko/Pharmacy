package by.epam.safonenko.pharmacy.specification.impl.card.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateMoneyAmount implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.credit_card SET credit_card.money_amount = ? WHERE login = ?";
    private BigDecimal moneyAmount;
    private String login;

    public UpdateMoneyAmount(BigDecimal moneyAmount, String login){
        this.moneyAmount = moneyAmount;
        this.login = login;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setBigDecimal(1, moneyAmount);
            current.setString(2, login);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating money amount.");
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
