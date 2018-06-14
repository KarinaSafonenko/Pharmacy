package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import by.epam.safonenko.pharmacy.entity.Medicine;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindMedicinesByCategory extends AbstractFindMedicines {
    private static String REQUEST = "SELECT medicine_id, name, category, recipe_need, image_path, description FROM pharmacy.medicine WHERE medicine.category = ?";
    private Medicine.ProductCategory category;

    public FindMedicinesByCategory(Medicine.ProductCategory category){
        this.category = category;

    }

    @Override
    protected void prepareStatement(PreparedStatement current) throws SQLException {
        current.setString(1, category.name());
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
