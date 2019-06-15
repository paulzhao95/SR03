package postgresqlImpl;

import dao.DaoException;
import dao.DaoFactory;
import dao.TopicDao;
import model.Questionnaire;
import model.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TopicImpl implements TopicDao {

    protected DaoFactory daoFactory;

    public TopicImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public ArrayList<Topic> getTopics(int offset, int limit ) throws DaoException {


        Connection connection ;
        PreparedStatement preparedStatement ;
        ArrayList<Topic> topics = new ArrayList<Topic>();

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("select  * from topics order by  topic_id limit ? offset ? ");

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                String topicName = resultSet.getString("topic");
                topics.add(new Topic(topicName));
            }
        } catch (SQLException e) {
            throw new DaoException("Get topics from database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return topics;
    }

    public ArrayList<Topic> getTopicsByName(String topic ) throws DaoException {


        Connection connection ;
        PreparedStatement preparedStatement ;
        ArrayList<Topic> topics = new ArrayList<Topic>();

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("select  * from topics where topic = ? ");
            preparedStatement.setString(1, topic);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                String topicName = resultSet.getString("topic");
                topics.add(new Topic(topicName));
            }
        } catch (SQLException e) {
            throw new DaoException("Get topics from database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return topics;
    }

    public int getTopicCount() throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        int topicCount = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select count(*) as topic_number from topics" );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                topicCount = resultSet.getInt("topic_number");
            }


        } catch (SQLException e) {
            throw new DaoException("Update topics in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
        return topicCount;

    }


}
