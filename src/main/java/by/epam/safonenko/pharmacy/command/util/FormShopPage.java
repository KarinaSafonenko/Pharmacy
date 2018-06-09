package by.epam.safonenko.pharmacy.command.util;

import by.epam.safonenko.pharmacy.command.impl.ShowCategoryProducts;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.MedicineLogic;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;

import java.util.List;

public interface FormShopPage {

    default Trigger formShopPage(RequestContent requestContent, List<Medicine> products, String category, int page) throws LogicException {
        MedicineLogic medicineLogic = new MedicineLogic();
        int leftBorder = medicineLogic.countLeftBorder(products, page);
        int rightBorder = medicineLogic.countRightBorder(products, page);
        int productNumberOnPage = medicineLogic.getMaxNumberOnPage();
        List productList = medicineLogic.formSubList(products, page);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.PAGE.name().toLowerCase(), page);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.LEFT_BORDER.name().toLowerCase(), leftBorder);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.RIGHT_BORDER.name().toLowerCase(), rightBorder);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.PRODUCTS.name().toLowerCase(), productList);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.PRODUCT_NUMBER_ON_PAGE.name().toLowerCase(), productNumberOnPage);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.RESULT_COUNT.name().toLowerCase(), products.size());
        requestContent.addRequestAttribute(MedicineParameter.CATEGORY.name().toLowerCase(), category);
        return new Trigger(PagePath.SHOP_PATH, Trigger.TriggerType.FORWARD);
    }
}
