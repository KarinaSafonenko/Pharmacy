package by.epam.safonenko.pharmacy.command.impl;

import by.epam.safonenko.pharmacy.command.MultipartCommand;
import by.epam.safonenko.pharmacy.command.util.UploadUtil;
import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.MedicineLogic;
import by.epam.safonenko.pharmacy.logic.impl.ProducerLogic;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;
import by.epam.safonenko.pharmacy.specification.impl.medicine.PackParameter;
import by.epam.safonenko.pharmacy.specification.impl.medicine.ProducerParameter;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.util.List;


public class AddProduct implements MultipartCommand {
    private static Logger logger = LogManager.getLogger(AddProduct.class);
    private MedicineLogic medicineLogic;
    private ProducerLogic producerLogic;


    private enum Parameter{
        INCORRECT, PRODUCERS
    }

    public AddProduct(){
        medicineLogic = new MedicineLogic();
        producerLogic = new ProducerLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent, Part part, String path, String context) {
        String name = requestContent.getRequestParameter(MedicineParameter.NAME.name().toLowerCase());
        String category = requestContent.getRequestParameter(MedicineParameter.CATEGORY.name().toLowerCase());
        String recipeNeed = requestContent.getRequestParameter(MedicineParameter.RECIPE_NEED.name().toLowerCase());
        String quantity = requestContent.getRequestParameter(PackParameter.QUANTITY.name().toLowerCase());
        String dosage = requestContent.getRequestParameter(PackParameter.DOSAGE.name().toLowerCase());
        String price = requestContent.getRequestParameter(PackParameter.PRICE.name().toLowerCase());
        String amount = requestContent.getRequestParameter(PackParameter.AMOUNT.name().toLowerCase());
        String producerId = requestContent.getRequestParameter(ProducerParameter.PRODUCER_ID.name().toLowerCase());
        String description = requestContent.getRequestParameter(MedicineParameter.DESCRIPTION.name().toLowerCase());

        requestContent.addRequestAttribute(MedicineParameter.NAME.name().toLowerCase(), name);
        requestContent.addRequestAttribute(PackParameter.QUANTITY.name().toLowerCase(), quantity);
        requestContent.addRequestAttribute(PackParameter.DOSAGE.name().toLowerCase(), dosage);
        requestContent.addRequestAttribute(PackParameter.PRICE.name().toLowerCase(), price);
        requestContent.addRequestAttribute(PackParameter.AMOUNT.name().toLowerCase(), amount);
        requestContent.addRequestAttribute(MedicineParameter.DESCRIPTION.name().toLowerCase(), description);

        Medicine.ProductCategory productCategory = Medicine.ProductCategory.valueOf(category.toUpperCase());
        try {
            if (UploadUtil.getFileName(part).isEmpty()) {
                requestContent.addRequestAttribute(Parameter.INCORRECT.name().toLowerCase(), true);
                List<Producer> producers = producerLogic.findProducers();
                requestContent.addRequestAttribute(Parameter.PRODUCERS.name().toLowerCase(), producers);
                return new Trigger(PagePath.ADD_PRODUCT_PATH, Trigger.TriggerType.FORWARD);
            } else {
                String imagePath = context + UploadUtil.load(part, path);
                if (medicineLogic.addMedicine(name, productCategory, recipeNeed, description, imagePath, producerId, quantity, dosage, price, amount)) {
                    return new Trigger(PagePath.SHOW_ALL_PRODUCTS, Trigger.TriggerType.REDIRECT);
                } else {
                    requestContent.addRequestAttribute(Parameter.INCORRECT.name().toLowerCase(), true);
                    List<Producer> producers = producerLogic.findProducers();
                    requestContent.addRequestAttribute(Parameter.PRODUCERS.name().toLowerCase(), producers);
                    return new Trigger(PagePath.ADD_PRODUCT_PATH, Trigger.TriggerType.FORWARD);
                }
            }
        } catch (LogicException e) {
            logger.catching(e);
            return new Trigger(PagePath.ERROR_PATH, Trigger.TriggerType.REDIRECT);
        }
    }
}
