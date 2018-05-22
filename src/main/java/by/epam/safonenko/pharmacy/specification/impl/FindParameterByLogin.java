package by.epam.safonenko.pharmacy.specification.impl;

import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindValueSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindParameterByLogin implements FindValueSpecification {
    private String login;
    private RequestType requestType;

    public enum RequestType{
        CONFIRMATION_CODE ("SELECT user.confirmation_code FROM pharmacy.user WHERE login = ?") ,
        REGISTERED ("SELECT user.registered FROM pharmacy.user WHERE login = ?"),
        MAIL("SELECT user.mail FROM pharmacy.user WHERE login = ?");

        private final String REQUEST;

        RequestType(String request){
            this.REQUEST = request;
        }

        public String getRequest() {
            return REQUEST;
        }

    }

    public FindParameterByLogin(String login, RequestType requestType){
        this.login = login;
        this.requestType = requestType;
    }

    @Override
    public String find(Statement statement) throws RepositoryException {
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(requestType.name().toLowerCase());
            }else{
                throw new RepositoryException("Something went wrong while finding parameter by login.");
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public String getRequest() {
        return requestType.getRequest();
    }
}
