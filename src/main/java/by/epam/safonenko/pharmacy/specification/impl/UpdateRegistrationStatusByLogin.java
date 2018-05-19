package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateRegistrationStatusByLogin implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.user SET user.registered = TRUE WHERE login = ?";

    private String login;

    public UpdateRegistrationStatusByLogin(String login){
        this.login = login;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setString(1, login);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating user registration status.");
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
