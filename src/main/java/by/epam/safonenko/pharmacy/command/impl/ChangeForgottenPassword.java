package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.specification.impl.FindParameterByLogin;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.parameter.UserParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ChangeForgottenPassword implements Command {
    private static Logger logger = LogManager.getLogger(ChangeForgottenPassword.class);
    private final String CONFIRMATION_FAILED = "confirmation_failed";
    private UserLogic userLogic;

    public ChangeForgottenPassword(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String code = requestContent.getRequestParameter(FindParameterByLogin.RequestType.CONFIRMATION_CODE.name().toLowerCase().trim());
        String password = requestContent.getRequestParameter(UserParameter.PASSWORD.name().toLowerCase());
        String repeatPassword = requestContent.getRequestParameter(UserParameter.REPEAT_PASSWORD.name().toLowerCase());
        try {
            requestContent.addRequestAttribute(FindParameterByLogin.RequestType.CONFIRMATION_CODE.name().toLowerCase(), code);
            if (userLogic.checkConfirmationCode(login, code)){
                Map<Registration.RegistrationMessage, UserParameter> incorrect = userLogic.comparePasswords(password, repeatPassword);
                for (Map.Entry<Registration.RegistrationMessage, UserParameter> entry : incorrect.entrySet()) {
                    requestContent.addRequestAttribute(entry.getKey().name().toLowerCase(), true);
                    requestContent.addRequestAttribute(entry.getValue().name().toLowerCase(), null);
                }
                if (incorrect.isEmpty()){
                    userLogic.setUserPassword(login, password);
                    return new Trigger(PagePath.LOGIN_PATH, Trigger.TriggerType.FORWARD);
                }else {
                    return new Trigger(PagePath.CHANGE_FORGOTTEN_PASSWORD_PATH, Trigger.TriggerType.FORWARD);
                }
            }else{
                requestContent.addRequestAttribute(CONFIRMATION_FAILED, true);
                return new Trigger(PagePath.CHANGE_FORGOTTEN_PASSWORD_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
