package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.CreditCardLogic;
import by.epam.safonenko.pharmacy.logic.impl.CreditLogic;
import by.epam.safonenko.pharmacy.specification.impl.credit.CreditParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class PreparePayCredit implements Command {
    private static Logger logger = LogManager.getLogger(PreparePayCredit.class);
    private CreditLogic creditLogic;

    public PreparePayCredit(){
        creditLogic = new CreditLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String creditId = requestContent.getRequestParameter(CreditParameter.CREDIT_ID.name().toLowerCase());
        requestContent.addRequestAttribute(CreditParameter.CREDIT_ID.name().toLowerCase(), creditId);
        try {
            BigDecimal obligation = creditLogic.findObligation(creditId);
            requestContent.addRequestAttribute(CreditParameter.OBLIGATION.name().toLowerCase(), obligation);
            return new Trigger(PagePath.PAY_CREDIT_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
