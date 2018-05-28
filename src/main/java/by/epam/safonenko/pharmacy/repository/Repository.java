package by.epam.safonenko.pharmacy.repository;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    default List<T> find(FindSpecification<T> specification) throws RepositoryException{
        List<T> result;
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
    default boolean check(FindSpecification<T> specification) throws RepositoryException{
        List<T> userList= find(specification);
        return !userList.isEmpty();
    }
    default void update(UpdateSpecification specification) throws RepositoryException{
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(specification.getRequest())) {
            specification.update(statement);
        }catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }
    default String find(FindValueSpecification specification) throws RepositoryException{
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
    default List<String> findValues(FindSpecification<String> specification) throws RepositoryException{
        List<String> result;
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
    default void releaseConnection(ProxyConnection proxyConnection) {
        if (proxyConnection != null) {
            proxyConnection.close();
        }
    }

}

