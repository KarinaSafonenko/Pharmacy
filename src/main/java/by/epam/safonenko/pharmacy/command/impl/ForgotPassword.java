package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.mail.MailSender;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class ForgotPassword implements Command {
    private static Logger logger = LogManager.getLogger(Registration.class);
    private static final String MESSAGE_PATH = "/property/message.properties";
    private static final String CHANGE_PASSWORD_SUBJECT = "changePasswordSubject";
    private static final String NO_SUCH_LOGIN = "no_such_login";
    private UserLogic userLogic;

    public ForgotPassword(){
        userLogic = new UserLogic();
    }
    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = requestContent.getRequestParameter(UserParameter.LOGIN.name().toLowerCase().trim());
        try {
            if (userLogic.findLogin(login) && userLogic.checkUserRegisteredStatus(login)){
                String userMail = userLogic.findMailByLogin(login);
                String code = userLogic.generateUserCode();
                try {
                    userLogic.setUserCode(login, code);
                } catch (LogicException e) {
                    return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
                }
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                URL propertyURL = classLoader.getResource(MESSAGE_PATH);
                if (propertyURL == null) {
                    logger.log(Level.FATAL, "Message property file hasn't been found");
                    throw new RuntimeException();
                }

                Properties properties = new Properties();
                try {
                    properties.load(new FileInputStream(new File(propertyURL.toURI())));
                } catch (URISyntaxException | IOException e) {
                    logger.catching(e);
                    throw new RuntimeException(e);
                }
                String subject = properties.getProperty(CHANGE_PASSWORD_SUBJECT);
                requestContent.addSessionAttribute(UserParameter.LOGIN.name().toLowerCase(), login);
                new MailSender().sendMail(userMail, subject, code);
                return new Trigger(PagePath.CHANGE_FORGOTTEN_PASSWORD_PATH, Trigger.TriggerType.REDIRECT);
            }else {
                requestContent.addRequestAttribute(NO_SUCH_LOGIN, true);
                return new Trigger(PagePath.FORGOT_PASSWORD_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
