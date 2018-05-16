package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;

public class LoginCommand implements Command {
    private UserLogic userLogic;

    public LoginCommand(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = requestContent.getRequestParameter(UserParameter.LOGIN.toString().toLowerCase()).trim();
        String password = requestContent.getRequestParameter(UserParameter.PASSWORD.toString().toLowerCase()).trim();
        return null;
    }
}
