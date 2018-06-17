package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.RecipeLogic;
import by.epam.safonenko.pharmacy.specification.impl.recipe.RecipeParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendRecipe implements Command {
    private static Logger logger = LogManager.getLogger(ExtendRecipe.class);
    private RecipeLogic recipeLogic;

    private enum Parameter{
        INCORRECT
    }

    public ExtendRecipe(){
        recipeLogic = new RecipeLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String recipeId = requestContent.getRequestParameter(RecipeParameter.RECIPE_ID.name().toLowerCase());
        String number = requestContent.getRequestParameter(RecipeParameter.NUMBER.name().toLowerCase());
        String endDate = requestContent.getRequestParameter(RecipeParameter.END_DATE.name().toLowerCase());
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());

        requestContent.addRequestAttribute(RecipeParameter.RECIPE_ID.name().toLowerCase(), recipeId);
        requestContent.addRequestAttribute(RecipeParameter.NUMBER.name().toLowerCase(), number);
        requestContent.addRequestAttribute(RecipeParameter.END_DATE.name().toLowerCase(), endDate);
        try {
            if (recipeLogic.extendRecipe(number, endDate, login, recipeId)) {
                return new Trigger(PagePath.SHOW_DOCTOR_RECIPES, Trigger.TriggerType.REDIRECT);
            }else{
                requestContent.addRequestAttribute(Parameter.INCORRECT.name().toLowerCase(), true);
                return new Trigger(PagePath.EXTEND_RECIPE, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
