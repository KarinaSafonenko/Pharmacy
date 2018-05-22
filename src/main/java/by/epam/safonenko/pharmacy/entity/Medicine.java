package by.epam.safonenko.pharmacy.entity;

import by.epam.safonenko.pharmacy.util.ProductCategory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Medicine {
    private String name;
    private ProductCategory category;
    private boolean recipeNeed;
    private String description;
    private Set<Pack> medicinePacks;

    public Medicine(){
        medicinePacks = new HashSet<>();
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

    public boolean isRecipeNeed() {
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

    public Set<Pack> getMedicinePacks() {
        return medicinePacks;
    }

    public void setMedicinePacks(Set<Pack> medicinePacks) {
        this.medicinePacks = medicinePacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicine)) return false;
        Medicine medicine = (Medicine) o;
        return recipeNeed == medicine.recipeNeed &&
                Objects.equals(name, medicine.name) &&
                category == medicine.category &&
                Objects.equals(description, medicine.description) &&
                Objects.equals(medicinePacks, medicine.medicinePacks);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, category, recipeNeed, description, medicinePacks);
    }
}
