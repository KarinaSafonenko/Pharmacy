package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ClientBasketLogic;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShowBasketProducts implements Command {
    private static Logger logger = LogManager.getLogger(ShowBasketProducts.class);
    private ClientBasketLogic clientBasketLogic;

    private enum Parameter{
        CLIENT_BASKET, RECIPE_MAP, PRODUCTS, CART_SUM
    }

    public ShowBasketProducts(){
        clientBasketLogic = new ClientBasketLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        try {
           Basket basket = clientBasketLogic.findClientBasketContent(login);
           Map<Medicine, Boolean> recipeList = clientBasketLogic.formRecipeMap(login, basket);
           Set<Medicine> medicineSet = basket.getContent().keySet();
           List<Medicine> medicineList = new ArrayList<>(medicineSet);
           BigDecimal totalPrice = clientBasketLogic.countBasketPrice(basket);
           requestContent.addRequestAttribute(Parameter.CLIENT_BASKET.name().toLowerCase(), basket);
           requestContent.addRequestAttribute(Parameter.RECIPE_MAP.name().toLowerCase(), recipeList);
           requestContent.addRequestAttribute(Parameter.PRODUCTS.name().toLowerCase(), medicineList);
           requestContent.addRequestAttribute(Parameter.CART_SUM.name().toLowerCase(), totalPrice);
           return new Trigger(PagePath.BASKET_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
