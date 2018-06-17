package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.util.AbstractShowCategoryProducts;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowNextProductPage extends AbstractShowCategoryProducts {
    private static Logger logger = LogManager.getLogger(ShowNextProductPage.class);

    @Override
    protected int countPage(RequestContent requestContent, List<Medicine> medicineList) {
        return countNextPage(requestContent, medicineList);
    }

    @Override
    protected void logException(LogicException e) {
        logger.catching(e);
    }
}
