package by.epam.safonenko.pharmacy.command;

import by.epam.safonenko.pharmacy.command.impl.ConfirmationCommand;
import by.epam.safonenko.pharmacy.command.impl.LoginCommand;
import by.epam.safonenko.pharmacy.command.impl.RegistrationCommand;

public enum CommandType {
    REGISTRATION(new RegistrationCommand()),
    CONFIRMATION(new ConfirmationCommand()),
    LOGIN(new LoginCommand());

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
