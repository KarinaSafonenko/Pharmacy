package by.epam.safonenko.pharmacy.specification.util;

import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FormUser {
    default User formUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setName(resultSet.getString(UserParameter.NAME.name().toLowerCase()));
        user.setSurname(resultSet.getString(UserParameter.SURNAME.name().toLowerCase()));
        user.setPatronymic(resultSet.getString(UserParameter.PATRONYMIC.name().toLowerCase()));
        user.setSex(User.UserSex.valueOf(resultSet.getString(UserParameter.SEX.name().toLowerCase())));
        user.setMail(resultSet.getString(UserParameter.MAIL.name().toLowerCase()));
        user.setLogin(resultSet.getString(UserParameter.LOGIN.name().toLowerCase()));
        user.setPassword(resultSet.getString(UserParameter.PASSWORD.name().toLowerCase()));
        user.setRole(User.UserRole.valueOf(resultSet.getString(UserParameter.ROLE.name().toLowerCase())));
        return user;
    }
}
