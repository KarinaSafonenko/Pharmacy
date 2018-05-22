package by.epam.safonenko.pharmacy.repository;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.Card;
import by.epam.safonenko.pharmacy.exception.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreditCardRepository implements Repository<Card> {

    private static final String INSERT_CARD = "INSERT INTO pharmacy.credit_card (card_number, card_code) values(?, ?);";

    public void add(String cardNumber, String cardCode) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(INSERT_CARD)) {
            statement.setString(1,cardNumber);
            statement.setString(2,cardCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            releaseConnection(connection);
        }
    }

}
