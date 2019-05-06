package dao.administrator;

import dao.DaoException;
import model.Choice;
import model.Question;

import java.util.ArrayList;

public interface QuestionDao {

    void deleteQuestion(Question question) throws DaoException;
    
    void addQuestion(Question question) throws DaoException;

    void updateQuestion(Question question) throws DaoException;
    
    void exchangeQuestions(Question question1, Question question2) throws DaoException;
}
