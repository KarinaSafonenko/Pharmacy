package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ProducerLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PrepareAddProduct implements Command {
    private static Logger logger = LogManager.getLogger(PrepareAddProduct.class);
    private ProducerLogic producerLogic;

    private enum Parameter{
        PRODUCERS
    }

    public PrepareAddProduct(){
        producerLogic = new ProducerLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        try {
            List<Producer> producers = producerLogic.findProducers();
            if (producers.isEmpty()) {
                return new Trigger(PagePath.ADD_PRODUCER_PATH, Trigger.TriggerType.REDIRECT);
            }
            requestContent.addRequestAttribute(Parameter.PRODUCERS.name().toLowerCase(), producers);
            return new Trigger(PagePath.ADD_PRODUCT_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
