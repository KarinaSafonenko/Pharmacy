package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ClientBasketLogic;
import by.epam.safonenko.pharmacy.specification.impl.basket.ClientBasketParameter;
import by.epam.safonenko.pharmacy.specification.impl.medicine.PackParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddToCart implements Command {
    private static Logger logger = LogManager.getLogger(AddToCart.class);
    private ClientBasketLogic clientBasketLogic;

    public AddToCart(){
        clientBasketLogic = new ClientBasketLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        if (login == null){
            return new Trigger(PagePath.LOGIN_PATH, Trigger.TriggerType.REDIRECT);
        }else{
            String packId = requestContent.getRequestParameter(ClientBasketParameter.PACK_ID.name().toLowerCase());
            String quantity = requestContent.getRequestParameter(PackParameter.QUANTITY.name().toLowerCase());
            try {
                if (clientBasketLogic.checkPackIdInClientBasket(packId, login)){
                    int resultAmount = clientBasketLogic.calculateProductAmount(quantity, packId, login);
                    clientBasketLogic.updatePackAmount(login, String.valueOf(resultAmount), packId);
                    return new Trigger(PagePath.SHOW_BASKET, Trigger.TriggerType.REDIRECT);
                }else{
                    clientBasketLogic.addToCart(login, packId, quantity);
                    return new Trigger(PagePath.SHOW_BASKET, Trigger.TriggerType.REDIRECT);
                }
            } catch (LogicException e) {
                logger.catching(e);
                return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
            }
        }
    }
}
