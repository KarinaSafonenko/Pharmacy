package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public class CloseCreditTransaction extends PayCreditTransaction {
    private static final String REQUEST = "UPDATE pharmacy.credit SET obligation = 0, status = 'CLOSED', latest_deposit = ? WHERE credit_id = ?";

    public CloseCreditTransaction(int creditId, String client, Date latestDeposit, BigDecimal sum){
        super(creditId, client, latestDeposit, sum);
    }

    @Override
    protected void updateCredit() throws SQLException, TransactionException {
        preparedStatement = proxyConnection.prepareStatement(REQUEST);
        preparedStatement.setDate(1, new java.sql.Date(latestDeposit.getTime()));
        preparedStatement.setInt(2, creditId);
        int changed = preparedStatement.executeUpdate();
        if (changed == 0) {
            throw new TransactionException("Something went wrong while closing credit transaction.");
        }
    }
}
