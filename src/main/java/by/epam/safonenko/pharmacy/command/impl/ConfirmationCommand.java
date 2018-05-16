package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;

public class ConfirmationCommand implements Command {
    private UserLogic userLogic;

    public ConfirmationCommand(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String code = requestContent.getRequestParameter(UserParameter.CONFIRMATION_CODE.name().toLowerCase().trim());
//        try {
//            if (userLogic.checkConfirmationCode(login, code)){
//                return new Trigger(PagePath.MAIN_PATH, Trigger.TriggerType.REDIRECT);
//            }else{
//                requestContent.addRequestAttribute();
//            }
//        } catch (LogicException e) {
//            //e.printStackTrace();
//        }

        return null;
    }

}
