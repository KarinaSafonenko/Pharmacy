package by.epam.safonenko.pharmacy.repository;

import by.epam.safonenko.pharmacy.connection.ConnectionPool;
import by.epam.safonenko.pharmacy.connection.ProxyConnection;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class UserRepository implements Repository<User> {
    private static Logger logger = LogManager.getLogger(UserRepository.class);

    private static final String INSERT_USER = "INSERT INTO pharmacy.user(login, password, mail, second_name, first_name, patronymic, sex) VALUES(?,SHA1(?),?,?,?,?,?)";

    public void add(String name, String surname, String patronymic, String sex, String mail, String login, String password){
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1,login);
            statement.setString(2,password);
            statement.setString(3,mail);
            statement.setString(4,surname);
            statement.setString(5,name);
            statement.setString(6,patronymic);
            statement.setString(7,sex);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.catching(Level.WARN, e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<User> find(Specification specification) {
        return specification.execute();
    }

    @Override
    public boolean check(Specification specification) {
        List<User> userList= find(specification);
        if (userList.isEmpty()){
            return false;
        }
        return true;
    }
}
