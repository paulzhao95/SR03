package dao.administrator;

import dao.DaoException;
import model.Questionnaire;
import model.Topic;

import java.util.ArrayList;

public interface TopicManagerDao {

    ArrayList<Topic> getTopics() throws DaoException;

    ArrayList<Questionnaire> getQuestionnairesByTopic (String topic) throws DaoException;

    void addTopic(String topicName) throws DaoException;
    void deleteTopic(String topicName) throws DaoException;
    void alterTopic(String oldTopicName, String newTopicName) throws DaoException;

}
