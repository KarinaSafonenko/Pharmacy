package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.RecipeRepository;
import by.epam.safonenko.pharmacy.specification.impl.recipe.find.FindOpenRecipes;
import by.epam.safonenko.pharmacy.specification.impl.recipe.find.FindRecipesByLogin;
import by.epam.safonenko.pharmacy.specification.impl.recipe.find.FindRecipesByLoginAndPackId;
import by.epam.safonenko.pharmacy.specification.impl.recipe.update.UpdateRecipe;import by.epam.safonenko.pharmacy.validator.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class RecipeLogic implements Logic {
    private static final String DATE = "yyyy-MM-dd";
    private RecipeRepository recipeRepository;
    private UserLogic userLogic;

    public RecipeLogic(){
        recipeRepository = new RecipeRepository();
        userLogic = new UserLogic();
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

    public Recipe findRecipe(String login, String packId) throws LogicException {
        if (!Validator.validateId(packId) || !Validator.validateLogin(login)){
            throw  new LogicException("Incorrect login or pack id while finding recipe.");
        }
        try {
            List<Recipe> recipes = recipeRepository.find(new FindRecipesByLoginAndPackId(login, Integer.valueOf(packId)));
            return  recipes.isEmpty()? new Recipe(): recipes.get(0);
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

    public List<Recipe> findRecipes(String login) throws LogicException {
        if (!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while finding recipes.");
        }
        try {
            List<Recipe> recipes = recipeRepository.find(new FindRecipesByLogin(login));
            findInformation(recipes);
            return recipes;
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public List<Recipe> findOpenRecipes() throws LogicException {
        try {
            List<Recipe> recipes = recipeRepository.find(new FindOpenRecipes());
            findInformation(recipes);
            return recipes;
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    private void findInformation(List<Recipe> recipes) throws LogicException {
        for (Recipe recipe: recipes){
            User doctor = recipe.getDoctor();
            String doctorLogin = doctor.getLogin();
            User client = recipe.getClient();
            String clientLogin = client.getLogin();
            if (doctorLogin != null) {
                doctor = userLogic.findUser(doctorLogin);
                recipe.setDoctor(doctor);
            }
            if (clientLogin != null) {
                client = userLogic.findUser(clientLogin);
                recipe.setClient(client);
            }
        }
    }

    public boolean extendRecipe(String number, String endDate, String doctor, String recipeId) throws LogicException {
        if (!Validator.validateLogin(doctor) || !Validator.validateNumber(number)
                || !Validator.validateDate(endDate) || !Validator.validateId(recipeId)){
            return false;
        }
        Date today = getCurrentDate();
        int amount = Integer.parseInt(number);
        int id = Integer.parseInt(recipeId);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE);
        try {
            Date end = formatter.parse(endDate);
            if (end.before(today)){
                return false;
            }
            recipeRepository.update(new UpdateRecipe(amount, today, end, doctor, id));
            return true;
        } catch (ParseException|RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public List<Medicine> formMedicineList(List<Recipe> recipes){
        List<Medicine> medicineList = new ArrayList<>();
        for (Recipe recipe: recipes){
            Medicine medicine = recipe.getMedicine();
            medicineList.add(medicine);
        }
        return medicineList;
    }
}
