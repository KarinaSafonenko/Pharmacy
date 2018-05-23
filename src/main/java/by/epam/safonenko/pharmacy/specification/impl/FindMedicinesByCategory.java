package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.util.parameter.MedicineParameter;
import by.epam.safonenko.pharmacy.util.parameter.ProductCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindMedicinesByCategory implements FindSpecification<Medicine> {
    private static String REQUEST = "SELECT medicine_id, name, category, recipe_need, description FROM pharmacy.medicine WHERE medicine.category = ?";
    private ProductCategory category;

    public FindMedicinesByCategory(ProductCategory category){
        this.category = category;

    }

    @Override
    public List<Medicine> execute(Statement statement) throws RepositoryException {
        List<Medicine> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, category.name());
            ResultSet resultSet = current.executeQuery();
            while (resultSet.next()) {
                Medicine medicine = new Medicine();
                medicine.setId(resultSet.getInt(MedicineParameter.MEDICINE_ID.name().toLowerCase()));
                medicine.setName(resultSet.getString(MedicineParameter.NAME.name().toLowerCase()));
                medicine.setCategory(category);
                medicine.setRecipeNeed(resultSet.getBoolean(MedicineParameter.RECIPE_NEED.name().toLowerCase()));
                medicine.setDescription(resultSet.getString(MedicineParameter.DESCRIPTION.name().toLowerCase()));
                result.add(medicine);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
