package dao.administrator;
import dao.DaoException;
import model.Question;
import model.Questionnaire;
import model.Topic;

import java.util.ArrayList;

public interface QuestionnaireManagerDao {

    Questionnaire getQuestionnaireById(String topic, int questionnaireId) throws DaoException;

    void changeQuestionnaireStatus(int questionnaireId, String topic,Boolean status) throws DaoException;

    void addQuestionnaire(int questionnaireId , String topic, String name) throws DaoException;
    void deleteQuestionnaire(int questionnaireId ,String topic) throws DaoException;

    void alterQuestionnaireName(String oldQuestionnaireName, String newQuestionnaireName) throws DaoException;

}

