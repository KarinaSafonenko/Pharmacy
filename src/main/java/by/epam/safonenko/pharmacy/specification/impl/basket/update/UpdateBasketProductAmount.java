package by.epam.safonenko.pharmacy.specification.impl.basket.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateBasketProductAmount implements UpdateSpecification {
    private final String REQUEST = "UPDATE pharmacy.client_basket SET amount = 10 WHERE pack_id = ? AND client = ?";
    private String login;
    private int amount;
    private int packId;

    public UpdateBasketProductAmount(String login, int amount, int packId){
        this.login = login;
        this.amount = amount;
        this.packId = packId;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, amount);
            current.setInt(2, packId);
            current.setString(3, login);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating client basket product amount.");
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
