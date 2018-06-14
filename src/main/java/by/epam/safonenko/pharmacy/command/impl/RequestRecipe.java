package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.RecipeLogic;
import by.epam.safonenko.pharmacy.specification.impl.medicine.PackParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestRecipe implements Command {
    private static Logger logger = LogManager.getLogger(RequestRecipe.class);
    private RecipeLogic recipeLogic;

    public RequestRecipe(){
        recipeLogic = new RecipeLogic();
    }

    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String pack_id = requestContent.getRequestParameter(PackParameter.PACK_ID.name().toLowerCase());
        try {
            if (recipeLogic.checkRecipe(login, pack_id)){
                recipeLogic.deleteRecipe(login, pack_id);
            }
            recipeLogic.addRecipe(login, pack_id);
            return new Trigger(PagePath.MAIN_PATH, Trigger.TriggerType.REDIRECT); //на рецепты
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
