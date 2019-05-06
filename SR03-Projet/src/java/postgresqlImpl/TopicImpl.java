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
        String preTopicName = "";

        ArrayList<Topic> topics = new ArrayList<Topic>();
        ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

        int index = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select T.Topic as Topic, " +
                            "Q.Number as Number," +
                            "Q.Name as Name " +
                            "from " +
                            "Topics T join Questionnaires Q " +
                            "on T.Topic = Q.Topic " +
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

}
