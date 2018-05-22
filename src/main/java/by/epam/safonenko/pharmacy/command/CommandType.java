package by.epam.safonenko.pharmacy.command;

import by.epam.safonenko.pharmacy.command.impl.*;

public enum CommandType {
    REGISTRATION(new Registration()),
    CONFIRMATION(new Confirmation()),
    LOGIN(new Login()),
    CHANGE_LOCALE(new ChangeLocale()),
    FORGOT_PASSWORD(new ForgotPassword()),
    LOGOUT(new Logout()),
    CHANGE_FORGOTTEN_PASSWORD(new ChangeForgottenPassword());

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
