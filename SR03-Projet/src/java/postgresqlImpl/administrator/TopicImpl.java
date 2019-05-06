package postgresqlImpl.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.TopicDao;
import model.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TopicImpl extends postgresqlImpl.TopicImpl implements TopicDao {

    public TopicImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public void addTopic(Topic topic) throws DaoException {
        String topicName = topic.getName();
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("insert into Topics values (?)");
            preparedStatement.setString(1,topicName);
            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not insert topic");
            }

        } catch (SQLException e) {
            throw new DaoException("Add topics to database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void deleteTopic(Topic topic) throws DaoException {
        String topicName = topic.getName();
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("delete from Topics where Topic = ?");
            preparedStatement.setString(1, topicName);
            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not delete topic");
            }

        } catch (SQLException e) {
            throw new DaoException("Add topics to database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

    }

    @Override
    public void updateTopic(String oldTopicName, String newTopicName) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update Topics set Topic = ? where Topic = ?" );
            preparedStatement.setString(1,oldTopicName);
            preparedStatement.setString(1,newTopicName);
            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not update topic");
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

    }
    
}
