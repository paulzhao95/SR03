package dao;

import model.Choice;

import java.util.ArrayList;

public interface ChoiceDao {

    Choice getChoice(String topic, int questionnaireId, int QuestionId, int choiceId) throws DaoException;

    ArrayList<Choice> getChoices(String topic, int questionnaireId, int QuestionId) throws DaoException;
}
