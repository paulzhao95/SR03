package dao.intern;

import dao.DaoException;
import dao.DaoFactory;
import model.Attempt;

import java.util.ArrayList;

public interface AttemptDao extends dao.AttemptDao {

    void addAttempt(Attempt attempt) throws DaoException;

}
