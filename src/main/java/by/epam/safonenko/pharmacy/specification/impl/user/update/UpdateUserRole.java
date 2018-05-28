package by.epam.safonenko.pharmacy.specification.impl.user.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateUserRole implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.user SET user.role = 'DOCTOR' WHERE user.login = ?";
    private String login;

    public UpdateUserRole(String login){
        this.login = login;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setString(1, login);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating user role.");
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
