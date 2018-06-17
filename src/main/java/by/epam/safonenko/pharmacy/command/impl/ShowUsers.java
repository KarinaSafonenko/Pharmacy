package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.UserLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowUsers implements Command {
    private static Logger logger = LogManager.getLogger(ShowUsers.class);
    private UserLogic userLogic;

    private enum Parameter{
        USER_LIST
    }

    public ShowUsers(){
        userLogic = new UserLogic();
    }


    @Override
    public Trigger execute(RequestContent requestContent) {
        try {
            List<User> userList = userLogic.findUsers();
            requestContent.addRequestAttribute(Parameter.USER_LIST.name().toLowerCase(), userList);
            return new Trigger(PagePath.USER_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
