package by.epam.safonenko.pharmacy.specification.impl.recipe.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateRecipe implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.recipe SET number = ?, start_date = ?, end_date = ?, doctor = ? WHERE recipe_id = ?";
    private int packNumber;
    private Date startDate;
    private Date endDate;
    private String doctor;
    private int recipeId;

    public UpdateRecipe(int packNumber, Date startDate, Date endDate, String doctor, int recipeId) {
        this.packNumber = packNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recipeId = recipeId;
        this.doctor = doctor;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, packNumber);
            current.setDate(2, new java.sql.Date(startDate.getTime()));
            current.setDate(3, new java.sql.Date(endDate.getTime()));
            current.setString(4, doctor);
            current.setInt(5, recipeId);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating user recipe.");
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
