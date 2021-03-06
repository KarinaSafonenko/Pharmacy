package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowUserRecipes extends AbstractShowRecipes {
    private static Logger logger = LogManager.getLogger(ShowUserRecipes.class);

    public ShowUserRecipes(){
        super();
    }

    @Override
    protected void logException(LogicException e) {
        logger.catching(e);
    }

    @Override
    protected List<Recipe> findRecipes(String login) throws LogicException {
        return recipeLogic.findRecipes(login);
    }

    @Override
    protected Trigger forward() {
        return new Trigger(PagePath.RECIPE_PATH, Trigger.TriggerType.FORWARD);
    }


}
