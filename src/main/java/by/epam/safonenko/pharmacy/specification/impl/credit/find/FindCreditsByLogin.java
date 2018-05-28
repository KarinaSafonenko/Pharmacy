package by.epam.safonenko.pharmacy.specification.impl.credit.find;

import by.epam.safonenko.pharmacy.entity.Credit;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.impl.credit.CreditParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindCreditsByLogin implements FindSpecification<Credit> {
    private static String REQUEST = "SELECT credit_id, order_id, start_date, end_date, latest_deposit, obligation, status FROM pharmacy.credit WHERE client = ?";
    private String login;

    public FindCreditsByLogin(String login){
        this.login = login;

    }

    @Override
    public List<Credit> execute(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            List<Credit> result = new ArrayList<>();
            while (resultSet.next()) {
                Credit credit = new Credit();
                credit.setCreditId(resultSet.getInt(CreditParameter.CREDIT_ID.name().toLowerCase()));
                credit.setCreditStatus(Credit.CreditStatus.valueOf(resultSet.getString(CreditParameter.STATUS.name().toLowerCase())));
                credit.setStartDate(resultSet.getDate(CreditParameter.START_DATE.name().toLowerCase()));
                credit.setEndDate(resultSet.getDate(CreditParameter.END_DATE.name().toLowerCase()));
                credit.setLatestDeposite(resultSet.getBigDecimal(CreditParameter.LATEST_DEPOSITE.name().toLowerCase()));
                credit.setObligation(resultSet.getBigDecimal(CreditParameter.OBLIGATION.name().toLowerCase()));
                credit.setOrderId(resultSet.getInt(CreditParameter.ORDER_ID.name().toLowerCase()));
                result.add(credit);
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
