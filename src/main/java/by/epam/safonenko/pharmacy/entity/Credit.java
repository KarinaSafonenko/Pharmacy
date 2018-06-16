package by.epam.safonenko.pharmacy.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Credit {
    private int creditId;
    private int orderId;
    private String client;
    private Date startDate;
    private Date latestDeposit;
    private BigDecimal obligation;
    private CreditStatus creditStatus;

    public enum CreditStatus{
        OPEN, CLOSED
    }

    public Credit(){}

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLatestDeposit() {
        return latestDeposit;
    }

    public void setLatestDeposit(Date latestDeposit) {
        this.latestDeposit = latestDeposit;
    }

    public BigDecimal getObligation() {
        return obligation;
    }

    public void setObligation(BigDecimal obligation) {
        this.obligation = obligation;
    }

    public CreditStatus getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(CreditStatus creditStatus) {
        this.creditStatus = creditStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credit)) return false;
        Credit credit = (Credit) o;
        return creditId == credit.creditId &&
                orderId == credit.orderId &&
                Objects.equals(client, credit.client) &&
                Objects.equals(startDate, credit.startDate) &&
                Objects.equals(latestDeposit, credit.latestDeposit) &&
                Objects.equals(obligation, credit.obligation) &&
                creditStatus == credit.creditStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(creditId, orderId, client, startDate, latestDeposit, obligation, creditStatus);
    }
}
