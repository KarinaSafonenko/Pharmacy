package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import java.sql.PreparedStatement;

public class FindAllMedicines extends AbstractFindMedicines {
    private static final String REQUEST = "SELECT medicine_id, name, category, recipe_need, description, image_path FROM pharmacy.medicine";
    @Override
    protected void prepareStatement(PreparedStatement current) {
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
