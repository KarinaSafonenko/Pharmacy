package by.epam.safonenko.pharmacy.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class LocaleListener implements HttpSessionListener {
    private final String DEFAULT_LOCALE = "en";
    private final String LOCALE = "locale";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
