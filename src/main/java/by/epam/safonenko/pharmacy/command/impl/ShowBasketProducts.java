package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.command.util.ShowBasket;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ClientBasketLogic;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowBasketProducts implements Command, ShowBasket {
    private static Logger logger = LogManager.getLogger(ShowBasketProducts.class);
    private ClientBasketLogic clientBasketLogic;

    public enum Parameter{
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
           addBasketParameters(requestContent, login, basket);
           return new Trigger(PagePath.BASKET_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
