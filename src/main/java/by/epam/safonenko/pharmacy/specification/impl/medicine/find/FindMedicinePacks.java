package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.impl.medicine.PackParameter;
import by.epam.safonenko.pharmacy.specification.impl.medicine.ProducerParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindMedicinePacks implements FindSpecification<Pack> {
    private static String REQUEST = "SELECT pack.pack_id, producer.producer_id, producer.name, producer.country, pack.quantity, pack.dosage, pack.price, pack.amount FROM pharmacy.pack \n" +
            "INNER JOIN pharmacy.producer ON producer.producer_id = pack.producer_id\n" +
            "WHERE pack.medicine_id = ?";
    private int id;

    public FindMedicinePacks(int id){
        this.id = id;
    }

    @Override
    public List<Pack> execute(Statement statement) throws RepositoryException {
        List<Pack> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setInt(1, id);
            ResultSet resultSet = current.executeQuery();
            while (resultSet.next()) {
                Pack pack = new Pack();
                Producer producer = new Producer();
                producer.setId(resultSet.getInt(ProducerParameter.PRODUCER_ID.name().toLowerCase()));
                producer.setName(resultSet.getString(ProducerParameter.NAME.name().toLowerCase()));
                producer.setCountry(resultSet.getString(ProducerParameter.COUNTRY.name().toLowerCase()));
                pack.setProducer(producer);
                pack.setPackId(resultSet.getInt(PackParameter.PACK_ID.name().toLowerCase()));
                pack.setPrice(resultSet.getBigDecimal(PackParameter.PRICE.name().toLowerCase()));
                pack.setQuantity(resultSet.getInt(PackParameter.QUANTITY.name().toLowerCase()));
                pack.setDosage(resultSet.getInt(PackParameter.DOSAGE.name().toLowerCase()));
                pack.setAmount(resultSet.getInt(PackParameter.AMOUNT.name().toLowerCase()));
                result.add(pack);
            }
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
