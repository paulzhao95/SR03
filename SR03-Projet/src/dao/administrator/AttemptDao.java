package dao.administrator;

import dao.DaoException;
import model.Attempt;
import model.User;

import java.util.ArrayList;

public interface AttemptDao extends dao.AttemptDao {
    ArrayList<Attempt> getAttempts(String userEmail,int offset, int limit) throws DaoException;

    ArrayList<Attempt> getAttemptsByQuestionnaire(String topic, int questionnaireId, int offset, int limit ) throws DaoException;

}
