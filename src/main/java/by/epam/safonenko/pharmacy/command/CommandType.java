package by.epam.safonenko.pharmacy.command;

import by.epam.safonenko.pharmacy.command.impl.*;

public enum CommandType {
    REGISTRATION(new Registration()),
    CONFIRMATION(new Confirmation()),
    LOGIN(new Login()),
    CHANGE_LOCALE(new ChangeLocale()),
    FORGOT_PASSWORD(new ForgotPassword()),
    LOGOUT(new Logout()),
    CHANGE_FORGOTTEN_PASSWORD(new ChangeForgottenPassword()),
    FORM_MAIN_PAGE(new FormMainPage()),
    SHOW_CATEGORY_PRODUCTS(new ShowCategoryProducts()),
    ADD_TO_CART(new AddToCart()),
    CHANGE_LATEST_PATH(new ChangeLatestPath());

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
