package dao.administrator;

import dao.DaoException;
import model.Questionnaire;
import model.Topic;

import java.util.ArrayList;

public interface TopicDao  {

    void addTopic(Topic topic) throws DaoException;

    void deleteTopic(Topic topic) throws DaoException;

    void updateTopic(String oldTopicName, String newTopicName) throws DaoException;

}
