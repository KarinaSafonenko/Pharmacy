package by.epam.safonenko.pharmacy.listener;

import by.epam.safonenko.pharmacy.util.PagePath;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class LocaleAndLastPageListener implements HttpSessionListener {
    private final String DEFAULT_LOCALE = "en_EN";
    private final String LOCALE = "locale";
    private final String LAST_PAGE = "lastPage";


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LAST_PAGE, PagePath.INDEX_PATH);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
