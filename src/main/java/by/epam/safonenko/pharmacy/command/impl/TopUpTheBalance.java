package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.CreditCardLogic;
import by.epam.safonenko.pharmacy.specification.impl.card.CardParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class TopUpTheBalance implements Command {
    private static Logger logger = LogManager.getLogger(TopUpTheBalance.class);
    private CreditCardLogic creditCardLogic;

    public enum Parameter{
        WRONG_CARD_INFORMATION, WRONG_SUM
    }

    public TopUpTheBalance(){
        creditCardLogic = new CreditCardLogic();
    }


    @Override
    public Trigger execute(RequestContent requestContent) {
        String cardCode = requestContent.getRequestParameter(CardParameter.CARD_CODE.name().toLowerCase());
        String cardNumber = requestContent.getRequestParameter(CardParameter.CARD_NUMBER.name().toLowerCase());
        String moneyAmount = requestContent.getRequestParameter(CardParameter.MONEY_AMOUNT.name().toLowerCase());
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        try {
            Set<Parameter> incorrect = creditCardLogic.topUpTheBalance(login, cardNumber, cardCode, moneyAmount);
            if (incorrect.isEmpty()){
                return new Trigger(PagePath.SHOW_PROFILE, Trigger.TriggerType.REDIRECT);
            }else{
                for (Parameter parameter: incorrect){
                    requestContent.addRequestAttribute(parameter.name().toLowerCase(), true);
                }
                requestContent.addRequestAttribute(CardParameter.CARD_NUMBER.name().toLowerCase(), cardNumber);
                requestContent.addRequestAttribute(CardParameter.CARD_CODE.name().toLowerCase(), cardCode);
                requestContent.addRequestAttribute(CardParameter.MONEY_AMOUNT.name().toLowerCase(), moneyAmount);
                return new Trigger(PagePath.TOP_UP_BALANCE_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
