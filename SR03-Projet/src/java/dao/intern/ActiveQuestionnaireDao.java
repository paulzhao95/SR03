package dao.intern;

import dao.DaoException;
import model.Choice;
import model.Question;
import model.Questionnaire;
import model.Topic;

import java.util.ArrayList;
import java.util.List;

public interface ActiveQuestionnaireDao {
    public ArrayList<Topic> getTopics() throws DaoException;

    public ArrayList<Questionnaire> getActiveQuestionnairesByTopic(String topic) throws DaoException;

    public ArrayList<Question> getActiveQuestions(String topic, int questionnaireId) throws DaoException;

}
