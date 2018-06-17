package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import java.sql.PreparedStatement;

public class FindPopularMedicines extends AbstractFindMedicines {
    private static final String REQUEST = "SELECT medicine_id, name, category, recipe_need, description, image_path FROM pharmacy.medicine ORDER BY medicine.number_of_orders DESC";

    @Override
    protected void prepareStatement(PreparedStatement current) {

    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
