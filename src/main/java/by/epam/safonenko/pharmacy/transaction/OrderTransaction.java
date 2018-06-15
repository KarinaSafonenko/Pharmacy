package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class OrderTransaction extends Transaction {
    private static final String INSERT_ORDER = "INSERT INTO pharmacy.order (client, address, payment, date, sum) values(?, ?, ?, ?, ?)";
    private static final String INSERT_ORDER_CONTENT = "INSERT INTO pharmacy.order_content (order_id, pack_id, amount) values(?,?,?)";
    private static final String DECREASE_PACK_AMOUNT = "UPDATE pharmacy.pack SET amount = amount - ? WHERE pack_id = ?";
    private static final String INCREASE_ORDER_NUMBER = "UPDATE pharmacy.medicine SET number_of_orders = number_of_orders + ? WHERE medicine_id = ?";

    protected Basket basket;
    protected String client;
    protected String address;
    protected BigDecimal sum;
    protected Date date;
    protected Order.PaymentType paymentType;

    protected OrderTransaction(String client, Basket basket, String address, BigDecimal sum, Date date){
        this.client = client;
        this.basket = basket;
        this.address = address;
        this.sum = sum;
        this.date = date;
    }

    @Override
    protected void performTransaction() throws SQLException, TransactionException {
        checkBasket();
        proxyConnection.setAutoCommit(false);
        addOrder();
        int orderId = getGeneratedOrderId();
        updateRelatedTables(orderId);
        makePayment(orderId); //возможно либо снять кредит
        proxyConnection.commit();
    }

    protected abstract void makePayment(int orderId) throws SQLException, TransactionException;

    private void checkBasket() throws TransactionException {
        Map<Medicine, Integer> map = basket.getContent();
        if (map == null){
            throw new TransactionException("Empty medicine map while performing order transaction.");
        }
        for (Map.Entry<Medicine, Integer> entry : map.entrySet()) {
            Medicine medicine = entry.getKey();
            if (medicine == null || medicine.getMedicinePacks() == null || medicine.getMedicinePacks().isEmpty()) {
                throw new TransactionException("No packs for adding to order content.");
            }
        }
    }

    private void addOrder() throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(INSERT_ORDER);
        preparedStatement.setString(1, client);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, paymentType.name());
        preparedStatement.setDate(4, new java.sql.Date(date.getTime()));
        preparedStatement.setBigDecimal(5, sum);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while adding new order.");
        }
    }

    private int getGeneratedOrderId() throws SQLException, TransactionException {
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            throw new TransactionException("Unexpected result while adding new order.");
        }
    }

    private void updateRelatedTables(int orderId) throws SQLException, TransactionException {
        Map<Medicine, Integer> map = basket.getContent();
        for (Map.Entry<Medicine, Integer> entry : map.entrySet()) {
            Medicine medicine = entry.getKey();
            int medicineId = medicine.getId();
            int amount = entry.getValue();
            List<Pack> packs = medicine.getMedicinePacks();
            Pack pack = packs.get(0);
            int packId = pack.getPackId();
            insertOrderContent(orderId, packId, amount);
            decreasePackAmount(amount, packId);
            increaseOrderNumber(amount, medicineId);
        }
    }

    private void insertOrderContent(int orderId, int packId, int amount) throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(INSERT_ORDER_CONTENT);
        preparedStatement.setInt(1, orderId);
        preparedStatement.setInt(2, packId);
        preparedStatement.setInt(3, amount);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while adding order content.");
        }
    }

    private void decreasePackAmount(int amount, int packId) throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(DECREASE_PACK_AMOUNT);
        preparedStatement.setInt(1, amount);
        preparedStatement.setInt(2, packId);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while decreasing pack amount.");
        }
    }

    private void increaseOrderNumber(int amount, int medicineId) throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(INCREASE_ORDER_NUMBER);
        preparedStatement.setInt(1, amount);
        preparedStatement.setInt(2, medicineId);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while increasing number of medicine order.");
        }
    }
}
