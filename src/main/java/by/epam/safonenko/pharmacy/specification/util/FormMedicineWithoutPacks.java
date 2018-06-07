package by.epam.safonenko.pharmacy.specification.util;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FormMedicineWithoutPacks {

    default Medicine formMedicine(ResultSet resultSet) throws SQLException {
            Medicine medicine = new Medicine();
            medicine.setImagePath(resultSet.getString(MedicineParameter.IMAGE_PATH.name().toLowerCase()));
            medicine.setId(resultSet.getInt(MedicineParameter.MEDICINE_ID.name().toLowerCase()));
            medicine.setName(resultSet.getString(MedicineParameter.NAME.name().toLowerCase()));
            medicine.setCategory(Medicine.ProductCategory.valueOf(resultSet.getString(MedicineParameter.CATEGORY.name().toLowerCase())));
            medicine.setRecipeNeed(resultSet.getBoolean(MedicineParameter.RECIPE_NEED.name().toLowerCase()));
            medicine.setDescription(resultSet.getString(MedicineParameter.DESCRIPTION.name().toLowerCase()));
        return medicine;
    }
}
