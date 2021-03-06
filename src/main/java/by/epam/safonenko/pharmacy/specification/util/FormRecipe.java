package by.epam.safonenko.pharmacy.specification.util;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.specification.impl.recipe.RecipeParameter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FormRecipe extends FormSinglePackMedicine {
    default Recipe formRecipe(ResultSet resultSet) throws SQLException {
        Recipe recipe = new Recipe();
        User doctor = new User();
        User client = new User();
        doctor.setLogin(resultSet.getString(RecipeParameter.DOCTOR.name().toLowerCase()));
        client.setLogin(resultSet.getString(RecipeParameter.CLIENT.name().toLowerCase()));
        Medicine medicine = formMedicineWithSinglePack(resultSet);
        recipe.setRecipeId(resultSet.getInt(RecipeParameter.RECIPE_ID.name().toLowerCase()));
        recipe.setAmount(resultSet.getInt(RecipeParameter.NUMBER.name().toLowerCase()));
        recipe.setStartDate(resultSet.getDate(RecipeParameter.START_DATE.name().toLowerCase()));
        recipe.setEndDate(resultSet.getDate(RecipeParameter.END_DATE.name().toLowerCase()));
        recipe.setDoctor(doctor);
        recipe.setClient(client);
        recipe.setMedicine(medicine);
        return recipe;
    }
}
