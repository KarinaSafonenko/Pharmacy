package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;

public class Logout implements Command {

    @Override
    public Trigger execute(RequestContent requestContent) {
        requestContent.addSessionAttribute(UserParameter.LOGIN.name().toLowerCase(), null);
        requestContent.addSessionAttribute(UserParameter.NAME.name().toLowerCase(), null);
        requestContent.addSessionAttribute(UserParameter.SURNAME.name().toLowerCase(), null);
        requestContent.addSessionAttribute(UserParameter.ROLE.name().toLowerCase(), null);
        return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.REDIRECT);
    }
}
