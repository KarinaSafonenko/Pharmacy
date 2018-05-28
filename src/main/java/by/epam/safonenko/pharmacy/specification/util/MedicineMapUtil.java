package by.epam.safonenko.pharmacy.specification.util;

import by.epam.safonenko.pharmacy.entity.Medicine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface MedicineMapUtil extends FormSinglePackMedicine {

    default Map<Medicine, Integer> formMedicineMap(ResultSet resultSet) throws SQLException {
        Map<Medicine, Integer> medicineMap = new HashMap<>();
        while (resultSet.next()) {
            Medicine medicine = formMedicineWithSinglePack(resultSet);
            int amount = findAmount(resultSet);
            medicineMap.put(medicine, amount);
        }
        return medicineMap;
    }

    int findAmount(ResultSet resultSet) throws SQLException;
}
