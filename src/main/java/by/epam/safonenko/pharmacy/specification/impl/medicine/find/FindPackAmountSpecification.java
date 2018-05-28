package by.epam.safonenko.pharmacy.specification.impl.medicine.find;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;
import by.epam.safonenko.pharmacy.specification.impl.medicine.PackParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindPackAmountSpecification implements FindValueSpecification{
    private static String REQUEST = "SELECT pack.amount FROM pharmacy.pack WHERE pack_id = ?";
    private int packId;

    public FindPackAmountSpecification(int packId){
        this.packId = packId;
    }

    @Override
    public String find(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setInt(1, packId);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(PackParameter.AMOUNT.name().toLowerCase());
            }else{
                throw new RepositoryException("Something went wrong while finding pack amount by pack id.");
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
