package by.epam.safonenko.pharmacy.specification.impl.user.find;

import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.util.FormUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindUsers implements FindSpecification<User>, FormUser {
    private static String REQUEST = "SELECT login, password, mail, surname, name, patronymic, sex, role FROM pharmacy.user";
    @Override
    public List<User> execute(Statement statement) throws RepositoryException {
        List<User> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            ResultSet resultSet = current.executeQuery();
            while (resultSet.next()) {
                User user = formUser(resultSet);
                result.add(user);
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
