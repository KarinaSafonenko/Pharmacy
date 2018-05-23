package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.SessionAttribute;

public class ChangeLocale implements Command {

    @Override
    public Trigger execute(RequestContent requestContent) {
        String locale = requestContent.getRequestParameter(SessionAttribute.LOCALE.name().toLowerCase());
        requestContent.addSessionAttribute(SessionAttribute.LOCALE.name().toLowerCase(), locale);
        String lastPage = (String) requestContent.getSessionAttribute(SessionAttribute.LATEST_PAGE.name().toLowerCase());
        return new Trigger(lastPage, Trigger.TriggerType.REDIRECT);
    }
}
