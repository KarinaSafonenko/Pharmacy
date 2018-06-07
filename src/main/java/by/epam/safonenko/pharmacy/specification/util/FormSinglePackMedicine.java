package by.epam.safonenko.pharmacy.specification.util;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.entity.Producer;
import by.epam.safonenko.pharmacy.specification.impl.medicine.MedicineParameter;
import by.epam.safonenko.pharmacy.specification.impl.medicine.PackParameter;
import by.epam.safonenko.pharmacy.specification.impl.medicine.ProducerParameter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface FormSinglePackMedicine extends FormMedicineWithoutPacks {
    String PRODUCER = "producer.";
    String MEDICINE = "medicine.";
    String PACK = "pack.";

    default Medicine formMedicineWithSinglePack(ResultSet resultSet) throws SQLException {
        Medicine medicine = formMedicine(resultSet);
        Pack pack = new Pack();
        Producer producer = new Producer();
        List<Pack> packList = new ArrayList<>();
        producer.setName(resultSet.getString(PRODUCER + ProducerParameter.NAME.name().toLowerCase()));
        producer.setCountry(resultSet.getString(ProducerParameter.COUNTRY.name().toLowerCase()));
        pack.setProducer(producer);
        pack.setPackId(resultSet.getInt(PackParameter.PACK_ID.name().toLowerCase()));
        pack.setPrice(resultSet.getBigDecimal(PackParameter.PRICE.name().toLowerCase()));
        pack.setQuantity(resultSet.getInt(PackParameter.QUANTITY.name().toLowerCase()));
        pack.setDosage(resultSet.getInt(PackParameter.DOSAGE.name().toLowerCase()));
        pack.setAmount(resultSet.getInt(PACK + PackParameter.AMOUNT.name().toLowerCase()));
        packList.add(pack);
        medicine.setId(resultSet.getInt(MedicineParameter.MEDICINE_ID.name().toLowerCase()));
        medicine.setName(resultSet.getString(MEDICINE + MedicineParameter.NAME.name().toLowerCase()));
        medicine.setCategory(Medicine.ProductCategory.valueOf(resultSet.getString(MedicineParameter.CATEGORY.name().toLowerCase())));
        medicine.setRecipeNeed(resultSet.getBoolean(MedicineParameter.RECIPE_NEED.name().toLowerCase()));
        medicine.setDescription(resultSet.getString(MedicineParameter.DESCRIPTION.name().toLowerCase()));
        medicine.setMedicinePacks(packList);
        return medicine;
    }
}
