package dao;

import model.Question;

import java.util.ArrayList;

public interface QuestionDao {

    Question getQuestion(String topic, int questionnaireId, int questionNumber) throws DaoException;

    ArrayList<Question> getQuestions(String topic, int questionnaireId) throws DaoException;

}
