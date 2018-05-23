package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.util.parameter.CardParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindCardIdWithoutUser implements FindValueSpecification {
    private static String REQUEST = "SELECT credit_card.card_id FROM pharmacy.credit_card WHERE login IS NULL;";

    public FindCardIdWithoutUser(){
        }

    @Override
    public String find(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(CardParameter.CARD_ID.name().toLowerCase());
            }else{
                throw new RepositoryException("Something went wrong while finding card id without user.");
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
