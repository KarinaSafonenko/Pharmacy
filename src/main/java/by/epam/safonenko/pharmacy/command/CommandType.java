package by.epam.safonenko.pharmacy.command;

public enum CommandType {
    REGISTRATION(new RegistrationCommand());

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
