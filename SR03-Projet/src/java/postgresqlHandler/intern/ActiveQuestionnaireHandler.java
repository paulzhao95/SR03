package postgresqlHandler.intern;

import dao.DaoException;
import dao.DaoFactory;
import dao.intern.ActiveQuestionnaireDao;
import model.Choice;
import model.Question;
import model.Questionnaire;
import model.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActiveQuestionnaireHandler implements ActiveQuestionnaireDao {

    private DaoFactory daoFactory;

    ActiveQuestionnaireHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;}

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
    public ArrayList<Questionnaire> getActiveQuestionnairesByTopic(String topic) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select  Q.Number as Number,Q.Name as Name , Q.Status as Status ,Q.Topic as Topic" +
                            "from Questionnaires Q " +
                            "where Q.Topic = ? and Q.status = 'Active'"
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
    public ArrayList<Question> getActiveQuestions(String topic, int questionnaireId) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        int preQuestionId = -1;
        String questionDescription = "";
        Boolean questionStatus = true;
        ArrayList<Choice> choices = new ArrayList<>();
        int index = 0;

        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select Q.Number as question_Id, Q.Description as question_description, Q.Status as question_status, C.Number as choice_ID, C.Description as choice_description, C.Status as choice_status, C.Type as choice_type   from " +
                            "Questions Q join Choices C  " +
                            "on Q.Topic = C.Topic and Q.Questionnaire_Id = C.Questionnaire_Id and Q.Number = C.Question_Id  " +
                            "where Q.Topic = ? and Q.Questionnaire_Id  = ? and Q.status = 'Active' and C.status = 'Active'"
            );

            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int question_id = resultSet.getInt("question_id");
                questionDescription = resultSet.getString("question_description");
                questionStatus = resultSet.getString("question_status").equals("Active");

                int choice_id = resultSet.getInt("choice_id");
                String choice_description = resultSet.getString("choice_description");
                boolean choice_status = resultSet.getString("choice_status").equals("Active");
                boolean isRight = resultSet.getString("choice_type").equals("Right_choice");

                if (question_id != preQuestionId){
                    if (0 <= preQuestionId){
                        questions.add(new Question(preQuestionId,questionDescription, questionStatus, choices));
                        choices.clear();
                    }
                    index++;
                    preQuestionId = question_id;
                }
                choices.add(new Choice(choice_id,choice_description,choice_status,isRight));
            }
            questions.add(new Question(preQuestionId, questionDescription,questionStatus,choices));

        } catch (SQLException e) {
            throw new DaoException("Get questions  tfrom database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return questions;
    }

}
