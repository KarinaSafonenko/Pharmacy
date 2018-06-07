package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.util.FormMedicineWithoutPacks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindMedicines implements FindSpecification<Medicine>, FormMedicineWithoutPacks {
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
    public List<Medicine> execute(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            ResultSet resultSet = current.executeQuery();
            List<Medicine> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(formMedicine(resultSet));
            }
            return result;
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public String getRequest() {
        return type.getRequest();
    }
}
