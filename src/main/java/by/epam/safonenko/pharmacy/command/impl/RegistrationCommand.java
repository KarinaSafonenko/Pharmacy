package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.mail.MailSender;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;


public class RegistrationCommand implements Command {
    private static Logger logger = LogManager.getLogger(RegistrationCommand.class);
    public static final String MESSAGE_PATH = "/property/message.properties";
    private static final String ROLE = "CLIENT";
    private UserLogic userLogic;

    public enum RegistrationMessage {
        WRONG_NAME, WRONG_SURNAME, WRONG_PATRONYMIC,
        WRONG_EMAIL, WRONG_LOGIN, WRONG_PASSWORD,
        DUPLICATE_LOGIN, DIFFERENT_PASSWORDS
    }

    public RegistrationCommand(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String name = requestContent.getRequestParameter(UserParameter.NAME.name().toLowerCase()).trim();
        String surname = requestContent.getRequestParameter(UserParameter.SURNAME.name().toLowerCase()).trim();
        String patronymic = requestContent.getRequestParameter(UserParameter.PATRONYMIC.name().toLowerCase()).trim();
        String sex = requestContent.getRequestParameter(UserParameter.SEX.name().toLowerCase()).trim();
        String mail = requestContent.getRequestParameter(UserParameter.MAIL.name().toLowerCase()).trim();
        String login = requestContent.getRequestParameter(UserParameter.LOGIN.name().toLowerCase()).trim();
        String password = requestContent.getRequestParameter(UserParameter.PASSWORD.name().toLowerCase());
        String repeatPassword = requestContent.getRequestParameter(UserParameter.REPEAT_PASSWORD.name().toLowerCase());

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL propertyURL = classLoader.getResource(MESSAGE_PATH);
        if (propertyURL == null) {
           // logger.log(Level.FATAL, "Database property file hasn't been found");
            //throw new RuntimeException();
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(propertyURL.toURI())));
        } catch (URISyntaxException | IOException e) {
            //logger.log(Level.FATAL, e);
            //throw new RuntimeException(e);
        }

        String subject = properties.getProperty(MailSender.MailParameter.SUBJECT.name().toLowerCase());


        Map<RegistrationMessage, UserParameter> incorrect = null;
        try {
            incorrect = userLogic.addUser(name, surname, patronymic, sex, mail, login, password, repeatPassword);
        } catch (LogicException e) {
            logger.catching(e);
            //e.printStackTrace();
        }

        requestContent.addRequestAttribute(UserParameter.NAME.name().toLowerCase(), name);
        requestContent.addRequestAttribute(UserParameter.SURNAME.name().toLowerCase(), surname);
        requestContent.addRequestAttribute(UserParameter.PATRONYMIC.name().toLowerCase(), patronymic);
        requestContent.addRequestAttribute(UserParameter.SEX.name().toLowerCase(), sex);
        requestContent.addRequestAttribute(UserParameter.MAIL.name().toLowerCase(), mail);
        requestContent.addRequestAttribute(UserParameter.LOGIN.name().toLowerCase(), login);

        for (Map.Entry<RegistrationMessage, UserParameter> entry : incorrect.entrySet()) {
            requestContent.addRequestAttribute(entry.getKey().name().toLowerCase(), true);
            requestContent.addRequestAttribute(entry.getValue().name().toLowerCase(), null);
        }

        if (incorrect.isEmpty()){
            requestContent.addRequestAttribute(MailSender.MailParameter.SUBJECT.name().toLowerCase(), subject);
            String code = userLogic.generateUserCode();
            try {
                userLogic.setUserCode(login, code);
            } catch (LogicException e) {
                //e.printStackTrace();
            }
            requestContent.addRequestAttribute(MailSender.MailParameter.MESSAGE.name().toLowerCase(), code);
            new MailSender().sendMail(mail, subject, code);
            requestContent.addSessionAttribute(UserParameter.LOGIN.name().toLowerCase(), login);
            requestContent.addSessionAttribute(UserParameter.NAME.name().toLowerCase(), name);
            requestContent.addSessionAttribute(UserParameter.SURNAME.name().toLowerCase(), surname);
            requestContent.addSessionAttribute(UserParameter.ROLE.name().toLowerCase(), ROLE);
            return new Trigger(PagePath.CONFIRM_PATH, Trigger.TriggerType.REDIRECT);

        }else{
            return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.FORWARD);
        }
    }

}
