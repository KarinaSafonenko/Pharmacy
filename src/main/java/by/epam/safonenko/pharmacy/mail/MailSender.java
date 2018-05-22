package by.epam.safonenko.pharmacy.mail;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class MailSender {
    private static Logger logger = LogManager.getLogger(MailSender.class);
    private static final String MAIL_PROPERTY_PATH = "/property/mail.properties";

    public void sendMail(String to, String subject, String message){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL propertyURL = classLoader.getResource(MAIL_PROPERTY_PATH);
        if (propertyURL == null) {
            logger.log(Level.FATAL, "Mail property file hasn't been found");
            throw new RuntimeException();
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(propertyURL.toURI())));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        MailThread mailOperator = new MailThread(to, subject, message ,properties);
        mailOperator.start();
    }
}
