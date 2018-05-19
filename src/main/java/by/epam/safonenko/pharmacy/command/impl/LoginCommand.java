package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;

public class LoginCommand implements Command {
    private UserLogic userLogic;
    private static final String LOGIN_FAILED = "login_failed";

    public LoginCommand(){
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
                return new Trigger(PagePath.MAIN_PATH, Trigger.TriggerType.FORWARD);
            }else{
                requestContent.addRequestAttribute(LOGIN_FAILED, true);
                return new Trigger(PagePath.LOGIN_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            //e.printStackTrace();
        }
        return null;
    }
}
