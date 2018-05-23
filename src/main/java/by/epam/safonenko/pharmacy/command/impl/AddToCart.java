package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.ClientBasketLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.SessionAttribute;
import by.epam.safonenko.pharmacy.util.parameter.ClientBasketParameter;
import by.epam.safonenko.pharmacy.util.parameter.UserParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddToCart implements Command {
    private static Logger logger = LogManager.getLogger(AddToCart.class);
    private static final String QUANTITY = "quantity";
    private ClientBasketLogic clientBasketLogic;

    public AddToCart(){
        clientBasketLogic = new ClientBasketLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String login = (String) requestContent.getSessionAttribute(UserParameter.LOGIN.name().toLowerCase());
        String latestPage = (String) requestContent.getSessionAttribute(SessionAttribute.LATEST_PAGE.name().toLowerCase());
        if (login == null){
            return new Trigger(PagePath.LOGIN_PATH, Trigger.TriggerType.REDIRECT);
        }else{
            String packId = requestContent.getRequestParameter(ClientBasketParameter.PACK_ID.name().toLowerCase());
            String quantity = requestContent.getRequestParameter(QUANTITY);
            try {
                if (clientBasketLogic.checkPackIdInClientBasket(packId, login)){
                    int amount = Integer.parseInt(quantity);
                    int currentAmount = clientBasketLogic.findPackAmount(packId, login);
                    String resultAmount = String.valueOf(amount + currentAmount);
                    clientBasketLogic.setPackAmount(login, resultAmount);
                    return new Trigger(latestPage, Trigger.TriggerType.FORWARD);
                }else{
                    clientBasketLogic.setPackAmount(login, quantity);
                    return new Trigger(latestPage, Trigger.TriggerType.FORWARD);
                }
            } catch (LogicException e) {
                logger.catching(e);
                return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
            }
        }
    }
}
