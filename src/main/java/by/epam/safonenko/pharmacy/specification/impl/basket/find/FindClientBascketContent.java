package by.epam.safonenko.pharmacy.specification.impl.basket.find;

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

public class FindClientBascketContent implements FindSpecification<Basket>, MedicineMapUtil {

    private static String REQUEST = "SELECT client_basket.amount, pack.pack_id, producer.name, producer.country, pack.quantity, pack.dosage, pack.price, pack.amount, pack.image_path, medicine.medicine_id, medicine.name, medicine.category, medicine.recipe_need, medicine.description\n" +
            "FROM  pharmacy.client_basket INNER JOIN (pharmacy.pack INNER JOIN pharmacy.medicine ON medicine.medicine_id = pack.medicine_id\n" +
            "INNER JOIN pharmacy.producer ON producer.producer_id = pack.producer_id) \n" +
            "ON pack.pack_id = client_basket.pack_id WHERE client_basket.client = ?";

    private static final String CLIENT_BASKET = "client_basket.";

    private String login;

    public FindClientBascketContent(String login){
        this.login = login;
    }

    @Override
    public List<Basket> execute(Statement statement) throws RepositoryException {
        List<Basket> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
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
        return  resultSet.getInt(CLIENT_BASKET + ClientBasketParameter.AMOUNT.name().toLowerCase());
    }
}
