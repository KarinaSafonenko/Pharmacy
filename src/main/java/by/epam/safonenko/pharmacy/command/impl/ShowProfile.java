package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.CreditCardLogic;
import by.epam.safonenko.pharmacy.logic.impl.UserLogic;
import by.epam.safonenko.pharmacy.specification.impl.card.CardParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowProfile implements Command {
    private static Logger logger = LogManager.getLogger(ShowProfile.class);
    private UserLogic userLogic;
    private CreditCardLogic creditCardLogic;

    public ShowProfile(){
        userLogic = new UserLogic();
        creditCardLogic = new CreditCardLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String lang = (String) requestContent.getSessionAttribute(SessionAttribute.LOCALE.name().toLowerCase());
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(lang);
        try {
            User user = userLogic.findUser(login);
            String role = resourceBundle.getString(user.getRole().name().toLowerCase());
            BigDecimal money = creditCardLogic.findMoneyAmount(login);
            requestContent.addRequestAttribute(UserParameter.SEX.name().toLowerCase(), user.getSex().name());
            requestContent.addRequestAttribute(UserParameter.ROLE.name().toLowerCase(), role);
            requestContent.addRequestAttribute(CardParameter.MONEY_AMOUNT.name().toLowerCase(), money);
            return new Trigger(PagePath.PROFILE_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
