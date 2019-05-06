package dao.administrator;

import dao.DaoException;
import model.Question;
import model.Questionnaire;
import model.Topic;

import java.util.ArrayList;

public interface QuestionnaireDao extends dao.QuestionnaireDao {

    void updateQuestionnaire(Questionnaire questionnaire) throws DaoException;

    void addQuestionnaire(String topic, String name) throws DaoException;

    void deleteQuestionnaire(Questionnaire questionnaire) throws DaoException;


}

