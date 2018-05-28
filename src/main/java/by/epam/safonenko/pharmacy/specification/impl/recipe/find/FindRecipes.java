package by.epam.safonenko.pharmacy.specification.impl.recipe.find;

import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.util.FormRecipe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindRecipes implements FindSpecification<Recipe>, FormRecipe {
    private static String REQUEST = "SELECT recipe.doctor, recipe.number, recipe.start_date, recipe.end_date, pack.pack_id, producer.name, producer.country, pack.quantity, pack.dosage, pack.price, pack.amount, pack.image_path, medicine.medicine_id, medicine.name, medicine.category, medicine.recipe_need, medicine.description\n" +
            "            FROM  pharmacy.recipe INNER JOIN (pharmacy.pack INNER JOIN pharmacy.medicine ON medicine.medicine_id = pack.medicine_id\n" +
            "                                  INNER JOIN pharmacy.producer ON producer.producer_id = pack.producer_id)\n" +
            "            ON recipe.pack_id = pack.pack_id";

    @Override
    public List<Recipe> execute(Statement statement) throws RepositoryException {
        List<Recipe> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            ResultSet resultSet = current.executeQuery();
            while (resultSet.next()) {
                result.add(formRecipe(resultSet));
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
