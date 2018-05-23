package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.MedicineRepository;
import by.epam.safonenko.pharmacy.repository.PackRepository;
import by.epam.safonenko.pharmacy.specification.impl.FindMedicinePacks;
import by.epam.safonenko.pharmacy.specification.impl.FindMedicinesByCategory;
import by.epam.safonenko.pharmacy.specification.impl.FindPopularMedicines;
import by.epam.safonenko.pharmacy.util.parameter.ProductCategory;

import java.util.List;

public class MedicineLogic {
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

    public List<Medicine> findMedicinesByCategory(ProductCategory category) throws LogicException {
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
