package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.util.FormMedicineWithoutPacks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFindMedicines implements FindSpecification<Medicine>, FormMedicineWithoutPacks {

    @Override
    public List<Medicine> execute(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            prepareStatement(current);
            ResultSet resultSet = current.executeQuery();
            List<Medicine> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(formMedicine(resultSet));
            }
            return result;
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    protected abstract void prepareStatement(PreparedStatement current) throws SQLException;

    @Override
    public abstract String getRequest();
}
