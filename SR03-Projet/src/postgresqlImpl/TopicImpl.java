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
    public ArrayList<Topic> getTopics() throws DaoException {


        Connection connection ;
        PreparedStatement preparedStatement ;
        ArrayList<Topic> topics = new ArrayList<Topic>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select  * from topics");

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

}
