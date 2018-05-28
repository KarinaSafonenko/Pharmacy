package by.epam.safonenko.pharmacy.specification.impl.medicine.update;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateMedicine implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.medicine SET name = ?, category = ?, recipe_need = ?, description = ? WHERE medicine_id = ?";
    private String name;
    private Medicine.ProductCategory category;
    private boolean recipeNeed;
    private String description;
    private int medicineId;

    public UpdateMedicine(String name, Medicine.ProductCategory category, boolean recipeNeed, String description, int medicineId) {
        this.name = name;
        this.category = category;
        this.recipeNeed = recipeNeed;
        this.description = description;
        this.medicineId = medicineId;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setString(1, name);
            current.setString(2, category.name());
            current.setBoolean(3, recipeNeed);
            current.setString(4, description);
            current.setInt(5, medicineId);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating money amount.");
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
