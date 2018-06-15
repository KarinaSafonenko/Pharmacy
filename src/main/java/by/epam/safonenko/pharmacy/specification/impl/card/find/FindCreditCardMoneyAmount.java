package by.epam.safonenko.pharmacy.specification.impl.card.find;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.specification.impl.card.CardParameter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindCreditCardMoneyAmount implements FindValueSpecification {
    private static String REQUEST = "SELECT credit_card.money_amount FROM pharmacy.credit_card WHERE login = ?";
    private String login;

    public FindCreditCardMoneyAmount(String login){
        this.login = login;

    }
    @Override
    public String find(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                BigDecimal amount = resultSet.getBigDecimal(CardParameter.MONEY_AMOUNT.name().toLowerCase());
                return String.valueOf(amount);
            }else{
                throw new RepositoryException("Something went wrong while finding credit card money amount.");
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
