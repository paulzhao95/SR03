package dao.administrator;

import dao.DaoException;
import model.User;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

public interface UserDao {

    User getUser(String email) throws DaoException;

    ArrayList<User> getUsers() throws DaoException;

    void addUser(User user) throws DaoException;

    void deleteUser(User user) throws DaoException;

    void updateUser(User user) throws DaoException;

}

