package by.epam.safonenko.pharmacy.controller;

import by.epam.safonenko.pharmacy.command.MultipartCommand;
import by.epam.safonenko.pharmacy.command.MultipartCommandFactory;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10,
                maxFileSize=1024*1024*50,
                maxRequestSize=1024*1024*100)
public class FileUploadController extends HttpServlet {
    private static final String COMMAND = "command";
    private static final String PATH = "uploadPath";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(COMMAND);
        Optional<MultipartCommand> command = new MultipartCommandFactory().defineCommand(action);
        if (command.isPresent()){
            RequestContent requestContent = new RequestContent();
            requestContent.extractValues(request);
            Part part = request.getPart(MedicineParameter.IMAGE_PATH.name().toLowerCase());
            String parameter = request.getServletContext().getInitParameter(PATH);
            String path = getServletContext().getRealPath(parameter);
            Trigger trigger = command.get().execute(requestContent, part, path, parameter);
            requestContent.insertAttributes(request);
            if (trigger.getRoot().equals(Trigger.TriggerType.FORWARD)){
                request.getRequestDispatcher(trigger.getPagePath()).forward(request, response);
            }else{
                response.sendRedirect(trigger.getPagePath());
            }
        }
        else{
            response.sendRedirect(request.getContextPath() + PagePath.INDEX_PATH);
        }
    }
}