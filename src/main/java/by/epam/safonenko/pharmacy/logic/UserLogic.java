package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.repository.UserRepository;
import by.epam.safonenko.pharmacy.specification.FindUserByLogin;

public class UserLogic {
    private UserRepository userRepository;

    public UserLogic(){
        userRepository = new UserRepository();
    }

    public void addUser(String name, String surname, String patronymic, String sex, String mail, String login, String password){
        userRepository.add(name, surname, patronymic, sex, mail, login, password);
    }

    public boolean checkUser(String login){
        return userRepository.check(new FindUserByLogin(login));
    }
}
