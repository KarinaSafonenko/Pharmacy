package by.epam.safonenko.pharmacy.command;

import by.epam.safonenko.pharmacy.controller.Trigger;
import by.epam.safonenko.pharmacy.logic.UserLogic;
import by.epam.safonenko.pharmacy.util.Message;
import by.epam.safonenko.pharmacy.util.PagePath;
import by.epam.safonenko.pharmacy.util.RequestContent;
import by.epam.safonenko.pharmacy.util.UserParameter;
import by.epam.safonenko.pharmacy.validator.Validator;


public class RegistrationCommand implements Command {
    private UserLogic userLogic;

    public RegistrationCommand(){
        userLogic = new UserLogic();
    }

    @Override
    public Trigger execute(RequestContent requestContent) {
        String name = requestContent.getRequestParameter(UserParameter.NAME.toString().toLowerCase()).trim();
        String surname = requestContent.getRequestParameter(UserParameter.SURNAME.toString().toLowerCase()).trim();
        String patronymic = requestContent.getRequestParameter(UserParameter.PATRONYMIC.toString().toLowerCase()).trim();
        String sex = requestContent.getRequestParameter(UserParameter.SEX.toString().toLowerCase()).trim();
        String mail = requestContent.getRequestParameter(UserParameter.MAIL.toString().toLowerCase()).trim();
        String login = requestContent.getRequestParameter(UserParameter.LOGIN.toString().toLowerCase()).trim();
        String password = requestContent.getRequestParameter(UserParameter.PASSWORD.toString().toLowerCase()).trim();

        boolean validated = true;

        if (!Validator.validateInitials(name)){
            requestContent.addRequestAttridute(Message.MessageType.WRONG_NAME.getValue(), Message.INCORRECT_NAME);
            validated = false;
        }
        if (!Validator.validateInitials(surname)){
            requestContent.addRequestAttridute(Message.MessageType.WRONG_SURNAME.getValue(), Message.INCORRECT_SURNAME);
            validated = false;
        }
        if (!Validator.validateInitials(patronymic)) {
            requestContent.addRequestAttridute(Message.MessageType.WRONG_PATRONYMIC.getValue(), Message.INCORRECT_PATRONYMIC);
            validated = false;
        }
        if (!Validator.validateMail(mail)){
            requestContent.addRequestAttridute(Message.MessageType.WRONG_EMAIL.getValue(), Message.INCORRECT_EMAIL);
            validated = false;
        }
        if (!Validator.validateLogin(login)){
            requestContent.addRequestAttridute(Message.MessageType.WRONG_LOGIN.getValue(), Message.INCORRECT_LOGIN);
            validated = false;
        }
        if (!Validator.validatePassword(password)){
            requestContent.addRequestAttridute(Message.MessageType.WRONG_PASSWORD.getValue(), Message.INCORRECT_PASSWORD);
            validated = false;
        }

        if (validated){
            if (!userLogic.checkUser(login)) {
                userLogic.addUser(name, surname, patronymic, sex, mail, login, password);
                requestContent.addRequestAttridute(UserParameter.NAME.toString().toLowerCase(), name);
                return new Trigger(PagePath.MAIN_PATH, Trigger.TriggerType.FORWARD);
            }else{
                requestContent.addRequestAttridute(Message.MessageType.WRONG_LOGIN.getValue(), Message.DUPLICATE_LOGIN);
                return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.FORWARD);
            }
        }
        return new Trigger(PagePath.INDEX_PATH, Trigger.TriggerType.FORWARD);
    }

}
