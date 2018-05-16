package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.util.UserParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindConfirmationCodeByLogin implements FindValueSpecification {
    private static final String REQUEST = "SELECT user.confirmation_code FROM pharmacy.user WHERE login = ?";
    private String login;

    public FindConfirmationCodeByLogin(String login){
        this.login = login;
    }

    @Override
    public String find(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(UserParameter.CONFIRMATION_CODE.toString().toLowerCase());
            }else{
                throw new RepositoryException("Confirmation code for user " + login + " hasn.t been found");
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
