package by.epam.safonenko.pharmacy.validator;

public class Validator {
    private static final String INITIALS_TEMPLATE = "[а-яА-Я]{1,45}";
    private static final String MAIL_TEMPLATE = "[^-](([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}])|(([a-zA-Z\\-\\d]+\\.)+[a-zA-Z]{2,}))";
    private static final String LOGIN_TEMPLATE = "[a-zA-Z\\d]+([_ -]?[a-zA-Z\\d])*";
    private static final String PASSWORD_TEMPLATE = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{6,20})";
    private static final String CODE_TEMPLATE = "[\\da-zA-Z]{8}-[\\da-zA-Z]{4}-[\\da-zA-Z]{4}-[\\da-zA-Z]{4}-[\\da-zA-Z]{12}";
    private static final String ID_TEMPLATE = "\\d{1,5}";
    private static final String AMOUNT_TEMPLATE = "\\d{1,5}";
    private static final String MONEY_AMOUNT_TEMPLATE = "\\d{1,5}(.\\d{1,2})*";
    private static final String DATE_TEMPLATE = "\\d{4}-\\d{2}-\\d{2}";
    private static final String CARD_NUMBER_TEMPLATE = "\\d{12}";
    private static final String CARD_CODE_TEMPLATE = "\\d{3}";
    private static final String DESCRIPTION = "[^<>@$#^*]{1,300}";
    private static final String STREET_TEMPLATE = "[а-яА-Я]+(-|\\s)?[а-яА-Я]*";
    private static final int MAX_MAIL_LENGTH = 50;
    private static final int MAX_LOGIN_LENGTH = 20;

    public static boolean validateInitials(String input) {
        return !isEmptyOrNull(input) && input.matches(INITIALS_TEMPLATE);
    }

    public static boolean validateNumber(String input) {
        return !isEmptyOrNull(input) && input.matches(AMOUNT_TEMPLATE);
    }

    public static boolean validateDescription(String input) {
        return !isEmptyOrNull(input) && input.matches(DESCRIPTION);
    }

    public static boolean validateStreet(String input) {
        return !isEmptyOrNull(input) && input.matches(STREET_TEMPLATE);
    }

    public static boolean validateDate(String input) {
        return !isEmptyOrNull(input) && input.matches(DATE_TEMPLATE);
    }

    public static boolean validateCardNumber(String input) {
        return !isEmptyOrNull(input) && input.matches(CARD_NUMBER_TEMPLATE);
    }

    public static boolean validateCardCode(String input) {
        return !isEmptyOrNull(input) && input.matches(CARD_CODE_TEMPLATE);
    }

    public static boolean validateId(String input) {
        return !isEmptyOrNull(input) && input.matches(ID_TEMPLATE);
    }

    public static boolean validateMoneyAmount(String input) {
        return !isEmptyOrNull(input) && input.matches(MONEY_AMOUNT_TEMPLATE);
    }

    public static boolean validateMail(String input) {
        return input.length() <= MAX_MAIL_LENGTH && !isEmptyOrNull(input) && input.matches(MAIL_TEMPLATE);
    }

    public static boolean validateLogin(String input) {
        return input.length() <= MAX_LOGIN_LENGTH && !isEmptyOrNull(input) && input.matches(LOGIN_TEMPLATE);
    }

    public static boolean validatePassword(String input) {
        return !isEmptyOrNull(input) && input.matches(PASSWORD_TEMPLATE);
    }

    public static boolean validateCode(String input) {
        return !isEmptyOrNull(input) && input.matches(CODE_TEMPLATE);
    }

    private static boolean isEmptyOrNull(String input){
        return input == null || input.trim().isEmpty();
    }
}
