package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.CreditCardLogic;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.specification.impl.FindParameterByLogin;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.parameter.UserParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Confirmation implements Command {
    private static Logger logger = LogManager.getLogger(Confirmation.class);
    private final String CONFIRMATION_FAILED = "confirmation_failed";
    private UserLogic userLogic;
    private CreditCardLogic creditCardLogic;

    public Confirmation(){
        userLogic = new UserLogic();
        creditCardLogic = new CreditCardLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String code = requestContent.getRequestParameter(FindParameterByLogin.RequestType.CONFIRMATION_CODE.name().toLowerCase().trim());
        try {
            if (userLogic.checkConfirmationCode(login, code)){
                userLogic.setUserRegisteredStatus(login);
                String cardId = creditCardLogic.findCardId();
                creditCardLogic.bindUserToCard(login, cardId);
                return new Trigger(PagePath.MAIN_PATH, Trigger.TriggerType.REDIRECT);
            }else{
                requestContent.addRequestAttribute(CONFIRMATION_FAILED, true);
                return new Trigger(PagePath.CONFIRM_PATH, Trigger.TriggerType.REDIRECT);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }

}
