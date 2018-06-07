package by.epam.safonenko.pharmacy.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Pack {
    private int packId;
    private Producer producer;
    private int quantity;
    private int dosage;
    private BigDecimal price;
    private int amount;

    public Pack(){}

    public int getPackId() {
        return packId;
    }

    public void setPackId(int packId) {
        this.packId = packId;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pack)) return false;
        Pack pack = (Pack) o;
        return packId == pack.packId &&
                quantity == pack.quantity &&
                dosage == pack.dosage &&
                amount == pack.amount &&
                Objects.equals(producer, pack.producer) &&
                Objects.equals(price, pack.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(packId, producer, quantity, dosage, price, amount);
    }
}
