package by.epam.safonenko.pharmacy.command.util;

import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;

import java.util.List;

public abstract class AbstractShowCategoryProducts extends AbstractShowProducts{
    @Override
    protected Trigger forward() {
        return new Trigger(PagePath.SHOP_PATH, Trigger.TriggerType.FORWARD);
    }

    @Override
    protected List<Medicine> formList(RequestContent requestContent) throws LogicException {
        String category = requestContent.getRequestParameter(MedicineParameter.CATEGORY.name().toLowerCase());
        Medicine.ProductCategory productCategory = Medicine.ProductCategory.valueOf(category.toUpperCase());
        requestContent.addRequestAttribute(MedicineParameter.CATEGORY.name().toLowerCase(), category);
        return medicineLogic.findMedicinesByCategory(productCategory);
    }
}
