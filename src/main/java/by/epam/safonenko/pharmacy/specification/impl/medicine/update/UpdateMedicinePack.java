package by.epam.safonenko.pharmacy.specification.impl.medicine.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateMedicinePack implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.pack SET medicine_id = ?, producer_id = ?, quantity = ?, dosage = ?, price = ?, amount = ?, image_path = ? WHERE pack_id = ?";
    private int medicineId;
    private int producerId;
    private int quantity;
    private int dosage;
    private BigDecimal price;
    private int amount;
    private String imagePath;
    private int packId;

    public UpdateMedicinePack(int medicineId, int producerId, int quantity, int dosage, BigDecimal price, int amount, String imagePath, int packId) {
        this.medicineId = medicineId;
        this.producerId = producerId;
        this.quantity = quantity;
        this.dosage = dosage;
        this.price = price;
        this.amount = amount;
        this.imagePath = imagePath;
        this.packId = packId;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, medicineId);
            current.setInt(2, producerId);
            current.setInt(3, quantity);
            current.setInt(4, dosage);
            current.setBigDecimal(5, price);
            current.setInt(6, amount);
            current.setString(7, imagePath);
            current.setInt(8, packId);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating medicine pack.");
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
