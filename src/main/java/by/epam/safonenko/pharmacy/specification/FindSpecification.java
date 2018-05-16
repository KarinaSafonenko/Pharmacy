package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.exception.RepositoryException;

import java.sql.Statement;
import java.util.List;

public interface FindSpecification<T> extends Specification {
    List<T> execute(Statement statement) throws RepositoryException;
}
