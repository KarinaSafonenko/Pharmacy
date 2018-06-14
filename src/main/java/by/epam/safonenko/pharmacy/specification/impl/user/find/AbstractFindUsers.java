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

public abstract class AbstractFindUsers implements FindSpecification<User>, FormUser {
    @Override
    public List<User> execute(Statement statement) throws RepositoryException {
        List<User> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            prepareStatement(current);
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

    protected abstract void prepareStatement(PreparedStatement current) throws SQLException;

    @Override
    public abstract String getRequest();
}
