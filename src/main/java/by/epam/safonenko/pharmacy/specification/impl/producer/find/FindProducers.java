package by.epam.safonenko.pharmacy.specification.impl.producer.find;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.impl.medicine.ProducerParameter;
import by.epam.safonenko.pharmacy.specification.impl.order.OrderParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindProducers implements FindSpecification<Producer> {
    private final String REQUEST = "SELECT producer.producer_id, producer.name, producer.country FROM pharmacy.producer";
    @Override
    public List<Producer> execute(Statement statement) throws RepositoryException {
        List<Producer> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            ResultSet resultSet = current.executeQuery();
            while (resultSet.next()) {
                Producer producer = new Producer();
                producer.setId(resultSet.getInt(ProducerParameter.PRODUCER_ID.name().toLowerCase()));
                producer.setCountry(resultSet.getString(ProducerParameter.COUNTRY.name().toLowerCase()));
                producer.setName(resultSet.getString(ProducerParameter.NAME.name().toLowerCase()));
                result.add(producer);
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
