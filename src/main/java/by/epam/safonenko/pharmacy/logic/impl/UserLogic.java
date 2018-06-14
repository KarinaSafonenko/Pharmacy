package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.command.impl.Registration;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.UserRepository;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.specification.impl.user.find.FindLoginsByPassword;
import by.epam.safonenko.pharmacy.specification.impl.user.find.FindParameterByLogin;
import by.epam.safonenko.pharmacy.specification.impl.user.find.FindUsersByLogin;
import by.epam.safonenko.pharmacy.specification.impl.user.update.UpdateRegistrationStatusByLogin;
import by.epam.safonenko.pharmacy.specification.impl.user.update.UpdateStringParameterByLogin;
import by.epam.safonenko.pharmacy.specification.impl.user.update.UpdateUserInfo;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.util.*;

public class UserLogic implements Logic {
    private UserRepository userRepository;

    public UserLogic(){
        userRepository = new UserRepository();
    }

    public Map<Registration.Parameter, UserParameter> addUser(String name, String surname, String patronymic, String sex, String mail, String login, String password, String repeatPassword, User.UserRole role) throws LogicException {
        Map<Registration.Parameter, UserParameter> incorrect = checkInitials(name, surname, patronymic);
        boolean validated = incorrect.isEmpty();
        if (!Validator.validateMail(mail)){
            incorrect.put(Registration.Parameter.WRONG_EMAIL, UserParameter.MAIL);
            validated = false;
        }
        if (!Validator.validateLogin(login)){
            incorrect.put(Registration.Parameter.WRONG_LOGIN, UserParameter.LOGIN);
            validated = false;
        }else if (findLogin(login)){
            incorrect.put(Registration.Parameter.DUPLICATE_LOGIN, UserParameter.LOGIN);
            validated = false;
        }

        if (!Validator.validatePassword(password)){
            incorrect.put(Registration.Parameter.WRONG_PASSWORD, UserParameter.PASSWORD);
            validated = false;
        }

        if (!password.equals(repeatPassword)){
            incorrect.put(Registration.Parameter.DIFFERENT_PASSWORDS, UserParameter.REPEAT_PASSWORD);
            validated = false;
        }

        if (validated){
            try {
                userRepository.add(name, surname, patronymic, sex, mail, login, password, role);
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
        return incorrect;
    }

    public Map<Registration.Parameter, UserParameter> checkInitials(String name, String surname, String patronymic){
        Map<Registration.Parameter, UserParameter> incorrect = new HashMap<>();
        if (!Validator.validateInitials(name)){
            incorrect.put(Registration.Parameter.WRONG_NAME, UserParameter.NAME);
        }
        if (!Validator.validateInitials(surname)){
            incorrect.put(Registration.Parameter.WRONG_SURNAME, UserParameter.SURNAME);
        }
        if (!Validator.validateInitials(patronymic)) {
            incorrect.put(Registration.Parameter.WRONG_PATRONYMIC, UserParameter.PATRONYMIC);
        }
        return incorrect;
    }

    public Map<Registration.Parameter, UserParameter> comparePasswords(String password, String repeatPassword){
        Map<Registration.Parameter, UserParameter> incorrect = new HashMap<>();
        if (!Validator.validatePassword(password)){
            incorrect.put(Registration.Parameter.WRONG_PASSWORD, UserParameter.PASSWORD);
        }else if (!password.equals(repeatPassword)){
            incorrect.put(Registration.Parameter.DIFFERENT_PASSWORDS, UserParameter.REPEAT_PASSWORD);
        }
        return incorrect;
    }

    public void updateUserInfo (String login, String name, String surname, String patronymic, String sex) throws LogicException {
        if (!Validator.validateLogin(login) || !Validator.validateInitials(name)
                || !Validator.validateInitials(surname) || !Validator.validateInitials(patronymic)) {
            throw new LogicException("Trying to update user info with incorrect parameter values");
        }
        try {
            userRepository.update(new UpdateUserInfo(name, surname, patronymic, sex, login));
        } catch (RepositoryException e) {
            throw  new LogicException(e);
        }
    }


    public void setUserCode(String login, String code) throws LogicException {
        if (!Validator.validateCode(code)){
            throw new LogicException("Trying to set invalid code");
        }
        try {
            userRepository.update(new UpdateStringParameterByLogin(login, UpdateStringParameterByLogin.ParameterType.CONFIRMATION_CODE, code));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public void setUserPassword(String login, String pasword) throws LogicException {
        if (!Validator.validatePassword(pasword)){
            throw new LogicException("Trying to set invalid password");
        }
        try {
            userRepository.update(new UpdateStringParameterByLogin(login, UpdateStringParameterByLogin.ParameterType.PASSWORD, pasword));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public boolean findLogin(String login) throws LogicException {
        boolean result;
        try {
            result = userRepository.check(new FindUsersByLogin(login));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
        return result;
    }

    public User findUser(String login) throws LogicException {
        try {
            List<User> foundUsers = userRepository.find(new FindUsersByLogin(login));
            if (foundUsers.isEmpty()){
                throw  new LogicException("User hasn't been found");
            }else {
                return foundUsers.get(0);
            }
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public String generateUserCode(){
        return UUID.randomUUID().toString();
    }

    public boolean checkConfirmationCode(String login, String code) throws LogicException {
        if (!Validator.validateLogin(login) || !Validator.validateCode(code)){
            return false;
        } else{
            String foundCode;
            try {
                foundCode = userRepository.find(new FindParameterByLogin(login, FindParameterByLogin.RequestType.CONFIRMATION_CODE));
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
            return code.equals(foundCode);
        }
    }

    public boolean checkUserRegisteredStatus(String login) throws LogicException {
        if (!Validator.validateLogin(login)){
            return false;
        } else{
            String foundStatus;
            try {
                foundStatus = userRepository.find(new FindParameterByLogin(login, FindParameterByLogin.RequestType.REGISTERED));
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
            return Boolean.valueOf(foundStatus);
        }
    }

    public String findMailByLogin(String login) throws LogicException {
        if (!Validator.validateLogin(login)){
            throw new LogicException("Trying to use invalid user login while finding mail by login.");
        } else{
            try {
                return userRepository.find(new FindParameterByLogin(login, FindParameterByLogin.RequestType.MAIL));
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
    }


    public void setUserRegisteredStatus(String login) throws LogicException {
        try {
            userRepository.update(new UpdateRegistrationStatusByLogin(login));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public boolean checkPassword(String login, String password) throws LogicException {
        if (!Validator.validateLogin(login) || !Validator.validatePassword(password)) {
            return false;
        } else {
            try {
                List<String> foundLogins = userRepository.findValues(new FindLoginsByPassword(password));
                return !foundLogins.isEmpty() && foundLogins.contains(login);
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
    }


}
