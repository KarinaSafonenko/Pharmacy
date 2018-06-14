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

public abstract class AbstractFindRecipes implements FindSpecification<Recipe>, FormRecipe{

    @Override
    public List<Recipe> execute(Statement statement) throws RepositoryException {
        List<Recipe> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            prepareStatement(current);
            ResultSet resultSet = current.executeQuery();
            while (resultSet.next()) {
                result.add(formRecipe(resultSet));
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    protected abstract void prepareStatement(PreparedStatement current) throws SQLException;

    @Override
    public abstract String getRequest();
}
