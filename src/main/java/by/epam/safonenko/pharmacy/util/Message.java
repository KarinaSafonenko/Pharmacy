package by.epam.safonenko.pharmacy.util;

public final class Message {

    public enum MessageType{
        WRONG_NAME("wrongNameMessage"), WRONG_SURNAME("wrongSurnameMessage"), WRONG_PATRONYMIC("wrongPatronymicMessage"),
        WRONG_EMAIL("wrongEmailMessage"), WRONG_LOGIN("wrongLoginMessage"), WRONG_PASSWORD("wrongPasswordMessage");

        private String value;

        MessageType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static final String DUPLICATE_LOGIN = "Пользователь с таким логином уже зарегистрирован";
    public static final String INCORRECT_PASSWORD = "Некорректный пароль";
    public static final String INCORRECT_NAME = "Некорректное имя";
    public static final String INCORRECT_LOGIN = "Некорректный логин";
    public static final String INCORRECT_SURNAME = "Некорректная фамилия";
    public static final String INCORRECT_PATRONYMIC = " Некорректное отчество";
    public static final String INCORRECT_EMAIL = "Некорректная почта";
}
