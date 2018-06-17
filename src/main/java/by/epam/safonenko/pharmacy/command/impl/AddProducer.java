package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ProducerLogic;
import by.epam.safonenko.pharmacy.specification.impl.medicine.ProducerParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddProducer implements Command {
    private static Logger logger = LogManager.getLogger(AddProducer.class);
    private ProducerLogic producerLogic;

    private enum Parameter{
        INCORRECT
    }

    public AddProducer(){
        producerLogic = new ProducerLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String name = requestContent.getRequestParameter(ProducerParameter.NAME.name().toLowerCase());
        String country = requestContent.getRequestParameter(ProducerParameter.COUNTRY.name().toLowerCase());

        requestContent.addRequestAttribute(ProducerParameter.NAME.name().toLowerCase(), name);
        requestContent.addRequestAttribute(ProducerParameter.COUNTRY.name().toLowerCase(), country);

        try {
            if (producerLogic.addProducer(name, country)){
                return new Trigger(PagePath.SHOW_ALL_PRODUCTS, Trigger.TriggerType.REDIRECT);
            }else{
                requestContent.addRequestAttribute(Parameter.INCORRECT.name().toLowerCase(), true);
                return new Trigger(PagePath.ADD_PRODUCER_PATH, Trigger.TriggerType.FORWARD);
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
