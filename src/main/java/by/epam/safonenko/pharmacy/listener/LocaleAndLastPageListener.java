package by.epam.safonenko.pharmacy.listener;

import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.SessionAttribute;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class LocaleAndLastPageListener implements HttpSessionListener {
    private final String DEFAULT_LOCALE = "en_EN";


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(SessionAttribute.LOCALE.name().toLowerCase(), DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.LATEST_PAGE.name().toLowerCase(), PagePath.INDEX_PATH);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
