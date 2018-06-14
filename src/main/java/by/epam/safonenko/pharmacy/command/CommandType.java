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
    SHOW_NEXT_PRODUCT_PAGE(new ShowNextProductPageUtil()),
    SHOW_PREVIOUS_PRODUCT_PAGE(new ShowPreviousProductPageUtil()),
    SHOW_PROFILE(new ShowProfile()),
    CHANGE_USER_INFO(new ChangeUserInfo()),
    TOP_UP_THE_BALANCE(new TopUpTheBalance()),
    SHOW_BASKET(new ShowBasketProducts()),
    REMOVE_PACK(new RemoveFromBasket()),
    UPDATE_BASKET(new UpdateBasket()),
    REQUEST_RECIPE(new RequestRecipe());

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
