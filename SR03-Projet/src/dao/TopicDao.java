package dao;

import model.Topic;

import java.util.ArrayList;

public interface TopicDao {
    ArrayList<Topic> getTopics() throws DaoException;

}
