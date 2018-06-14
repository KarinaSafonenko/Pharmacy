package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import java.sql.PreparedStatement;

public class FindMedicines extends AbstractFindMedicines {
    private FindType type;

    public enum FindType{
        ALL ("SELECT medicine_id, name, category, recipe_need, description, image_path FROM pharmacy.medicine") ,
        POPULAR ("SELECT medicine_id, name, category, recipe_need, description, image_path FROM pharmacy.medicine ORDER BY medicine.number_of_orders DESC");

        private final String REQUEST;

        FindType(String request){
            this.REQUEST = request;
        }

        public String getRequest() {
            return REQUEST;
        }
    }

    public FindMedicines(FindType type) {
        this.type = type;
    }

    @Override
    protected void prepareStatement(PreparedStatement current) {

    }

    @Override
    public String getRequest() {
        return type.getRequest();
    }
}
