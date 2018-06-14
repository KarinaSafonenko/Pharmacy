package by.epam.safonenko.pharmacy.specification.impl.recipe.find;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindRecipesByLoginAndPackId extends AbstractFindRecipes {
    private static String REQUEST = "SELECT recipe.doctor, recipe.number, recipe.start_date, recipe.end_date, pack.pack_id, producer.name, producer.country, pack.quantity, pack.dosage, pack.price, pack.amount, medicine.medicine_id, medicine.name, medicine.image_path, medicine.category, medicine.recipe_need, medicine.description\n" +
            "            FROM  pharmacy.recipe INNER JOIN (pharmacy.pack INNER JOIN pharmacy.medicine ON medicine.medicine_id = pack.medicine_id\n" +
            "                                  INNER JOIN pharmacy.producer ON producer.producer_id = pack.producer_id)\n" +
            "            ON recipe.pack_id = pack.pack_id WHERE recipe.client = ? AND recipe.pack_id = ?";

    private String login;
    private int packId;

    public FindRecipesByLoginAndPackId(String login, int packId){
        this.login = login;
        this.packId = packId;
    }

    @Override
    protected void prepareStatement(PreparedStatement current) throws SQLException {
        current.setString(1, login);
        current.setInt(2, packId);
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
