package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.UserLogic;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class AddUser implements Command {
    private static Logger logger = LogManager.getLogger(AddUser.class);
    private UserLogic userLogic;

    public AddUser(){
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
        String userRole = requestContent.getRequestParameter(UserParameter.ROLE.name().toLowerCase());
        User.UserRole role = User.UserRole.valueOf(userRole.toUpperCase());

        requestContent.addRequestAttribute(UserParameter.NAME.name().toLowerCase(), name);
        requestContent.addRequestAttribute(UserParameter.SURNAME.name().toLowerCase(), surname);
        requestContent.addRequestAttribute(UserParameter.PATRONYMIC.name().toLowerCase(), patronymic);
        requestContent.addRequestAttribute(UserParameter.MAIL.name().toLowerCase(), mail);
        requestContent.addRequestAttribute(UserParameter.LOGIN.name().toLowerCase(), login);
        try {
            Set<Registration.Parameter> incorrect = userLogic.addRegisteredUser(name, surname, patronymic, sex, mail, login, password, repeatPassword, User.UserRole.CLIENT);
            return  incorrect.isEmpty() ? new Trigger(PagePath.SHOW_USERS, Trigger.TriggerType.REDIRECT) : new Trigger(PagePath.ADD_USER_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
