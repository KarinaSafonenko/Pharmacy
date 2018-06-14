package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.User;
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

import java.util.Map;
import java.util.ResourceBundle;


public class Registration implements Command {
    private static Logger logger = LogManager.getLogger(Registration.class);
    private UserLogic userLogic;

    public enum Parameter {
        WRONG_NAME, WRONG_SURNAME, WRONG_PATRONYMIC,
        WRONG_EMAIL, WRONG_LOGIN, WRONG_PASSWORD,
        DUPLICATE_LOGIN, DIFFERENT_PASSWORDS, REGISTRATION_SUBJECT
    }

    public Registration(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String name = requestContent.getRequestParameter(UserParameter.NAME.name().toLowerCase()).trim();
        String surname = requestContent.getRequestParameter(UserParameter.SURNAME.name().toLowerCase()).trim();
        String patronymic = requestContent.getRequestParameter(UserParameter.PATRONYMIC.name().toLowerCase()).trim();
        String sex = requestContent.getRequestParameter(UserParameter.SEX.name().toLowerCase()).trim();
        String mail = requestContent.getRequestParameter(UserParameter.MAIL.name().toLowerCase()).trim();
        String login = requestContent.getRequestParameter(UserParameter.LOGIN.name().toLowerCase()).trim();
        String password = requestContent.getRequestParameter(UserParameter.PASSWORD.name().toLowerCase());
        String repeatPassword = requestContent.getRequestParameter(UserParameter.REPEAT_PASSWORD.name().toLowerCase());

        Map<Parameter, UserParameter> incorrect;
        try {
            incorrect = userLogic.addUser(name, surname, patronymic, sex, mail, login, password, repeatPassword, User.UserRole.CLIENT);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }

        requestContent.addRequestAttribute(UserParameter.NAME.name().toLowerCase(), name);
        requestContent.addRequestAttribute(UserParameter.SURNAME.name().toLowerCase(), surname);
        requestContent.addRequestAttribute(UserParameter.PATRONYMIC.name().toLowerCase(), patronymic);
        requestContent.addRequestAttribute(UserParameter.SEX.name().toLowerCase(), sex);
        requestContent.addRequestAttribute(UserParameter.MAIL.name().toLowerCase(), mail);
        requestContent.addRequestAttribute(UserParameter.LOGIN.name().toLowerCase(), login);

        for (Map.Entry<Parameter, UserParameter> entry : incorrect.entrySet()) {
            requestContent.addRequestAttribute(entry.getKey().name().toLowerCase(), true);
            requestContent.addRequestAttribute(entry.getValue().name().toLowerCase(), null);
        }

        if (incorrect.isEmpty()){
            String code = userLogic.generateUserCode();
            try {
                userLogic.setUserCode(login, code);
            } catch (LogicException e) {
                return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
            }
            String lang = (String) requestContent.getSessionAttribute(SessionAttribute.LOCALE.name().toLowerCase());
            ResourceBundle resourceBundle = BundleUtil.getResourceBundle(lang);
            String subject = resourceBundle.getString(Parameter.REGISTRATION_SUBJECT.name().toLowerCase());
            new MailSender().sendMail(mail, subject, code);
            requestContent.addSessionAttribute(UserParameter.LOGIN.name().toLowerCase(), login);
            return new Trigger(PagePath.CONFIRM_PATH, Trigger.TriggerType.REDIRECT);
        }else{
            return new Trigger(PagePath.REGISTRATION_PATH, Trigger.TriggerType.FORWARD);
        }
    }

}
