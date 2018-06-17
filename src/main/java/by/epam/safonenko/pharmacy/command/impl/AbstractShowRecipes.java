package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.RecipeLogic;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;

import java.util.List;

public abstract class AbstractShowRecipes implements Command {
    RecipeLogic recipeLogic;

    private enum Parameter{
        RECIPE_LIST, MEDICINE_LIST
    }

    AbstractShowRecipes(){
        recipeLogic = new RecipeLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        try {
            List<Recipe> recipeList = findRecipes(login);
            List<Medicine> medicineList = recipeLogic.formMedicineList(recipeList);
            requestContent.addRequestAttribute(Parameter.RECIPE_LIST.name().toLowerCase(), recipeList);
            requestContent.addRequestAttribute(Parameter.MEDICINE_LIST.name().toLowerCase(), medicineList);
        } catch (LogicException e) {
            logException(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
        return forward();
    }

    protected abstract void logException(LogicException e);

    protected abstract List<Recipe> findRecipes(String login) throws LogicException;

    protected abstract Trigger forward();
}
