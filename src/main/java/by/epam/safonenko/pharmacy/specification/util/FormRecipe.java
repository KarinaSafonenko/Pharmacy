package by.epam.safonenko.pharmacy.specification.util;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.specification.impl.recipe.RecipeParameter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FormRecipe extends FormSinglePackMedicine {
    default Recipe formRecipe(ResultSet resultSet) throws SQLException {
        Recipe recipe = new Recipe();
        Medicine medicine = formMedicineWithSinglePack(resultSet);
        recipe.setAmount(resultSet.getInt(RecipeParameter.NUMBER.name().toLowerCase()));
        recipe.setDoctor(resultSet.getString(RecipeParameter.DOCTOR.name().toLowerCase()));
        recipe.setStartDate(resultSet.getDate(RecipeParameter.START_DATE.name().toLowerCase()));
        recipe.setEndDate(resultSet.getDate(RecipeParameter.END_DATE.name().toLowerCase()));
        recipe.setMedicine(medicine);
        return recipe;
    }
}
