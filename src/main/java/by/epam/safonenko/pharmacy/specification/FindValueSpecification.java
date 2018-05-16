package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.exception.RepositoryException;

import java.sql.Statement;

public interface FindValueSpecification extends Specification {
    String find(Statement statement) throws RepositoryException;
}
