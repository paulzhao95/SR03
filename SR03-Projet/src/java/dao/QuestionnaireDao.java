package dao;

import dao.DaoException;
import model.Choice;
import model.Question;
import model.Questionnaire;
import model.Topic;

import java.util.ArrayList;
import java.util.List;

public interface QuestionnaireDao {

    public ArrayList<Questionnaire> getQuestionnaires(String topic) throws DaoException;

    public Questionnaire getQuestionnaire(String topic, int questionnaireId) throws DaoException;

}
