package by.epam.safonenko.pharmacy.specification.impl.recipe.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateRecipe implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.recipe SET number = ?, start_date = ?, end_date = ? WHERE client = ? AND pack_id = ?";
    private int packNumber;
    private Date startDate;
    private Date endDate;
    private String client;
    private int packId;

    public UpdateRecipe(int packNumber, Date startDate, Date endDate, String client, int packId) {
        this.packNumber = packNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.packId = packId;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, packNumber);
            current.setDate(2, startDate);
            current.setDate(3, endDate);
            current.setString(4, client);
            current.setInt(5, packId);
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
