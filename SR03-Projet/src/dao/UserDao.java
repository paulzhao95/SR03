package dao;

import model.User;


public interface UserDao {
    User getUser(String login, String password, String typeUser) throws DaoException;

}


