package by.epam.safonenko.pharmacy.repository;

import by.epam.safonenko.pharmacy.specification.Specification;

import java.util.List;

public interface Repository<T> {
    List<T> find(Specification specification);
    boolean check(Specification specification);
}
