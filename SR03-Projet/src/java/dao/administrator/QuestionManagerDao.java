package dao.administrator;

import dao.DaoException;
import model.Choice;
import model.Question;

import java.util.ArrayList;

public interface QuestionManagerDao {

    Question getQuestionById(int questionnaireId, int questionNumber) throws DaoException;
    Choice getChoiceById(int questionnaireId,int questionId, int choiceNumber) throws DaoException;

    ArrayList<Question> getQuestionsById(int questionnaireId) throws DaoException;
    ArrayList<Choice> getChoicesById(int questionnaireId, int questionId, int choiceNumber) throws  DaoException;

    void deleteChoice(int questionnaireId, int questionId, int choiceNumber) throws DaoException;
    void deleteQuestion(int questionnaireId, int questionNumber) throws DaoException;
    
    void activateQuestion(int questionnaireId, int questionNumber) throws DaoException;
    void activateChoice(int questionnaireId, int questionId, int choiceNumber) throws DaoException;

    void disactivateQuestion(int questionnaireId, int questionNumber) throws DaoException;
    void disactivateChoice(int questionnaireId, int questionId, int choiceNumber) throws DaoException;
    
    void addChoice(int questionnaireId, int questionId, String choiceValue) throws DaoException;
    void addQuestion(int questionnaireId, String questionValue) throws DaoException;

    void setTrueChoice(int questionnaireId,int questionId, int choiceNumber) throws DaoException;
    void setFalseChoice(int questionnaireId,int questionId, int choiceNumber) throws DaoException;
    
    void updateChoice(int questionnaireId,int questionId, int choiceNumber, String choiceValue) throws DaoException;
    void updateQuestion(int questionnaireId, int questionId, String newQuestionValue) throws DaoException;
    
    void exchangeQuestions(int questionnaireId, int question1Number, int question2Number) throws DaoException;
    void exchangeChoices(int questionnaireId, int questionId, int choice1Number, int choice2Number) throws DaoException;
}
