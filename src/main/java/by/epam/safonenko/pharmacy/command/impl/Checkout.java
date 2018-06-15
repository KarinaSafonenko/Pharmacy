package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.command.util.ShowBasket;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ClientBasketLogic;
import by.epam.safonenko.pharmacy.logic.impl.CreditLogic;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

public class Checkout implements Command, ShowBasket {
    private static Logger logger = LogManager.getLogger(Checkout.class);
    private ClientBasketLogic clientBasketLogic;
    private CreditLogic creditLogic;

    public enum Parameter {
        PACK_AMOUNT_MESSAGE, RECIPE_MESSAGE, CREDIT_BASKET_MESSAGE, CART_SUM, PAYMENT
    }

    public Checkout() {
        clientBasketLogic = new ClientBasketLogic();
        creditLogic = new CreditLogic();
    }


    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String sum = requestContent.getRequestParameter(Parameter.CART_SUM.name().toLowerCase());
        String payment = requestContent.getRequestParameter(Parameter.PAYMENT.name().toLowerCase());
        Order.PaymentType paymentType = Order.PaymentType.valueOf(payment);
        try {
            Basket basket = clientBasketLogic.findClientBasketContent(login);
            Map<Medicine, Integer> basketContent = basket.getContent();
            if (basketContent != null) {
                Set<Parameter> incorrect = clientBasketLogic.checkBasketContent(login, basketContent);
                for (Parameter parameter : incorrect) {
                    requestContent.addRequestAttribute(parameter.name().toLowerCase(), true);
                }
                if (incorrect.isEmpty()) {
                    requestContent.addRequestAttribute(Parameter.CART_SUM.name().toLowerCase(), sum);
                    switch (paymentType) {
                        case CARD:
                            return new Trigger(PagePath.FORM_ORDER_PATH, Trigger.TriggerType.FORWARD);
                        case CREDIT:
                            if (creditLogic.isAnyOpenCredit(login)) {
                                requestContent.addRequestAttribute(Parameter.CREDIT_BASKET_MESSAGE.name().toLowerCase(), true);
                                addBasketParameters(requestContent, login, basket);
                                return new Trigger(PagePath.BASKET_PATH, Trigger.TriggerType.FORWARD);
                            }
                            return new Trigger(PagePath.FORM_CREDIT_PATH, Trigger.TriggerType.FORWARD);
                        default:
                            logger.log(Level.ERROR, "Incorrect payment type.");
                            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
                    }
                } else {
                    addBasketParameters(requestContent, login, basket);
                    return new Trigger(PagePath.BASKET_PATH, Trigger.TriggerType.FORWARD);
                }
            } else {
                return new Trigger(PagePath.SHOW_BASKET, Trigger.TriggerType.REDIRECT);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
