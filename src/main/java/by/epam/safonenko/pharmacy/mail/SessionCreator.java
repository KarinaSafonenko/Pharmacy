package by.epam.safonenko.pharmacy.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionCreator {
    private String username;
    private String password;
    private Properties properties;

    protected SessionCreator(Properties properties) {
        this.properties = properties;

        username = properties.getProperty(MailParameter.USERNAME.name().toLowerCase());
        password = properties.getProperty(MailParameter.PASSWORD.name().toLowerCase());

        properties.remove(MailParameter.FROM.name().toLowerCase());
        properties.remove(MailParameter.USERNAME.name().toLowerCase());
        properties.remove(MailParameter.PASSWORD.name().toLowerCase());
    }
    public Session createSession() {
        return Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
}
