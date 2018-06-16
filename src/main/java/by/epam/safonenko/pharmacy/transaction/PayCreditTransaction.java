package by.epam.safonenko.pharmacy.transaction;

import by.epam.safonenko.pharmacy.exception.TransactionException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

public abstract class PayCreditTransaction extends Transaction implements DecreaseMoneyAmount{
    private static final String DECREASE_MONEY_AMOUNT = "UPDATE pharmacy.credit SET latest_deposit = ? , obligation = obligation - ? WHERE credit_id = ?";

    protected int creditId;
    protected String client;
    protected Date latestDeposit;
    protected BigDecimal sum;

    protected PayCreditTransaction(int creditId, String client, Date latestDeposit, BigDecimal sum){
        this.creditId = creditId;
        this.client = client;
        this.latestDeposit = latestDeposit;
        this.sum = sum;
    }

    protected abstract void updateCredit() throws SQLException, TransactionException;

    @Override
    protected void performTransaction() throws SQLException, TransactionException {
        updateCredit();
        decreaseMoneyAmount(preparedStatement, proxyConnection, client, sum);
    }
}
