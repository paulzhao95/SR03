package dao;

import dao.DaoException;
import model.Attempt;

import java.util.ArrayList;

public interface AttemptDao {
    Attempt getAttempt( int evaluationID) throws DaoException;

    ArrayList<Attempt> getAttempts(String userEmail,int offset, int limit) throws DaoException;

    void deleteAttempt(Attempt attempt) throws DaoException;

    void deleteAttempts(String email) throws DaoException;

}
