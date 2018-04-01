package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.util.UserParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserByLogin implements Specification<User> {
    private static Logger logger = LogManager.getLogger(FindUserByLogin.class);
    private static String FIND_USER = "SELECT login, password, mail, second_name, first_name, patronymic, sex, role FROM pharmacy.user WHERE login = ?";
    private String login;

    public FindUserByLogin(String login){
        this.login = login;
    }

    @Override
    public List<User> execute() {
        List<User> result = new ArrayList<>();
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(FIND_USER)) {
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(UserParameter.FIRST_NAME.toString().toLowerCase());
                String surname = resultSet.getString(UserParameter.SECOND_NAME.toString().toLowerCase());
                String patronymic = resultSet.getString(UserParameter.PATRONYMIC.toString().toLowerCase());
                String sex = resultSet.getString(UserParameter.SEX.toString().toLowerCase());
                String mail = resultSet.getString(UserParameter.MAIL.toString().toLowerCase());
                String login = resultSet.getString(UserParameter.LOGIN.toString().toLowerCase());
                String password = resultSet.getString(UserParameter.PASSWORD.toString().toLowerCase());
                String role = resultSet.getString(UserParameter.ROLE.toString().toLowerCase());
                result.add(new User(name, surname, patronymic, sex, mail, login, password, role));
            }
        } catch (SQLException e) {
            logger.catching(Level.WARN, e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }
}
