package dao;

import model.Administrator;
import model.Intern;

public interface UserDao {
    Administrator getAdministrator(String login,String password) throws DaoException;
    Intern getIntern(String login, String password) throws DaoException;

}


