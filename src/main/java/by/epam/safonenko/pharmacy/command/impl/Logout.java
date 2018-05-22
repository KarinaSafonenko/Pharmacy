package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;

public class Logout implements Command {
    private final String LAST_PAGE = "lastPage";

    @Override
    public Trigger execute(RequestContent requestContent) {
        requestContent.addSessionAttribute(UserParameter.LOGIN.name().toLowerCase(), null);
        requestContent.addSessionAttribute(UserParameter.NAME.name().toLowerCase(), null);
        requestContent.addSessionAttribute(UserParameter.SURNAME.name().toLowerCase(), null);
        requestContent.addSessionAttribute(UserParameter.ROLE.name().toLowerCase(), null);
        requestContent.addSessionAttribute(LAST_PAGE, PagePath.INDEX_PATH);
        return new Trigger(PagePath.MAIN_PATH, Trigger.TriggerType.REDIRECT);
    }
}
