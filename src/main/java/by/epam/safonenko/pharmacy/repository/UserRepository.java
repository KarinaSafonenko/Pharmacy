package by.epam.safonenko.pharmacy.repository;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserRepository implements Repository<User> {

    public enum UserRole{
        ADMIN, CLIENT, PHARMACIST, DOCTOR
    }

    private static final String INSERT_USER = "INSERT INTO pharmacy.user(login, password, mail, surname, name, patronymic, sex, role) VALUES(?,SHA1(?),?,?,?,?,?,?)";

    public void add(String name, String surname, String patronymic, String sex, String mail, String login, String password, UserRole role) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1,login);
            statement.setString(2,password);
            statement.setString(3,mail);
            statement.setString(4,surname);
            statement.setString(5,name);
            statement.setString(6,patronymic);
            statement.setString(7,sex);
            statement.setString(8,role.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
