package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.command.impl.RegistrationCommand;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.UserRepository;
import by.epam.safonenko.pharmacy.specification.FindConfirmationCodeByLogin;
import by.epam.safonenko.pharmacy.specification.FindUsersByLoginSpecification;
import by.epam.safonenko.pharmacy.specification.UpdateCodeByLoginSpecification;
import by.epam.safonenko.pharmacy.util.UserParameter;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.util.*;

public class UserLogic {
    private UserRepository userRepository;

    public UserLogic(){
        userRepository = new UserRepository();
    }

    public Map<RegistrationCommand.RegistrationMessage, UserParameter> addUser(String name, String surname, String patronymic, String sex, String mail, String login, String password, String repeatPassword) throws LogicException {
        Map<RegistrationCommand.RegistrationMessage, UserParameter> incorrect = new HashMap<>();
        boolean validated = true;
        if (!Validator.validateInitials(name)){
            incorrect.put(RegistrationCommand.RegistrationMessage.WRONG_NAME, UserParameter.NAME);
            validated = false;
        }
        if (!Validator.validateInitials(surname)){
            incorrect.put(RegistrationCommand.RegistrationMessage.WRONG_SURNAME, UserParameter.SURNAME);
            validated = false;
        }
        if (!Validator.validateInitials(patronymic)) {
            incorrect.put(RegistrationCommand.RegistrationMessage.WRONG_PATRONYMIC, UserParameter.PATRONYMIC);
            validated = false;
        }
        if (!Validator.validateMail(mail)){
            incorrect.put(RegistrationCommand.RegistrationMessage.WRONG_EMAIL, UserParameter.MAIL);
            validated = false;
        }
        if (!Validator.validateLogin(login)){
            incorrect.put(RegistrationCommand.RegistrationMessage.WRONG_LOGIN, UserParameter.LOGIN);
            validated = false;
        }else if (findLogin(login)){
            incorrect.put(RegistrationCommand.RegistrationMessage.DUPLICATE_LOGIN, UserParameter.LOGIN);
            validated = false;
        }

        if (!Validator.validatePassword(password)){
            incorrect.put(RegistrationCommand.RegistrationMessage.WRONG_PASSWORD, UserParameter.PASSWORD);
            validated = false;
        }

        if (!password.equals(repeatPassword)){
            incorrect.put(RegistrationCommand.RegistrationMessage.DIFFERENT_PASSWORDS, UserParameter.REPEAT_PASSWORD);
            validated = false;
        }

        if (validated){
            try {
                userRepository.add(name, surname, patronymic, sex, mail, login, password);
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
        return incorrect;
    }

    public void setUserCode(String login, String code) throws LogicException {
        if (!Validator.validateCode(code)){
            throw new LogicException("Trying to set invalid code");
        }
        try {
            userRepository.update(new UpdateCodeByLoginSpecification(login, code));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public boolean findLogin(String login) throws LogicException {
        boolean result;
        try {
            result = userRepository.check(new FindUsersByLoginSpecification(login));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
        return result;
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
                foundCode = userRepository.find(new FindConfirmationCodeByLogin(login));
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
            return code.equals(foundCode);
        }
    }

}
