package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.specification.impl.recipe.RecipeParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;

public class PrepareExtendRecipe implements Command {
    @Override
    public Trigger execute(RequestContent requestContent) {
        String recipeId = requestContent.getRequestParameter(RecipeParameter.RECIPE_ID.name().toLowerCase());
        requestContent.addRequestAttribute(RecipeParameter.RECIPE_ID.name().toLowerCase(), recipeId);
        return new Trigger(PagePath.EXTEND_RECIPE, Trigger.TriggerType.FORWARD);
    }
}
