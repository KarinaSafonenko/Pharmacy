package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.MedicineLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.parameter.MedicineParameter;
import by.epam.safonenko.pharmacy.util.parameter.ProductCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowCategoryProducts implements Command {
    private static Logger logger = LogManager.getLogger(ShowCategoryProducts.class);
    private static final String PRODUCTS = "products";
    private MedicineLogic medicineLogic;

    public ShowCategoryProducts() {
        medicineLogic = new MedicineLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String category = requestContent.getRequestParameter(MedicineParameter.CATEGORY.name().toLowerCase());
        ProductCategory productCategory = ProductCategory.valueOf(category.toUpperCase());
        try {
            List<Medicine> products = medicineLogic.findMedicinesByCategory(productCategory);
            requestContent.addRequestAttribute(PRODUCTS, products);
            return new Trigger(PagePath.TEST_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}