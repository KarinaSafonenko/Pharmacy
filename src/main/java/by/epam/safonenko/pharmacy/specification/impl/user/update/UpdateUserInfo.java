package by.epam.safonenko.pharmacy.specification.impl.user.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateUserInfo implements UpdateSpecification {
    private static final String REQUEST = "UPDATE pharmacy.user SET user.name = ?, user.surname = ?, user.patronymic = ?, user.sex = ? WHERE login = ?";
    private String login;
    private String name;
    private String surname;
    private String patronymic;
    private String sex;

    public UpdateUserInfo(String name, String surname, String patronymic, String sex, String login){
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.sex = sex;
        this.login = login;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setString(1, name);
            current.setString(2, surname);
            current.setString(3, patronymic);
            current.setString(4, sex);
            current.setString(5, login);
            int changed = current.executeUpdate();
            if (changed == 0) {
                throw new RepositoryException("Something went wrong while updating user info.");
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
