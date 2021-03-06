package by.epam.safonenko.pharmacy.entity;

import java.util.*;

public class Medicine {
    private int id;
    private String name;
    private ProductCategory category;
    private boolean recipeNeed;
    private String description;
    private List<Pack> medicinePacks;
    private String imagePath;

    public enum ProductCategory {
        VITAMINS, ALLERGY, ANTIBIOTICS, HEART, COLD, COSMETICS
    }

    public Medicine(){
        medicinePacks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public boolean getRecipeNeed() {
        return recipeNeed;
    }

    public void setRecipeNeed(boolean recipeNeed) {
        this.recipeNeed = recipeNeed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Pack> getMedicinePacks() {
        return medicinePacks;
    }

    public void setMedicinePacks(List<Pack> medicinePacks) {
        this.medicinePacks = medicinePacks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicine)) return false;
        Medicine medicine = (Medicine) o;
        return id == medicine.id &&
                recipeNeed == medicine.recipeNeed &&
                Objects.equals(name, medicine.name) &&
                category == medicine.category &&
                Objects.equals(description, medicine.description) &&
                Objects.equals(medicinePacks, medicine.medicinePacks) &&
                Objects.equals(imagePath, medicine.imagePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, category, recipeNeed, description, medicinePacks, imagePath);
    }
}
