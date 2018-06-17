package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowDoctorRecipes extends AbstractShowRecipes {
    private static Logger logger = LogManager.getLogger(ShowDoctorRecipes.class);

    @Override
    protected void logException(LogicException e) {
        logger.catching(e);
    }

    @Override
    protected List<Recipe> findRecipes(String login) throws LogicException {
        return recipeLogic.findOpenRecipes();
    }

    @Override
    protected Trigger forward() {
        return new Trigger(PagePath.DOCTOR_RECIPES, Trigger.TriggerType.FORWARD);
    }
}
