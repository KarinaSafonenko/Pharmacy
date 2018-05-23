package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateBasketProductAmount implements UpdateSpecification {
    private final String REQUEST = "UPDATE pharmacy.client_basket SET amount = 10 WHERE pack_id = ? AND client = ?";
    private String login;
    private int amount;

    public UpdateBasketProductAmount(String login, int amount){
        this.login = login;
        this.amount = amount;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setInt(1, amount);
            current.setString(2, login);
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
