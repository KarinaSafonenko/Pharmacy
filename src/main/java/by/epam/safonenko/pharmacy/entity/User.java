package by.epam.safonenko.pharmacy.entity;

import java.util.Objects;

public class User {
    private String name;
    private String surname;
    private String patronymic;
    private UserSex sex;
    private String mail;
    private String login;
    private String password;
    private UserRole role;

    public enum UserRole{
        ADMIN, CLIENT, PHARMACIST, DOCTOR
    }

    public enum UserSex{
        MALE, FEMALE
    }

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public UserSex getSex() {
        return sex;
    }

    public void setSex(UserSex sex) {
        this.sex = sex;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(patronymic, user.patronymic) &&
                sex == user.sex &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, patronymic, sex, mail, login, password, role);
    }
}
