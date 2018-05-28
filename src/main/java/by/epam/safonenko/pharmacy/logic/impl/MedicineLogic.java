package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.MedicineRepository;
import by.epam.safonenko.pharmacy.repository.impl.PackRepository;
import by.epam.safonenko.pharmacy.specification.impl.medicine.find.FindMedicinePacks;
import by.epam.safonenko.pharmacy.specification.impl.medicine.find.FindMedicines;
import by.epam.safonenko.pharmacy.specification.impl.medicine.find.FindMedicinesByCategory;

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
            List<Medicine> foundMedicines = medicineRepository.find(new FindMedicines(FindMedicines.FindType.POPULAR));
            return findMedicinePacks(foundMedicines);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public List<Medicine> findMedicinesByCategory(Medicine.ProductCategory category) throws LogicException {
        try {
            List<Medicine> foundMedicines = medicineRepository.find(new FindMedicinesByCategory(category));
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
