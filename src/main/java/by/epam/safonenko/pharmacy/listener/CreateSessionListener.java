package by.epam.safonenko.pharmacy.listener;

import by.epam.safonenko.pharmacy.util.SessionAttribute;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class CreateSessionListener implements HttpSessionListener {
    private final String DEFAULT_LOCALE = "ru_RU";


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(SessionAttribute.LOCALE.name().toLowerCase(), DEFAULT_LOCALE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
