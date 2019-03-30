package dao.intern;

import dao.DaoException;
import model.Attempt;
import model.Choice;
import model.Question;

import java.util.ArrayList;

public interface ShowEvaluationDao {
    public ArrayList<Attempt> getAttempts(String email) throws DaoException;
    public Attempt getAttempt(String email, int attemptId) throws DaoException;
    public ArrayList<Question> getQuestions(int questionnaireId) throws DaoException;
}
