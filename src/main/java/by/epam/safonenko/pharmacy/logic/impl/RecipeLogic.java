package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.RecipeRepository;
import by.epam.safonenko.pharmacy.specification.impl.recipe.find.FindRecipesByLoginAndPackId;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.util.List;

public class RecipeLogic implements Logic {
    private RecipeRepository recipeRepository;

    public RecipeLogic(){
        recipeRepository = new RecipeRepository();
    }

    public void addRecipe(String login, String packId) throws LogicException {
        if (!Validator.validateLogin(login) || !Validator.validateId(packId)){
            throw new LogicException("Incorrect login or pack id while adding recipe.");
        }
        try {
            recipeRepository.add(login, Integer.parseInt(packId));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public boolean checkRecipe(String login, String packId) throws LogicException {
        if (!Validator.validateLogin(login) || !Validator.validateId(packId)){
            throw new LogicException("Incorrect login or pack id while checking recipe.");
        }
        List<Recipe> recipeList;
        try {
            recipeList = recipeRepository.find(new FindRecipesByLoginAndPackId(login, Integer.parseInt(packId)));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
        return !recipeList.isEmpty();
    }

    public void deleteRecipe(String login, String packId) throws LogicException{
        if (!Validator.validateLogin(login) || !Validator.validateId(packId)){
            throw new LogicException("Incorrect login or pack id while deleting recipe.");
        }
        try {
            recipeRepository.delete(login, Integer.parseInt(packId));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }
}
