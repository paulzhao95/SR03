package postgresqlImpl;

import dao.DaoException;
import dao.DaoFactory;
import dao.QuestionnaireDao;
import model.Choice;
import model.Question;
import model.Questionnaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionnaireImpl implements QuestionnaireDao {

    protected DaoFactory daoFactory;

    public QuestionnaireImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;}

    @Override
    public ArrayList<Questionnaire> getQuestionnaires(String topic, int offset, int limit ) throws DaoException {
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
                            "where Q.Topic = ? " +
                            "and Q.status = TRUE " +
                            "offset ? " +
                            "limit  ?"
            );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

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

    public ArrayList<Questionnaire> getQuestionnairesByName(String topic, String questionnaireName) throws DaoException {
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
                            "where Q.Topic = ? " +
                            "and Q.status = TRUE " +
                            "and Q.name = ?"
            );
            preparedStatement.setString(1,topic);
            preparedStatement.setString(2, questionnaireName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String topicName = resultSet.getString("topic");
                int questionnaireId = resultSet.getInt("number");
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

        int preQuestionId = -1;
        String questionDescription = "";
        Boolean questionStatus = true;
        ArrayList<Choice> choices = new ArrayList<>();
        int index = 0;

        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select qs.name as questionnaire_name, Q.Number as question_Id, qs.status as questionnaire_status ," +
                            "Q.Description as question_description, " +
                            "Q.Status as question_status, " +
                            "C.Number as choice_ID, " +
                            "C.Description as choice_description, " +
                            "C.Status as choice_status, " +
                            "C.Type as choice_type   " +
                            "from questionnaires qs join questions q on qs.topic = q.topic and qs.number = q.questionnaire_id join choices c on q.topic = c.topic and q.questionnaire_id = c.questionnaire_id and q.number = c.question_id " +
                            "where qs.topic = ? and qs.number = ? " +
                            "and qs.status = true and q.status = true "
            );

            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int question_id = resultSet.getInt("question_id");
                questionnaireName = resultSet.getString("questionnaire_name");
                questionnaireStatus = resultSet.getBoolean("questionnaire_status");
                if (question_id != preQuestionId){
                    if (0 <= preQuestionId){
                        questions.add(new Question(topic,questionnaireId,  preQuestionId,questionDescription, questionStatus, new ArrayList<Choice>(choices)));
                        choices.clear();
                    }
                    index++;
                    preQuestionId = question_id;
                }

                questionDescription = resultSet.getString("question_description");
                questionStatus = resultSet.getString("question_status").equals("Active");

                int choice_id = resultSet.getInt("choice_id");
                String choice_description = resultSet.getString("choice_description");
                boolean choice_status = resultSet.getString("choice_status").equals("Active");
                boolean isRight = resultSet.getBoolean("choice_type");


                choices.add(new Choice(topic,questionnaireId,question_id, choice_id,choice_description,choice_status,isRight));
            }
            questions.add(new Question(topic, questionnaireId, preQuestionId, questionDescription,questionStatus,choices));

        } catch (SQLException e) {
            throw new DaoException("Get questions  tfrom database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return new Questionnaire(questionnaireId, topic, questionnaireName,questionnaireStatus,questions);

    }


    public int getQuestionnaireCount(String topic) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        int questionnaireCount = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select count(*) as questionnaire_number from questionnaires where topic = ? and status = TRUE");
            preparedStatement.setString(1, topic);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                questionnaireCount = resultSet.getInt("questionnaire_number");
            }




        } catch (SQLException e) {
            throw new DaoException("Delete questionnaire in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
        return questionnaireCount;
    }

}
