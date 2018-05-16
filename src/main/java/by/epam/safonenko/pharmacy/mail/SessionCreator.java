package by.epam.safonenko.pharmacy.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionCreator {
    private String username;
    private String password;
    private Properties properties;

    public SessionCreator(Properties properties) {
        this.properties = properties;

        username = properties.getProperty(MailProperty.USERNAME.name().toLowerCase());
        password = properties.getProperty(MailProperty.PASSWORD.name().toLowerCase());

        properties.remove(MailProperty.FROM.name().toLowerCase());
        properties.remove(MailProperty.USERNAME.name().toLowerCase());
        properties.remove(MailProperty.PASSWORD.name().toLowerCase());
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
