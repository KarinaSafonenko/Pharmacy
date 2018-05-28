package by.epam.safonenko.pharmacy.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class CreditCard {
    private int id;
    private String number;
    private int code;
    private BigDecimal moneyAmount;

    public CreditCard(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return id == that.id &&
                code == that.code &&
                Objects.equals(number, that.number) &&
                Objects.equals(moneyAmount, that.moneyAmount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, code, moneyAmount);
    }
}
