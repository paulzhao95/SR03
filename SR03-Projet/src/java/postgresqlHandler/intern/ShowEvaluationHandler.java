package postgresqlHandler.intern;

import dao.DaoException;
import dao.DaoFactory;
import dao.intern.ShowEvaluationDao;
import model.Attempt;
import model.Choice;
import model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowEvaluationHandler implements ShowEvaluationDao {

    private DaoFactory daoFactory ;

    public ShowEvaluationHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;}

    @Override
    public ArrayList<Attempt> getAttempts(String email) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<Attempt> attempts = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select e.evaluation_id as evaluation_id, q.name as questionnaire_name,q.topic as questionnaire_topic ,q.number as questionnaire_id,e.duration as duration ,e.date as date from evaluation e join questionnaires q on e.topic = q.topic and e.questionnaire_id = q.number " +
                    "where e.user_email = ? ");
            preparedStatement.setString(1, email);


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int evaluation_id = resultSet.getInt("evaluation_id");
                int questionnaire_id = resultSet.getInt("questionnaire_id");
                String questionnaire_name = resultSet.getString("questionnaire_name");
                String topic = resultSet.getString("questionnqire_topic");
                int duration = resultSet.getInt("duration");
                Timestamp date = resultSet.getTimestamp("date");

                attempts.add(new Attempt(evaluation_id, topic, questionnaire_id, questionnaire_name, date, duration));

            }


        } catch (SQLException e) {
            throw new DaoException("modify administrator in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }

        return attempts;

    }

    @Override
    public Attempt getAttempt(String email, int evaluationId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Attempt attempt = new Attempt();

        HashMap<Integer, Choice> choices = new HashMap<Integer, Choice>();

        String topic = "";
        String questionnaireName = "";
        Timestamp startTime = new Timestamp(0);
        int questionnaireID = 0;
        int duration = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select  q.name as questionnaire_name," +
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.date as date, " +
                    "uc.question_id as question_id" +
                    "uc.number as choice_id, " +
                    "uc.type as choice_type " +
                    "from evaluation e join questionnaires q  " +
                    "on e.topic = q.topic and e.questionnaire_id = q.number " +
                    "join user_choice uc " +
                    "on uc.evaluation_id = e.evaluation_id  " +
                    "where e.user_email = ? and e.evaluation_id = ? "
            );
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, evaluationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                questionnaireName = resultSet.getString("questionnaire_name");
                topic = resultSet.getString("topic");
                questionnaireID = resultSet.getInt("questionnaire_id");
                startTime = resultSet.getTimestamp("date");
                duration = resultSet.getInt("duration");
                int question_id = resultSet.getInt("question_id");
                int choice_id = resultSet.getInt("choice_id");
                String choice_type = resultSet.getString("choice_type");

                choices.put(question_id, new Choice(choice_id, "", true, choice_type.equals("Right_answer")));

            }

            attempt.setTopicName(topic);
            attempt.setQuestionnaireId(questionnaireID);
            attempt.setQuestionnaireName(questionnaireName);
            attempt.setStartTime(startTime);
            attempt.setDurationInSeconds(duration);
            attempt.setId(evaluationId);
            attempt.setUserChoices(choices);


        } catch (SQLException e) {
            throw new DaoException("get attemps in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return attempt;
    }


    @Override
    public ArrayList<Question> getQuestions(int questionnaireId) throws DaoException {
        return null;
    }
}
