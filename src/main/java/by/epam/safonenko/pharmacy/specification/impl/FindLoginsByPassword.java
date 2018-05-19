package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.util.UserParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindLoginsByPassword implements FindSpecification<String>{
    private static String REQUEST = "SELECT login FROM pharmacy.user WHERE user.password = SHA1(?)";
    private String login;

    public FindLoginsByPassword(String login){
        this.login = login;
    }

    @Override
    public List<String> execute(Statement statement) throws RepositoryException {
        List<String> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString(UserParameter.LOGIN.name().toLowerCase());
                result.add(login);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
