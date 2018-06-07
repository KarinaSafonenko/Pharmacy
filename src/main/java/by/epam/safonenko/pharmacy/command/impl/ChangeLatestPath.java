package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.SessionAttribute;

public class ChangeLatestPath implements Command {
    private static final String PATH = "path";

    @Override
    public Trigger execute(RequestContent requestContent) {
        String path = requestContent.getRequestParameter(PATH);
        requestContent.addSessionAttribute(SessionAttribute.LATEST_PAGE.name().toLowerCase(), path);
        return new Trigger(path, Trigger.TriggerType.REDIRECT);
    }
}
