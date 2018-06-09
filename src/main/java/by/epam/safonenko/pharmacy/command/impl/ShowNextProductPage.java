package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.command.util.FormShopPage;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.MedicineLogic;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowNextProductPage implements Command, FormShopPage {
    private static Logger logger = LogManager.getLogger(ShowNextProductPage.class);
    private MedicineLogic medicineLogic;

    public ShowNextProductPage() {
        medicineLogic = new MedicineLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String category = requestContent.getRequestParameter(MedicineParameter.CATEGORY.name().toLowerCase());
        Medicine.ProductCategory productCategory = Medicine.ProductCategory.valueOf(category.toUpperCase());
        int page = Integer.valueOf(requestContent.getRequestParameter(ShowCategoryProducts.ShopParameter.PAGE.name().toLowerCase()));
        page++;
        try {
            List<Medicine> products = medicineLogic.findMedicinesByCategory(productCategory);
            int maxPageNumber = medicineLogic.countPageNumber(products);
            if (page > maxPageNumber){
                page = maxPageNumber;
            }
            return formShopPage(requestContent, products, category, page);
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
