package dao.administrator;

import dao.DaoException;
import model.Administrator;
import model.Attempt;
import model.Intern;

import java.util.ArrayList;

public interface UserManagerDao {
    Administrator getAdministratorByLogin(String login) throws DaoException;
    Intern getIntrenByLogin (String login) throws DaoException;

    void addIntern(Intern intern) throws DaoException;
    void addAdministrator(Administrator administrator) throws DaoException;

    void dropInternByLogin(String email) throws DaoException;
    void dropAdministratorByLogin(String email) throws DaoException;

    void modifyInternStatus(String email, boolean active) throws DaoException;
    void modifyAdministratorStatus(String email, boolean validate) throws DaoException;

    void updateIntern(Intern intern) throws DaoException;
    void updateAdministrator (Administrator administrator) throws DaoException;

    ArrayList<Attempt> getAttemptsByLogin(String email) throws DaoException;

    Attempt getAttempt(String email, int evaluationId) throws DaoException;

}

