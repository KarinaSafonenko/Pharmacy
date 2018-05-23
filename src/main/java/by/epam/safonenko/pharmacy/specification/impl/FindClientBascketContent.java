package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.util.parameter.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindClientBascketContent implements FindSpecification<Basket> {

    private static String REQUEST = "SELECT client_basket.amount, pack.pack_id, producer.name, producer.country, pack.quantity, pack.dosage, pack.price, pack.amount, pack.image_path, medicine.medicine_id, medicine.name, medicine.category, medicine.recipe_need, medicine.description\n" +
            "FROM  pharmacy.client_basket INNER JOIN (pharmacy.pack INNER JOIN pharmacy.medicine ON medicine.medicine_id = pack.medicine_id\n" +
            "INNER JOIN pharmacy.producer ON producer.producer_id = pack.producer_id) \n" +
            "ON pack.pack_id = client_basket.pack_id WHERE client_basket.client = ?";

    private static final String PRODUCER = "producer.";
    private static final String MEDICINE = "medicine.";
    private static final String PACK = "pack.";
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
            Map<Medicine, Integer> medicineMap= new HashMap<>();
            Basket basket = new Basket();
            while (resultSet.next()) {
                Medicine medicine = new Medicine();
                Pack pack = new Pack();
                Producer producer = new Producer();
                List<Pack> packList = new ArrayList<>();
                producer.setName(resultSet.getString(PRODUCER + ProducerParameter.NAME.name().toLowerCase()));
                producer.setCountry(resultSet.getString(ProducerParameter.COUNTRY.name().toLowerCase()));
                pack.setProducer(producer);
                pack.setPackId(resultSet.getInt(PackParameter.PACK_ID.name().toLowerCase()));
                pack.setPrice(resultSet.getBigDecimal(PackParameter.PRICE.name().toLowerCase()));
                pack.setQuantity(resultSet.getInt(PackParameter.QUANTITY.name().toLowerCase()));
                pack.setImagePath(resultSet.getString(PackParameter.IMAGE_PATH.name().toLowerCase()));
                pack.setDosage(resultSet.getInt(PackParameter.DOSAGE.name().toLowerCase()));
                pack.setAmount(resultSet.getInt(PACK + PackParameter.AMOUNT.name().toLowerCase()));
                packList.add(pack);
                medicine.setId(resultSet.getInt(MedicineParameter.MEDICINE_ID.name().toLowerCase()));
                medicine.setName(resultSet.getString(MEDICINE + MedicineParameter.NAME.name().toLowerCase()));
                medicine.setCategory(ProductCategory.valueOf(resultSet.getString(MedicineParameter.CATEGORY.name().toLowerCase())));
                medicine.setRecipeNeed(resultSet.getBoolean(MedicineParameter.RECIPE_NEED.name().toLowerCase()));
                medicine.setDescription(resultSet.getString(MedicineParameter.DESCRIPTION.name().toLowerCase()));
                medicine.setMedicinePacks(packList);
                int amount = resultSet.getInt(CLIENT_BASKET + ClientBasketParameter.AMOUNT.name().toLowerCase());
                medicineMap.put(medicine, amount);
            }
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
}
