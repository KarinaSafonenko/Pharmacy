package by.epam.safonenko.pharmacy.specification.impl.recipe.find;


import java.sql.PreparedStatement;

public class FindRecipes extends AbstractFindRecipes{
    private static String REQUEST = "SELECT recipe.doctor, recipe.number, recipe.start_date, recipe.end_date, pack.pack_id, producer.name, producer.country, pack.quantity, pack.dosage, pack.price, pack.amount, pack.image_path, medicine.medicine_id, medicine.name, medicine.category, medicine.recipe_need, medicine.description\n" +
            "            FROM  pharmacy.recipe INNER JOIN (pharmacy.pack INNER JOIN pharmacy.medicine ON medicine.medicine_id = pack.medicine_id\n" +
            "                                  INNER JOIN pharmacy.producer ON producer.producer_id = pack.producer_id)\n" +
            "            ON recipe.pack_id = pack.pack_id";

    @Override
    protected void prepareStatement(PreparedStatement current) {
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
