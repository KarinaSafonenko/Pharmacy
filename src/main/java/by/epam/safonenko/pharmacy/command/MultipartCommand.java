package by.epam.safonenko.pharmacy.command;

import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.util.RequestContent;

import javax.servlet.http.Part;

public interface MultipartCommand {
    Trigger execute(RequestContent requestContent, Part part, String path, String context);

}
