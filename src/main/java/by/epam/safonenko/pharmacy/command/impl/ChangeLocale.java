package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.SessionAttribute;

public class ChangeLocale implements Command {
    private final String CONTROLLER_SERVLET = "/ControllerServlet";

    private enum Parameter{
        URL, QUERY
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String locale = requestContent.getRequestParameter(SessionAttribute.LOCALE.name().toLowerCase());
        requestContent.addSessionAttribute(SessionAttribute.LOCALE.name().toLowerCase(), locale);
        String path = requestContent.getRequestParameter(Parameter.URL.name().toLowerCase());
        if (path.isEmpty()) {
            return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.REDIRECT);
        }
        String query = requestContent.getRequestParameter(Parameter.QUERY.name().toLowerCase());
        StringBuilder redirectPath = new StringBuilder();
        if (!query.isEmpty()) {
            redirectPath.append(CONTROLLER_SERVLET);
            redirectPath.append("?");
            redirectPath.append(query);
        } else {
            redirectPath.append(path);
        }
        return new Trigger(redirectPath.toString(), Trigger.TriggerType.REDIRECT);
    }
}
