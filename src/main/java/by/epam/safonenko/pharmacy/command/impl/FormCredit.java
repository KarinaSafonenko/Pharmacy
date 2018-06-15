package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.CreditLogic;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormCredit implements Command {
    private static Logger logger = LogManager.getLogger(FormCredit.class);
    private CreditLogic creditLogic;

    private enum Parameter{
        INCORRECT
    }

    public FormCredit(){
        creditLogic = new CreditLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String street = requestContent.getRequestParameter(FormOrder.Parameter.STREET.name().toLowerCase());
        String house = requestContent.getRequestParameter(FormOrder.Parameter.HOUSE.name().toLowerCase());
        String flat = requestContent.getRequestParameter(FormOrder.Parameter.FLAT.name().toLowerCase());
        String sum = requestContent.getRequestParameter(Checkout.Parameter.CART_SUM.name().toLowerCase());
        try {
            if (creditLogic.formCredit(login, street, house, flat, sum)) {
                return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.REDIRECT); //к кредитам
            }else{
                requestContent.addRequestAttribute(Parameter.INCORRECT.name().toLowerCase(), true);
                requestContent.addRequestAttribute(FormOrder.Parameter.STREET.name().toLowerCase(), street);
                requestContent.addRequestAttribute(FormOrder.Parameter.HOUSE.name().toLowerCase(), house);
                requestContent.addRequestAttribute(FormOrder.Parameter.FLAT.name().toLowerCase(), flat);
                requestContent.addRequestAttribute(Checkout.Parameter.CART_SUM.name().toLowerCase(), sum);
                return new Trigger(PagePath.FORM_CREDIT_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
