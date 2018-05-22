package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.command.impl.Registration;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.UserRepository;
import by.epam.safonenko.pharmacy.specification.impl.*;
import by.epam.safonenko.pharmacy.util.UserParameter;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.util.*;

public class UserLogic {
    private UserRepository userRepository;

    public UserLogic(){
        userRepository = new UserRepository();
    }

    public Map<Registration.RegistrationMessage, UserParameter> addUser(String name, String surname, String patronymic, String sex, String mail, String login, String password, String repeatPassword, UserRepository.UserRole role) throws LogicException {
        Map<Registration.RegistrationMessage, UserParameter> incorrect = new HashMap<>();
        boolean validated = true;
        if (!Validator.validateInitials(name)){
            incorrect.put(Registration.RegistrationMessage.WRONG_NAME, UserParameter.NAME);
            validated = false;
        }
        if (!Validator.validateInitials(surname)){
            incorrect.put(Registration.RegistrationMessage.WRONG_SURNAME, UserParameter.SURNAME);
            validated = false;
        }
        if (!Validator.validateInitials(patronymic)) {
            incorrect.put(Registration.RegistrationMessage.WRONG_PATRONYMIC, UserParameter.PATRONYMIC);
            validated = false;
        }
        if (!Validator.validateMail(mail)){
            incorrect.put(Registration.RegistrationMessage.WRONG_EMAIL, UserParameter.MAIL);
            validated = false;
        }
        if (!Validator.validateLogin(login)){
            incorrect.put(Registration.RegistrationMessage.WRONG_LOGIN, UserParameter.LOGIN);
            validated = false;
        }else if (findLogin(login)){
            incorrect.put(Registration.RegistrationMessage.DUPLICATE_LOGIN, UserParameter.LOGIN);
            validated = false;
        }

        if (!Validator.validatePassword(password)){
            incorrect.put(Registration.RegistrationMessage.WRONG_PASSWORD, UserParameter.PASSWORD);
            validated = false;
        }

        if (!password.equals(repeatPassword)){
            incorrect.put(Registration.RegistrationMessage.DIFFERENT_PASSWORDS, UserParameter.REPEAT_PASSWORD);
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

    public Map<Registration.RegistrationMessage, UserParameter> comparePasswords(String password, String repeatPassword){
        Map<Registration.RegistrationMessage, UserParameter> incorrect = new HashMap<>();
        if (!Validator.validatePassword(password)){
            incorrect.put(Registration.RegistrationMessage.WRONG_PASSWORD, UserParameter.PASSWORD);
        }

        if (!password.equals(repeatPassword)){
            incorrect.put(Registration.RegistrationMessage.DIFFERENT_PASSWORDS, UserParameter.REPEAT_PASSWORD);
        }

        return incorrect;
    }

    public void setUserCode(String login, String code) throws LogicException {
        if (!Validator.validateCode(code)){
            throw new LogicException("Trying to set invalid code");
        }
        try {
            userRepository.update(new UpdateParameterByLogin(login, UpdateParameterByLogin.ParameterType.CONFIRMATION_CODE, code));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public void setUserPassword(String login, String pasword) throws LogicException {
        if (!Validator.validatePassword(pasword)){
            throw new LogicException("Trying to set invalid password");
        }
        try {
            userRepository.update(new UpdateParameterByLogin(login, UpdateParameterByLogin.ParameterType.PASSWORD, pasword));
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
            return new Boolean(foundStatus);
        }
    }

    public String findMailByLogin(String login) throws LogicException {
        if (!Validator.validateLogin(login)){
            throw new LogicException("Trying to use invalid user login while finding mail by login.");
        } else{
            try {
                String foundMail = userRepository.find(new FindParameterByLogin(login, FindParameterByLogin.RequestType.MAIL));
                return foundMail;
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
                if (foundLogins.isEmpty()){
                    return false;
                }
                return foundLogins.contains(login);
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
    }


}
