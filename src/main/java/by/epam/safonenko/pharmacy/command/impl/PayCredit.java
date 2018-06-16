package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.CreditCardLogic;
import by.epam.safonenko.pharmacy.logic.impl.CreditLogic;
import by.epam.safonenko.pharmacy.specification.impl.card.CardParameter;
import by.epam.safonenko.pharmacy.specification.impl.credit.CreditParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PayCredit implements Command {
    private static Logger logger = LogManager.getLogger(PayCredit.class);
    private CreditCardLogic creditCardLogic;
    private CreditLogic creditLogic;

    public PayCredit(){
        creditCardLogic = new CreditCardLogic();
        creditLogic = new CreditLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String creditId = requestContent.getRequestParameter(CreditParameter.CREDIT_ID.name().toLowerCase());
        String cardCode = requestContent.getRequestParameter(CardParameter.CARD_CODE.name().toLowerCase());
        String cardNumber = requestContent.getRequestParameter(CardParameter.CARD_NUMBER.name().toLowerCase());
        String obligation = requestContent.getRequestParameter(CreditParameter.OBLIGATION.name().toLowerCase());
        String money_amount = requestContent.getRequestParameter(CardParameter.MONEY_AMOUNT.name().toLowerCase());
        requestContent.addRequestAttribute(CreditParameter.CREDIT_ID.name().toLowerCase(), creditId);
        requestContent.addRequestAttribute(CardParameter.CARD_CODE.name().toLowerCase(), cardCode);
        requestContent.addRequestAttribute(CardParameter.CARD_NUMBER.name().toLowerCase(), cardNumber);
        requestContent.addRequestAttribute(CreditParameter.OBLIGATION.name().toLowerCase(), obligation);
        requestContent.addRequestAttribute(CardParameter.MONEY_AMOUNT.name().toLowerCase(), money_amount);
        try {
            if (creditCardLogic.checkCardParameters(login, cardCode, cardNumber)) {
                requestContent.addRequestAttribute(TopUpTheBalance.Parameter.WRONG_CARD_INFORMATION.name().toLowerCase(), true);
                return new Trigger(PagePath.PAY_CREDIT_PATH, Trigger.TriggerType.FORWARD);
            }
            if (!creditCardLogic.checkSumParameters(obligation, money_amount, login)) {
                requestContent.addRequestAttribute(TopUpTheBalance.Parameter.WRONG_SUM.name().toLowerCase(), true);
                return new Trigger(PagePath.PAY_CREDIT_PATH, Trigger.TriggerType.FORWARD);
            }
            creditLogic.payCredit(money_amount, obligation, creditId, login);
            return  new Trigger(PagePath.SHOW_USER_CREDITS, Trigger.TriggerType.REDIRECT);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
