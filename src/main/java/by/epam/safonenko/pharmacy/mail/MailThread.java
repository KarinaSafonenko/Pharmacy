package by.epam.safonenko.pharmacy.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailThread extends Thread {
    private static Logger logger = LogManager.getLogger(MailThread.class);

    private MimeMessage message;
    private String subject;
    private String to;
    private String text;
    private Properties properties;

    public MailThread(String to,
                      String subject, String text, Properties properties) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.properties = properties;
    }
    private void init() {
        String from = properties.getProperty(MailProperty.FROM.name().toLowerCase());

        Session mailSession = (new SessionCreator(properties)).createSession();
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(text);
        } catch (MessagingException e) {
            logger.catching(e);
        }
    }

    public void run() {
        init();
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            logger.catching(e);
        }
    }
}
