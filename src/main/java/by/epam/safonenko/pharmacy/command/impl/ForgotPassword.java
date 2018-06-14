package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.UserLogic;
import by.epam.safonenko.pharmacy.mail.MailSender;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class ForgotPassword implements Command {
    private static Logger logger = LogManager.getLogger(Registration.class);
    private UserLogic userLogic;

    private enum Parameter{
        CHANGE_PASSWORD_SUBJECT, NO_SUCH_LOGIN
    }

    public ForgotPassword(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = requestContent.getRequestParameter(UserParameter.LOGIN.name().toLowerCase().trim());
        try {
            if (userLogic.findLogin(login) && userLogic.checkUserRegisteredStatus(login)){
                String userMail = userLogic.findMailByLogin(login);
                String code = userLogic.generateUserCode();
                try {
                    userLogic.setUserCode(login, code);
                } catch (LogicException e) {
                    return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
                }
                String lang = (String) requestContent.getSessionAttribute(SessionAttribute.LOCALE.name().toLowerCase());
                ResourceBundle resourceBundle = BundleUtil.getResourceBundle(lang);
                String subject = resourceBundle.getString(Parameter.CHANGE_PASSWORD_SUBJECT.name().toLowerCase());
                requestContent.addSessionAttribute(UserParameter.LOGIN.name().toLowerCase(), login);
                new MailSender().sendMail(userMail, subject, code);
                return new Trigger(PagePath.CHANGE_FORGOTTEN_PASSWORD_PATH, Trigger.TriggerType.REDIRECT);
            }else {
                requestContent.addRequestAttribute(Parameter.NO_SUCH_LOGIN.name().toLowerCase(), true);
                return new Trigger(PagePath.FORGOT_PASSWORD_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
