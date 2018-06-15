package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.OrderLogic;
import by.epam.safonenko.pharmacy.specification.impl.card.CardParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class FormOrder implements Command {
    private static Logger logger = LogManager.getLogger(FormOrder.class);
    private OrderLogic orderLogic;

    public enum Parameter{
        STREET, HOUSE, FLAT, NOT_ENOUGH_MONEY, WRONG_CARD_INFORMATION, INCORRECT_ADDRESS
    }

    public FormOrder(){
        orderLogic = new OrderLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String street = requestContent.getRequestParameter(Parameter.STREET.name().toLowerCase());
        String house = requestContent.getRequestParameter(Parameter.HOUSE.name().toLowerCase());
        String flat = requestContent.getRequestParameter(Parameter.FLAT.name().toLowerCase());
        String sum = requestContent.getRequestParameter(Checkout.Parameter.CART_SUM.name().toLowerCase());
        String cardCode = requestContent.getRequestParameter(CardParameter.CARD_CODE.name().toLowerCase());
        String cardNumber = requestContent.getRequestParameter(CardParameter.CARD_NUMBER.name().toLowerCase());
        try {
            Set<Parameter> incorrect = orderLogic.formOrder(login, street, house, flat, sum, cardCode, cardNumber);
            for (Parameter current: incorrect){
                requestContent.addRequestAttribute(current.name().toLowerCase(), true);
            }
            if (incorrect.isEmpty()){
                return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.REDIRECT); //к заказам
            }else{
                requestContent.addRequestAttribute(Parameter.STREET.name().toLowerCase(), street);
                requestContent.addRequestAttribute(Parameter.HOUSE.name().toLowerCase(), house);
                requestContent.addRequestAttribute(Parameter.FLAT.name().toLowerCase(), flat);
                requestContent.addRequestAttribute(Checkout.Parameter.CART_SUM.name().toLowerCase(), sum);
                requestContent.addRequestAttribute(CardParameter.CARD_CODE.name().toLowerCase(), cardCode);
                requestContent.addRequestAttribute(CardParameter.CARD_NUMBER.name().toLowerCase(), cardNumber);
                return new Trigger(PagePath.FORM_ORDER_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
