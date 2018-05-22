package by.epam.safonenko.pharmacy.validator;

public class Validator {
    private static final String INITIALS_TEMPLATE = "[а-яА-Яa-zA-Z]{1,45}";
    private static final String MAIL_TEMPLATE = "[^-](([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\])|(([a-zA-Z\\-\\d]+\\.)+[a-zA-Z]{2,}))";
    private static final String LOGIN_TEMPLATE = "[a-zA-Z\\d]+([_ -]?[a-zA-Z\\d])*";
    private static final String PASSWORD_TEMPLATE = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*]).{6,20})";
    private static final String  CODE_TEMPLATE = "[\\da-zA-Z]{8}-[\\da-zA-Z]{4}-[\\da-zA-Z]{4}-[\\da-zA-Z]{4}-[\\da-zA-Z]{12}";
    private static final String CARD_ID_TEMPLATE = "\\d{1,5}";
    private static final int MAX_MAIL_LENGTH = 50;
    private static final int MAX_LOGIN_LENGTH = 20;

    public static boolean validateInitials(String input){
        if (!isEmptyOrNull(input)){
            return input.matches(INITIALS_TEMPLATE);
        }
        return false;
    }

    public static boolean validateCardId(String input){
        if (!isEmptyOrNull(input)){
            return input.matches(CARD_ID_TEMPLATE);
        }
        return false;
    }

    public static boolean validateMail(String input){
        if (input.length() > MAX_MAIL_LENGTH || isEmptyOrNull(input)){
            return false;
        }
        return input.matches(MAIL_TEMPLATE);
    }

    public static boolean validateLogin(String input){
        if (input.length() > MAX_LOGIN_LENGTH || isEmptyOrNull(input)){
            return false;
        }
        return input.matches(LOGIN_TEMPLATE);
    }

    public static boolean validatePassword(String input){
        if (isEmptyOrNull(input)){
            return false;
        }
        return input.matches(PASSWORD_TEMPLATE);
    }

    public static boolean validateCode(String input){
        if (isEmptyOrNull(input)){
            return false;
        }
        return input.matches(CODE_TEMPLATE);
    }

    private static boolean isEmptyOrNull(String input){
        boolean result;
        result = (input == null || input.trim().isEmpty()) ? true: false;
        return result;
    }
}
