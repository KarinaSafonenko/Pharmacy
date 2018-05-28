package by.epam.safonenko.pharmacy.specification.impl.credit.find;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.impl.credit.CreditParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindOpenCreditIdsByLogin implements FindSpecification<String> {
    private static String REQUEST = "SELECT credit_id FROM pharmacy.credit WHERE status = 'OPEN' AND client = ?";
    private String login;

    public FindOpenCreditIdsByLogin(String login){
        this.login = login;
    }

    @Override
    public List<String> execute(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            List<String> result = new ArrayList<>();
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString(CreditParameter.CREDIT_ID.name().toLowerCase());
                result.add(login);
            }
            return result;
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
