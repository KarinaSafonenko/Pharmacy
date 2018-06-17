package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.util.AbstractShowProducts;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowAllProductsNextPage extends AbstractShowProducts {
    private static Logger logger = LogManager.getLogger(ShowAllProductsNextPage.class);

    @Override
    protected Trigger forward() {
        return new Trigger(PagePath.MEDICINES_PATH, Trigger.TriggerType.FORWARD);
    }

    @Override
    protected int countPage(RequestContent requestContent, List<Medicine> medicineList) {
        return countNextPage(requestContent, medicineList);
    }

    @Override
    protected List<Medicine> formList(RequestContent requestContent) throws LogicException {
        return medicineLogic.findAllMedicines();
    }

    @Override
    protected void logException(LogicException e) {
        logger.catching(e);
    }
}
