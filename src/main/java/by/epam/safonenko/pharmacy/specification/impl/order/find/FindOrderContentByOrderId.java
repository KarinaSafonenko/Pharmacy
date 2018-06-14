package by.epam.safonenko.pharmacy.specification.impl.order.find;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.util.MedicineMapUtil;
import by.epam.safonenko.pharmacy.specification.impl.basket.ClientBasketParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindOrderContentByOrderId implements FindSpecification<Basket>, MedicineMapUtil {
    private static String REQUEST = "SELECT order_content.amount, pack.pack_id, producer.name, producer.country, pack.quantity, pack.dosage, pack.price, pack.amount, medicine.medicine_id, medicine.name, medicine.image_path, medicine.category, medicine.recipe_need, medicine.description\n" +
            "            FROM  pharmacy.order_content INNER JOIN (pharmacy.pack INNER JOIN pharmacy.medicine ON medicine.medicine_id = pack.medicine_id\n" +
            "                                         INNER JOIN pharmacy.producer ON producer.producer_id = pack.producer_id)\n" +
            "            ON order_content.pack_id = pack.pack_id WHERE order_content.order_id = ?";

    private static final String ORDER_CONTENT = "order_content.";
    private int orderId;

    public FindOrderContentByOrderId(int orderId){
        this.orderId = orderId;
    }

    @Override
    public List<Basket> execute(Statement statement) throws RepositoryException {
        List<Basket> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setInt(1, orderId);
            ResultSet resultSet = current.executeQuery();
            Map<Medicine, Integer> medicineMap = formMedicineMap(resultSet);
            Basket basket = new Basket();
            basket.setContent(medicineMap);
            result.add(basket);
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }

    @Override
    public int findAmount(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(ORDER_CONTENT + ClientBasketParameter.AMOUNT.name().toLowerCase());
    }
}
