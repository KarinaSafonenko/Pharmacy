package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.MedicineLogic;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowCategoryProducts implements Command {
    private static Logger logger = LogManager.getLogger(ShowCategoryProducts.class);
    private static final int FIRST = 1;
    private MedicineLogic medicineLogic;

    public enum ShopParameter{
        PAGE, LEFT_BORDER, RIGHT_BORDER, PRODUCTS, PRODUCT_NUMBER_ON_PAGE, RESULT_COUNT
    }

    public ShowCategoryProducts() {
        medicineLogic = new MedicineLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String category = requestContent.getRequestParameter(MedicineParameter.CATEGORY.name().toLowerCase());
        Medicine.ProductCategory productCategory = Medicine.ProductCategory.valueOf(category.toUpperCase());
        try {
            List<Medicine> products = medicineLogic.findMedicinesByCategory(productCategory);
            requestContent.addRequestAttribute(ShopParameter.PRODUCTS.name().toLowerCase(), products);
            requestContent.addRequestAttribute(ShopParameter.PAGE.name().toLowerCase(), FIRST);
            requestContent.addSessionAttribute(SessionAttribute.LATEST_PAGE.name().toLowerCase(), PagePath.SHOP_PATH);
            int productOnPage = medicineLogic.getMaxNumberOnPage();
            requestContent.addRequestAttribute(ShopParameter.PRODUCT_NUMBER_ON_PAGE.name().toLowerCase(), productOnPage);
            requestContent.addRequestAttribute(MedicineParameter.CATEGORY.name().toLowerCase(), category);
            if (!products.isEmpty()){
                int productNumber = products.size();
                int rightBorder = (productNumber < productOnPage) ? productNumber : productOnPage;
                requestContent.addRequestAttribute(ShopParameter.LEFT_BORDER.name().toLowerCase(), FIRST);
                requestContent.addRequestAttribute(ShopParameter.RESULT_COUNT.name().toLowerCase(), productNumber);
                requestContent.addRequestAttribute(ShopParameter.RIGHT_BORDER.name().toLowerCase(), rightBorder);
            }
            return new Trigger(PagePath.SHOP_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}