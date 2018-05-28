package by.epam.safonenko.pharmacy.entity;

import java.util.Date;
import java.util.Objects;

public class Order {
    private int id;
    private String address;
    private PaymentType paymentType;
    private Date startDate;

    public enum PaymentType{
        CREDIT, CARD
    }

    public Order(){}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(address, order.address) &&
                paymentType == order.paymentType &&
                Objects.equals(startDate, order.startDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, address, paymentType, startDate);
    }
}
