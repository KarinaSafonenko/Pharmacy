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

public class Login implements Command {
    private static Logger logger = LogManager.getLogger(FormMainPage.class);
    private UserLogic userLogic;

    private enum Parameter{
        LOGIN_FAILED
    }

    public Login(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = requestContent.getRequestParameter(UserParameter.LOGIN.toString().toLowerCase()).trim();
        String password = requestContent.getRequestParameter(UserParameter.PASSWORD.toString().toLowerCase()).trim();
        try {
            if (userLogic.findLogin(login) && userLogic.checkUserRegisteredStatus(login) && userLogic.checkPassword(login, password)){
                User current = userLogic.findUser(login);
                requestContent.addSessionAttribute(UserParameter.LOGIN.name().toLowerCase(), login);
                requestContent.addSessionAttribute(UserParameter.NAME.name().toLowerCase(), current.getName());
                requestContent.addSessionAttribute(UserParameter.SURNAME.name().toLowerCase(), current.getSurname());
                requestContent.addSessionAttribute(UserParameter.ROLE.name().toLowerCase(), current.getRole());
                requestContent.addSessionAttribute(UserParameter.PATRONYMIC.name().toLowerCase(), current.getPatronymic());
                User.UserRole role = current.getRole();
                switch (role){
                    case CLIENT:
                        return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.REDIRECT);
                    case ADMIN:
                        return new Trigger(PagePath.SHOW_USERS, Trigger.TriggerType.REDIRECT);
                    case DOCTOR:
                        return new Trigger(PagePath.SHOW_DOCTOR_RECIPES, Trigger.TriggerType.REDIRECT);
                    case PHARMACIST:
                        return new Trigger(PagePath.SHOW_ALL_PRODUCTS, Trigger.TriggerType.REDIRECT);
                        default:
                            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
                }
            }else{
                requestContent.addRequestAttribute(Parameter.LOGIN_FAILED.name().toLowerCase(), true);
                return new Trigger(PagePath.LOGIN_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
