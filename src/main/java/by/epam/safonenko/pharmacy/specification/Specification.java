package by.epam.safonenko.pharmacy.specification;

import java.util.List;

public interface Specification<T> {
    List<T> execute();
}
