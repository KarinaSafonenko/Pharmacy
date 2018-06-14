package by.epam.safonenko.pharmacy.specification.impl.user.find;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindUsersByLogin extends AbstractFindUsers {
    private static String REQUEST = "SELECT login, password, mail, surname, name, patronymic, sex, role FROM pharmacy.user WHERE login = ?";
    private String login;

    public FindUsersByLogin(String login){
        this.login = login;
    }

    @Override
    protected void prepareStatement(PreparedStatement current) throws SQLException {
        current.setString(1, login);
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
