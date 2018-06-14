package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.MedicineLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FormMainPage implements Command {
    private static Logger logger = LogManager.getLogger(FormMainPage.class);
    private MedicineLogic medicineLogic;

    private enum Parameter{
        POPULAR_PRODUCT_NUMBER, PRODUCTS
    }

    public FormMainPage(){
        medicineLogic = new MedicineLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        try {
            List<Medicine> products = medicineLogic.findPopularProducts();
            requestContent.addRequestAttribute(Parameter.PRODUCTS.name().toLowerCase(), products);
            int popularProductNumber = medicineLogic.getPopularProductNumber();
            requestContent.addRequestAttribute(Parameter.POPULAR_PRODUCT_NUMBER.name().toLowerCase(), popularProductNumber);
            return new Trigger(PagePath.MAIN_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
