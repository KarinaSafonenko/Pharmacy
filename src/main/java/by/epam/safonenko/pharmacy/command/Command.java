package by.epam.safonenko.pharmacy.command;

import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.util.RequestContent;

public interface Command {
    Trigger execute(RequestContent requestContent);
}
