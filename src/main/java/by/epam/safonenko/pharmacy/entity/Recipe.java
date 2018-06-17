package by.epam.safonenko.pharmacy.entity;

import java.util.Date;
import java.util.Objects;

public class Recipe {
    private int recipeId;
    private Medicine medicine;
    private User doctor;
    private User client;
    private int amount;
    private Date startDate;
    private Date endDate;

    public Recipe(){}

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return recipeId == recipe.recipeId &&
                amount == recipe.amount &&
                Objects.equals(medicine, recipe.medicine) &&
                Objects.equals(doctor, recipe.doctor) &&
                Objects.equals(client, recipe.client) &&
                Objects.equals(startDate, recipe.startDate) &&
                Objects.equals(endDate, recipe.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(recipeId, medicine, doctor, client, amount, startDate, endDate);
    }
}
