package postgresqlHandler.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.TopicManagerDao;
import model.Administrator;
import model.Questionnaire;
import model.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TopicHandler implements TopicManagerDao {

    private DaoFactory daoFactory;

    public TopicHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public ArrayList<Topic> getTopics() throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        String preTopicName = "";
        ArrayList<Topic> topics = new ArrayList<Topic>();
        ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

        int index = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select T.Topic as Topic, Q.Number as Number,Q.Name as Name from " +
                            "Topics T join Questionnaires Q " +
                            "on T.Topic = Q.Topic" +
                            "order by T.Topic , Q.Number"
            );

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String topicName = resultSet.getString("topic");
                int questionnaireId = resultSet.getInt("number");
                String questionnaireName = resultSet.getString("name");

                if (!topicName.equals(preTopicName)){
                    if (index>0){
                        topics.add(new Topic(preTopicName,questionnaires));
                        questionnaires.clear();
                    }
                    index++;
                    preTopicName = topicName;
                }
                questionnaires.add(new Questionnaire(questionnaireId,topicName,questionnaireName));
            }
            topics.add(new Topic(preTopicName, questionnaires));

            resultSet.close();
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

    @Override
    public ArrayList<Questionnaire> getQuestionnairesByTopic(String topic) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                "select  Q.Number as Number,Q.Name as Name , Q.Status as Status ,Q.Topic as Topic" +
                            "from Questionnaires Q " +
                            "where Q.Topic = ?"
            );
            preparedStatement.setString(1,topic);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String topicName = resultSet.getString("topic");
                int questionnaireId = resultSet.getInt("number");
                String questionnaireName = resultSet.getString("name");
                Boolean status = resultSet.getString("status").equals("Active");

                questionnaires.add(new Questionnaire(questionnaireId,topicName,questionnaireName,status));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Get topics from database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return questionnaires;
    }

    @Override
    public void addTopic(String topicName) throws DaoException {
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
    public void deleteTopic(String topicName) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("delete from Topics where Topic = ?");
            preparedStatement.setString(1,topicName);
            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
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
    public void alterTopic(String oldTopicName, String newTopicName) throws DaoException {
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
