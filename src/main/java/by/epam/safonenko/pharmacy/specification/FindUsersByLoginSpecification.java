package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.util.UserParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindUsersByLoginSpecification implements FindSpecification<User> {
    private static String REQUEST = "SELECT login, password, mail, second_name, first_name, patronymic, sex, role FROM pharmacy.user WHERE login = ?";
    private String login;

    public FindUsersByLoginSpecification(String login){
        this.login = login;
    }

    @Override
    public List<User> execute(Statement statement) throws RepositoryException {
        List<User> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(UserParameter.NAME.name().toLowerCase());
                String surname = resultSet.getString(UserParameter.SURNAME.name().toLowerCase());
                String patronymic = resultSet.getString(UserParameter.PATRONYMIC.name().toLowerCase());
                String sex = resultSet.getString(UserParameter.SEX.name().toLowerCase());
                String mail = resultSet.getString(UserParameter.MAIL.name().toLowerCase());
                String login = resultSet.getString(UserParameter.LOGIN.name().toLowerCase());
                String password = resultSet.getString(UserParameter.PASSWORD.name().toLowerCase());
                String role = resultSet.getString(UserParameter.ROLE.name().toLowerCase());
                result.add(new User(name, surname, patronymic, sex, mail, login, password, role));
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
