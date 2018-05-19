package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.exception.RepositoryException;

public interface Specification {
    String getRequest() throws RepositoryException;
}