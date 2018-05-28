package by.epam.safonenko.pharmacy.specification.impl.basket.find;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.specification.impl.basket.ClientBasketParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindBasketProductAmount implements FindValueSpecification{
    private static String REQUEST = "SELECT amount FROM pharmacy.client_basket WHERE pack_id = ? AND client = ?";
    private int packId;
    private String login;

    public FindBasketProductAmount(int packId, String login){
        this.packId = packId;
        this.login = login;

    }
    @Override
    public String find(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setInt(1, packId);
            current.setString(2, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                int amount = resultSet.getInt(ClientBasketParameter.AMOUNT.name().toLowerCase());
                return String.valueOf(amount);
            }else{
                throw new RepositoryException("Something went wrong while finding basket product amount.");
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
