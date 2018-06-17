package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.command.util.UploadUtil;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.exception.TransactionException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.MedicineRepository;
import by.epam.safonenko.pharmacy.repository.impl.PackRepository;
import by.epam.safonenko.pharmacy.specification.impl.medicine.find.FindAllMedicines;
import by.epam.safonenko.pharmacy.specification.impl.medicine.find.FindMedicinePacks;
import by.epam.safonenko.pharmacy.specification.impl.medicine.find.FindPopularMedicines;
import by.epam.safonenko.pharmacy.specification.impl.medicine.find.FindMedicinesByCategory;
import by.epam.safonenko.pharmacy.transaction.AddProductTransaction;
import by.epam.safonenko.pharmacy.validator.Validator;
import javafx.fxml.LoadException;

import java.math.BigDecimal;
import java.util.List;

public class MedicineLogic implements Logic {
    private MedicineRepository medicineRepository;
    private PackRepository packRepository;

    public MedicineLogic(){
        medicineRepository = new MedicineRepository();
        packRepository = new PackRepository();
    }

    public List<Medicine> findPopularProducts() throws LogicException {
        try {
            List<Medicine> foundMedicines = medicineRepository.find(new FindPopularMedicines());
            return findMedicinePacks(foundMedicines);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public boolean addMedicine(String name, Medicine.ProductCategory category, String recipeNeed,
                               String description, String imagePath, String producerId, String quantity,
                               String dosage, String price, String amount) throws LogicException {
        if (!Validator.validateInitials(name) || !Validator.validateDescription(description) ||
            !Validator.validateNumber(quantity) || !Validator.validateId(producerId) ||
            !Validator.validateNumber(dosage) || !Validator.validateNumber(amount) ||
            !Validator.validateMoneyAmount(price) || !Validator.validateDescription(imagePath)){
            return false;
        }
        int producer = Integer.parseInt(producerId);
        int quantityParam = Integer.parseInt(quantity);
        int dosageParam = Integer.parseInt(dosage);
        int amountParam = Integer.parseInt(amount);
        BigDecimal priceParam = new BigDecimal(price);
        AddProductTransaction transaction= new AddProductTransaction(name, category, recipeNeed, description, imagePath, producer, quantityParam, dosageParam, priceParam, amountParam);
        try {
            transaction.execute();
        } catch (TransactionException e) {
            throw new LogicException(e);
        }
        return true;
    }

    public List<Medicine> findMedicinesByCategory(Medicine.ProductCategory category) throws LogicException {
        try {
            List<Medicine> foundMedicines = medicineRepository.find(new FindMedicinesByCategory(category));
            return findMedicinePacks(foundMedicines);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public List<Medicine> findAllMedicines() throws LogicException {
        try {
            List<Medicine> foundMedicines = medicineRepository.find(new FindAllMedicines());
            return findMedicinePacks(foundMedicines);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    private List<Medicine> findMedicinePacks(List<Medicine> medicines) throws LogicException {
        for (Medicine current: medicines){
            try {
                List<Pack> packList = packRepository.find(new FindMedicinePacks(current.getId()));
                current.setMedicinePacks(packList);
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
        return medicines;
    }

}
