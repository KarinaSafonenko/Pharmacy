package by.epam.safonenko.pharmacy.repository;

import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.util.List;

public interface Repository<T> {
    List<T> find(FindSpecification specification) throws RepositoryException;
    boolean check(FindSpecification specification) throws RepositoryException;
    void update(UpdateSpecification specification) throws RepositoryException;
    String find(FindValueSpecification specification) throws RepositoryException;
    List<String> findValues(FindSpecification specification) throws RepositoryException;
    default void releaseConnection(ProxyConnection proxyConnection) {
        if (proxyConnection != null) {
            proxyConnection.close();
        }
    }

}

