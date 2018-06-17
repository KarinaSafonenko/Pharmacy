package by.epam.safonenko.pharmacy.command.util;

import by.epam.safonenko.pharmacy.command.Command;
import by.epam.safonenko.pharmacy.command.impl.ShowCategoryProducts;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.logic.impl.MedicineLogic;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;

import java.util.List;

public abstract class AbstractShowProducts implements Command, Logic{
    protected MedicineLogic medicineLogic;

    protected enum ShopParameter{
        PAGE, LEFT_BORDER, RIGHT_BORDER, PRODUCTS, PRODUCT_NUMBER_ON_PAGE, RESULT_COUNT
    }

    protected AbstractShowProducts() {
        medicineLogic = new MedicineLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        try {
            List<Medicine> products = formList(requestContent);
            int page = countPage(requestContent, products);
            List productList = medicineLogic.formSubList(products, page);
            requestContent.addRequestAttribute(ShopParameter.PRODUCTS.name().toLowerCase(), productList);
            addPageParameters(requestContent, products.size(), page);
            return forward();
        } catch (LogicException e) {
            logException(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }

    private void addPageParameters(RequestContent requestContent, int infoSize, int page) throws LogicException {
        int leftBorder = countLeftBorder(infoSize, page);
        int rightBorder = countRightBorder(infoSize, page);
        int productNumberOnPage = getMaxNumberOnPage();
        requestContent.addRequestAttribute(ShopParameter.PAGE.name().toLowerCase(), page);
        requestContent.addRequestAttribute(ShopParameter.LEFT_BORDER.name().toLowerCase(), leftBorder);
        requestContent.addRequestAttribute(ShopParameter.RIGHT_BORDER.name().toLowerCase(), rightBorder);
        requestContent.addRequestAttribute(ShopParameter.PRODUCT_NUMBER_ON_PAGE.name().toLowerCase(), productNumberOnPage);
        requestContent.addRequestAttribute(ShopParameter.RESULT_COUNT.name().toLowerCase(), infoSize);
    }

    protected int getFirstPage(){
        return medicineLogic.getFirstPageNumber();
    }

    protected int countNextPage(RequestContent requestContent, List<Medicine> medicineList){
        int page = Integer.valueOf(requestContent.getRequestParameter(ShowCategoryProducts.ShopParameter.PAGE.name().toLowerCase()));
        page++;
        int maxPageNumber = medicineLogic.countPageNumber(medicineList);
        if (page > maxPageNumber){
            page = maxPageNumber;
        }
        return page;
    }

    protected int countPreviousPage(RequestContent requestContent){
        int page = Integer.valueOf(requestContent.getRequestParameter(ShowCategoryProducts.ShopParameter.PAGE.name().toLowerCase()));
        page--;
        int firstPageNumber = medicineLogic.getFirstPageNumber();
        if (page < medicineLogic.getFirstPageNumber()){
            page = firstPageNumber;
        }
        return page;
    }

    protected abstract Trigger forward();

    protected abstract int countPage(RequestContent requestContent, List<Medicine> medicineList);

    protected abstract List<Medicine> formList(RequestContent requestContent) throws LogicException;

    protected abstract void logException(LogicException e);
}
