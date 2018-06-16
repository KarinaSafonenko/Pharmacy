package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public class DecreaseCreditObligation extends PayCreditTransaction {
    private static final String DECREASE_OBLIGATION = "UPDATE pharmacy.credit SET latest_deposit = ? , obligation = obligation - ? WHERE credit_id = ?";

    public DecreaseCreditObligation(int creditId, String client, Date latestDeposit, BigDecimal sum){
        super(creditId, client, latestDeposit, sum);
    }

    @Override
    protected void updateCredit() throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(DECREASE_OBLIGATION);
        preparedStatement.setDate(1, new java.sql.Date(latestDeposit.getTime()));
        preparedStatement.setBigDecimal(2, sum);
        preparedStatement.setInt(3, creditId);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while decreasing credit obligation.");
        }
    }
}
