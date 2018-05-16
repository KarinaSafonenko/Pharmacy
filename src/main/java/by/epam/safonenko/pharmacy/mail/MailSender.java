package by.epam.safonenko.pharmacy.mail;

import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class MailSender {
    private static final String MAIL_PROPERTY_PATH = "/property/mail.properties";

    public enum MailParameter{
        SUBJECT, MESSAGE
    }
    public void sendMail(String to, String subject, String message){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL propertyURL = classLoader.getResource(MAIL_PROPERTY_PATH);
        if (propertyURL == null) {
            //throw new LogicException("");
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(propertyURL.toURI())));
        } catch (URISyntaxException | IOException e) {
            // throw new LogicException("");
        }
//        String to = requestContent.getRequestParameter(UserParameter.MAIL.name().toLowerCase()).trim();
//        String subject = requestContent.getRequestParameter(MailParameter.SUBJECT.name().toLowerCase());
//        String message = requestContent.getRequestParameter(MailParameter.MESSAGE.name().toLowerCase());

        MailThread mailOperator = new MailThread(to, subject, message ,properties);
        mailOperator.start();
    }
}
