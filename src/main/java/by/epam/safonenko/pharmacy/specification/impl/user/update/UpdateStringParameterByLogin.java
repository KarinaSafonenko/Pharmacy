package by.epam.safonenko.pharmacy.specification.impl.user.update;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateStringParameterByLogin implements UpdateSpecification {
    private String login;
    private ParameterType parameterType;
    private String value;

    public enum ParameterType{
        CONFIRMATION_CODE ("UPDATE pharmacy.user SET user.confirmation_code = ? WHERE login = ?") ,
        PASSWORD ("UPDATE pharmacy.user SET user.password = SHA1(?) WHERE login = ?"),
        MAIL("UPDATE pharmacy.user SET user.mail = ? WHERE login = ?");
        private final String REQUEST;

        ParameterType(String request){
            this.REQUEST = request;
        }

        public String getRequest() {
            return REQUEST;
        }

    }

    public UpdateStringParameterByLogin(String login, ParameterType parameterType, String value){
        this.login = login;
        this.parameterType = parameterType;
        this.value = value;
    }

    @Override
    public void update(Statement statement) throws RepositoryException {
        PreparedStatement current = (PreparedStatement) statement;
        try {
            current.setString(1, value);
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
        return parameterType.getRequest();
    }
}
