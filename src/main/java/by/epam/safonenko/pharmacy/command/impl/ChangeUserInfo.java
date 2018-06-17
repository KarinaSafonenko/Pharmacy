package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.UserLogic;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class ChangeUserInfo implements Command {
    private static Logger logger = LogManager.getLogger(ChangeUserInfo.class);
    private UserLogic userLogic;

    public ChangeUserInfo(){
        userLogic = new UserLogic();
    }
    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String name = requestContent.getRequestParameter(UserParameter.NAME.name().toLowerCase()).trim();
        String surname = requestContent.getRequestParameter(UserParameter.SURNAME.name().toLowerCase()).trim();
        String patronymic = requestContent.getRequestParameter(UserParameter.PATRONYMIC.name().toLowerCase()).trim();
        String sex = requestContent.getRequestParameter(UserParameter.SEX.name().toLowerCase()).trim();
        try {
            Set<Registration.Parameter> incorrect = userLogic.checkInitials(name, surname, patronymic);
            requestContent.addRequestAttribute(UserParameter.SEX.name().toLowerCase(), sex);

            if (incorrect.isEmpty()) {
                userLogic.updateUserInfo(login, name, surname, patronymic, sex);
                requestContent.addSessionAttribute(UserParameter.NAME.name().toLowerCase(), name);
                requestContent.addSessionAttribute(UserParameter.SURNAME.name().toLowerCase(), surname);
                requestContent.addSessionAttribute(UserParameter.PATRONYMIC.name().toLowerCase(), patronymic);
            }
            return new Trigger(PagePath.PROFILE_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
