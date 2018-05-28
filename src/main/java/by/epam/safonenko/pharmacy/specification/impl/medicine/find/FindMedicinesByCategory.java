package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.util.FormMedicineWithoutPacks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindMedicinesByCategory implements FindSpecification<Medicine>, FormMedicineWithoutPacks {
    private static String REQUEST = "SELECT medicine_id, name, category, recipe_need, description FROM pharmacy.medicine WHERE medicine.category = ?";
    private Medicine.ProductCategory category;

    public FindMedicinesByCategory(Medicine.ProductCategory category){
        this.category = category;

    }

    @Override
    public List<Medicine> execute(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, category.name());
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

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
