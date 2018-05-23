package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.MedicineRepository;
import by.epam.safonenko.pharmacy.repository.PackRepository;
import by.epam.safonenko.pharmacy.specification.impl.FindMedicinePacks;
import by.epam.safonenko.pharmacy.specification.impl.FindPopularMedicines;

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
            for (Medicine current: foundMedicines){
                List<Pack> packList = packRepository.find(new FindMedicinePacks(current.getId()));
                current.setMedicinePacks(packList);
            }
            return foundMedicines;
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

}
