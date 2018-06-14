package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ClientBasketLogic;
import by.epam.safonenko.pharmacy.specification.impl.medicine.PackParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveFromBasket implements Command {
    private static Logger logger = LogManager.getLogger(RemoveFromBasket.class);
    private ClientBasketLogic clientBasketLogic;

    public RemoveFromBasket(){
        clientBasketLogic = new ClientBasketLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String pack_id = requestContent.getRequestParameter(PackParameter.PACK_ID.name().toLowerCase());
        try {
            clientBasketLogic.removeFromBasket(login, pack_id);
            return new Trigger(PagePath.SHOW_BASKET, Trigger.TriggerType.REDIRECT);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
