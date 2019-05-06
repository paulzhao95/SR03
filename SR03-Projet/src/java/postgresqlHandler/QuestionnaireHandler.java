package postgresqlHandler;

import dao.DaoException;
import dao.DaoFactory;
import dao.QuestionnaireDao;
import model.Question;
import model.Questionnaire;
import model.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionnaireHandler implements QuestionnaireDao {

    protected DaoFactory daoFactory;

    public QuestionnaireHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;}

    @Override
    public ArrayList<Questionnaire> getQuestionnaires(String topic) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select  Q.Number as Number," +
                            "Q.Name as Name , " +
                            "Q.Status as Status ," +
                            "Q.Topic as Topic " +
                            "from " +
                            "Questionnaires Q " +
                            "where Q.Topic = ?" +
                            "and Q.status = 'Active'"
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
    public Questionnaire getQuestionnaire(String topic, int questionnaireId) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        String questionnaireName = "";
        boolean questionnaireStatus = false;
        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select  Q.Number as Questionnaire_id," +
                            "Q.Name as Questionnaire_Name , " +
                            "Q.Status as Questionnaire_Status ," +
                            "Q.Topic as Topic, " +
                            "Qs.Number as question_id, " +
                            "Qs.Description as description ," +
                            "Qs.Status as question_status "+
                            "from " +
                            "Questionnaires  Q  join Questions Qs " +
                            "on Q.Topic = Qs.Topic " +
                            "and Q.Number = Qs.Questionnaire_Id "+
                            "where Q.Topic = ? " +
                            "and Q.Number = ?" +
                            "and Q.status = 'Active' " +
                            "and Qs.status = 'Active'"
            );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questionnaireName = resultSet.getString("questionnaire_name");
                questionnaireStatus = resultSet.getString("questionnaire_status").equals("Active");
                int questionId = resultSet.getInt("question_id");
                String desctiption = resultSet.getString("description");
                Boolean questionStatus = resultSet.getString("question_status").equals("Active");

                questions.add(new Question(topic, questionnaireId, questionId,desctiption,questionStatus));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Get Questionnaire from database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return new Questionnaire(questionnaireId, topic, questionnaireName, questionnaireStatus, questions);

    }

}
