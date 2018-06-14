package by.epam.safonenko.pharmacy.specification.impl.user.find;

import java.sql.PreparedStatement;

public class FindUsers extends AbstractFindUsers {
    private static String REQUEST = "SELECT login, password, mail, surname, name, patronymic, sex, role FROM pharmacy.user";


    @Override
    protected void prepareStatement(PreparedStatement current) {
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
