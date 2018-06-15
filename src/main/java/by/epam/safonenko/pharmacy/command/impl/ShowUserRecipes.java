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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowUserRecipes implements Command {
    private static Logger logger = LogManager.getLogger(ShowUserRecipes.class);
    private RecipeLogic recipeLogic;

    private enum Parameter{
        RECIPE_LIST, MEDICINE_LIST
    }

    public ShowUserRecipes(){
        recipeLogic = new RecipeLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        try {
            List<Recipe> recipeList = recipeLogic.findRecipes(login);
            List<Medicine> medicineList = recipeLogic.formMedicineList(recipeList);
            requestContent.addRequestAttribute(Parameter.RECIPE_LIST.name().toLowerCase(), recipeList);
            requestContent.addRequestAttribute(Parameter.MEDICINE_LIST.name().toLowerCase(), medicineList);
            return new Trigger(PagePath.RECIPE_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
