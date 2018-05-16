package by.epam.safonenko.pharmacy.repository;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class UserRepository implements Repository<User> {

    private static final String INSERT_USER = "INSERT INTO pharmacy.user(login, password, mail, second_name, first_name, patronymic, sex) VALUES(?,SHA1(?),?,?,?,?,?)";

    public void add(String name, String surname, String patronymic, String sex, String mail, String login, String password) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1,login);
            statement.setString(2,password);
            statement.setString(3,mail);
            statement.setString(4,surname);
            statement.setString(5,name);
            statement.setString(6,patronymic);
            statement.setString(7,sex);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public List<User> find(FindSpecification specification) throws RepositoryException {
        List<User> result;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(specification.getRequest())) {
            result = specification.execute(statement);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
        return result;
    }

    @Override
    public boolean check(FindSpecification specification) throws RepositoryException {
        List<User> userList= find(specification);
        return !userList.isEmpty();
    }

    @Override
    public void update(UpdateSpecification specification) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(specification.getRequest())) {
            specification.update(statement);
        }catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public String find(FindValueSpecification specification) throws RepositoryException {
        String result;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(specification.getRequest())) {
            result = specification.find(statement);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
        return result;
    }
}
