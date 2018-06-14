package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.command.util.PageUtil;
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

public class ShowCategoryProducts implements Command, PageUtil {
    private static Logger logger = LogManager.getLogger(ShowCategoryProducts.class);
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
            int page = medicineLogic.getFirstPageNumber();
            List<Medicine> products = medicineLogic.findMedicinesByCategory(productCategory);
            List productList = medicineLogic.formSubList(products, page);
            requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.PRODUCTS.name().toLowerCase(), productList);
            requestContent.addRequestAttribute(MedicineParameter.CATEGORY.name().toLowerCase(), category);
            addPageParameters(requestContent, products.size(), page);
            return new Trigger(PagePath.SHOP_PATH, Trigger.TriggerType.FORWARD);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}