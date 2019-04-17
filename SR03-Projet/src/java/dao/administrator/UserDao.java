package dao.administrator;

import dao.DaoException;
import model.Administrator;
import model.Attempt;
import model.Intern;
import model.User;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

public interface UserDao {

    Intern getIntern(String email) throws DaoException;

    Administrator getAdministrator(String email) throws  DaoException;

    ArrayList<User> getUsers() throws DaoException;

    void addIntern(Intern intern) throws DaoException;

    void addAdministrator(Administrator administrator) throws DaoException;

    void deleteIntern(Intern intern) throws DaoException;

    void deleteAdministrator (Administrator administrator) throws DaoException;

    void updateUser(User user) throws DaoException;

}

