package dao.administrator;

import dao.DaoException;
import model.Choice;
import model.Question;

import java.util.ArrayList;

public interface QuestionManagerDao {

    Question getQuestionById(String topic, int questionnaireId, int questionNumber) throws DaoException;

    ArrayList<Question> getQuestionsById(String topic, int questionnaireId) throws DaoException;

    void deleteChoice(String topic, int questionnaireId, int questionId, int choiceNumber) throws DaoException;
    void deleteQuestion(String topic, int questionnaireId, int questionNumber) throws DaoException;
    
    void setQuestionStatus(String topic, int questionnaireId, int questionNumber, Boolean status) throws DaoException;
    void setChoiceStatus(String topic, int questionnaireId, int questionId, int choiceNumber, Boolean status) throws DaoException;


    void addChoice(String topic,int questionnaireId, int questionId, String choiceValue, boolean isRight) throws DaoException;
    void addQuestion(String topic,int questionnaireId, String questionValue) throws DaoException;

    void changeChoiceType(String topic, int questionnaireId, int questionId, int choiceNumber, boolean isRight  ) throws DaoException;

    void updateChoice(String topic,int questionnaireId,int questionId, int choiceNumber, String choiceValue) throws DaoException;
    void updateQuestion(String topic,int questionnaireId, int questionId, String newQuestionValue) throws DaoException;
    
    void exchangeQuestions(String topic,int questionnaireId, int question1Number, int question2Number) throws DaoException;
    void exchangeChoices(String topic,int questionnaireId, int questionId, int choice1Number, int choice2Number) throws DaoException;
}
