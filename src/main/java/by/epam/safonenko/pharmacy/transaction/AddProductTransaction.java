package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductTransaction extends Transaction {
    private static final String INSERT_MEDICINE = "INSERT INTO pharmacy.medicine (name, category, recipe_need, description, image_path) values(?, ?, ?, ?, ?)";
    private static final String INSERT_PACK = "INSERT INTO pharmacy.pack(medicine_id, producer_id, quantity, dosage, price, amount) values (?,?,?,?,?,?)";

    private String name;
    private Medicine.ProductCategory category;
    private String recipeNeed;
    private String description;
    private String imagePath;
    private int producerId;
    private int quantity;
    private int dosage;
    private BigDecimal price;
    private int amount;

    public AddProductTransaction(String name, Medicine.ProductCategory category, String recipeNeed,
                                 String description, String imagePath, int producerId, int quantity,
                                 int dosage, BigDecimal price, int amount) {
        this.name = name;
        this.category = category;
        this.recipeNeed = recipeNeed;
        this.description = description;
        this.imagePath = imagePath;
        this.producerId = producerId;
        this.quantity = quantity;
        this.dosage = dosage;
        this.price = price;
        this.amount = amount;
    }

    @Override
    protected void performTransaction() throws SQLException, TransactionException {
        int medicineId = addMedicine();
        addPack(medicineId);
    }

    private int addMedicine() throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(INSERT_MEDICINE);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, category.name());
        preparedStatement.setString(3, recipeNeed);
        preparedStatement.setString(4, description);
        preparedStatement.setString(5, imagePath);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while adding new order.");
        }
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            throw new TransactionException("Unexpected result while adding new order.");
        }
    }

    private void addPack(int medicineId) throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(INSERT_PACK);
        preparedStatement.setInt(1, medicineId);
        preparedStatement.setInt(2, producerId);
        preparedStatement.setInt(3, quantity);
        preparedStatement.setInt(4, dosage);
        preparedStatement.setBigDecimal(5, price);
        preparedStatement.setInt(6, amount);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while adding medicine pack.");
        }
    }
}
