package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateCardUser implements UpdateSpecification {
    private final String REQUEST = "UPDATE pharmacy.credit_card SET credit_card.login = ? WHERE credit_card.card_id = ?";
    private String login;
    private String cardId;

    public UpdateCardUser(String login, String cardId){
        this.login = login;
        this.cardId = cardId;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setString(1, login);
            current.setInt(2, Integer.parseInt(cardId));
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while binding user to credit card");
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
