package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ClientBasketLogic;
import by.epam.safonenko.pharmacy.specification.impl.order.OrderParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowOrderContent implements Command {
    private static Logger logger = LogManager.getLogger(ShowOrderContent.class);
    private ClientBasketLogic clientBasketLogic;

    private enum Parameter{
        BASKET_CONTENT, MEDICINE_LIST
    }

    public ShowOrderContent(){
        clientBasketLogic = new ClientBasketLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String orderId = requestContent.getRequestParameter(OrderParameter.ORDER_ID.name().toLowerCase());
        try {
            Basket basket = clientBasketLogic.findOrderContent(orderId);
            requestContent.addRequestAttribute(Parameter.BASKET_CONTENT.name().toLowerCase(), basket);
            if (basket.getContent() != null) {
                Set<Medicine> medicineSet = basket.getContent().keySet();
                List<Medicine> medicineList = new ArrayList<>(medicineSet);
                requestContent.addRequestAttribute(Parameter.MEDICINE_LIST.name().toLowerCase(), medicineList);
            }
            return new Trigger(PagePath.ORDER_CONTENT_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
