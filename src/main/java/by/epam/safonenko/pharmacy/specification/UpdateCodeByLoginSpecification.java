package by.epam.safonenko.pharmacy.specification;

import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.RepositoryException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateCodeByLoginSpecification implements UpdateSpecification<User>{
    private static final String REQUEST = "UPDATE pharmacy.user SET user.confirm_code = ? WHERE login = ?";

    private String login;
    private String code;

    public UpdateCodeByLoginSpecification(String login, String code){
        this.login = login;
        this.code = code;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setString(1, code);
            current.setString(2, login);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while setting user code");
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
