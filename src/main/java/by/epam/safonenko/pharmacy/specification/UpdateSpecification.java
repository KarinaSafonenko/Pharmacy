package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.exception.RepositoryException;

import java.sql.Statement;

public interface UpdateSpecification extends Specification {
    void update(Statement statement) throws RepositoryException;
}
