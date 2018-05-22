package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.util.RequestContent;

public class ChangeLocale implements Command {
    private static final String LOCALE = "locale";
    private final String LAST_PAGE = "lastPage";

    @Override
    public Trigger execute(RequestContent requestContent) {
        String locale = requestContent.getRequestParameter(LOCALE);
        requestContent.addSessionAttribute(LOCALE, locale);
        String lastPage = (String) requestContent.getSessionAttribute(LAST_PAGE);
        return new Trigger(lastPage, Trigger.TriggerType.REDIRECT);
    }
}
