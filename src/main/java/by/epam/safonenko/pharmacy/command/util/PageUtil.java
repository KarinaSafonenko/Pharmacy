package by.epam.safonenko.pharmacy.command.util;

import by.epam.safonenko.pharmacy.command.impl.ShowCategoryProducts;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.util.RequestContent;

public interface PageUtil extends Logic {
    default void addPageParameters(RequestContent requestContent, int infoSize, int page) throws LogicException {
        int leftBorder = countLeftBorder(infoSize, page);
        int rightBorder = countRightBorder(infoSize, page);
        int productNumberOnPage = getMaxNumberOnPage();
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.PAGE.name().toLowerCase(), page);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.LEFT_BORDER.name().toLowerCase(), leftBorder);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.RIGHT_BORDER.name().toLowerCase(), rightBorder);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.PRODUCT_NUMBER_ON_PAGE.name().toLowerCase(), productNumberOnPage);
        requestContent.addRequestAttribute(ShowCategoryProducts.ShopParameter.RESULT_COUNT.name().toLowerCase(), infoSize);
    }
}
