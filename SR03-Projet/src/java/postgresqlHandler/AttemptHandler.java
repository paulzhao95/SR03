package postgresqlHandler;

import dao.DaoException;
import dao.AttemptDao;
import dao.DaoFactory;
import model.Attempt;
import model.Choice;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AttemptHandler implements AttemptDao {

    protected DaoFactory daoFactory;

    public AttemptHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Attempt> getAttempts(String email) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<Attempt> attempts = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select e.attempt_id as attempt_id, " +
                    "e.user_email as email, "+
                    "q.name as questionnaire_name," +
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.start_time as start_time," +
                    "e.score as score ," +
                    "e.full_marks as full_marks " +
                    "from " +
                    "attempts e join questionnaires q " +
                    "on e.topic = q.topic " +
                    "and e.questionnaire_id = q.number " +
                    "where e.user_email = ? ");

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int attempt_id = resultSet.getInt("attempt_id");
                int questionnaire_id = resultSet.getInt("questionnaire_id");
                String questionnaire_name = resultSet.getString("questionnaire_name");
                String topic = resultSet.getString("questionnqire_topic");
                int duration = resultSet.getInt("duration");
                Timestamp date = resultSet.getTimestamp("date");
                int score = resultSet.getInt("score");
                int full_marks = resultSet.getInt("full_marks");
                attempts.add(new Attempt(attempt_id , email, topic, questionnaire_id, questionnaire_name, date, duration,score,full_marks));

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
        Connection connection ;
        PreparedStatement preparedStatement ;
        Attempt attempt = new Attempt();
        ArrayList<Choice> choices = new ArrayList<Choice>();

        String topic = "";
        String questionnaireName = "";
        Timestamp startTime = new Timestamp(0);
        int questionnaireID = 0;
        int duration = 0;
        int score = 0;
        int fullMarks = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select  q.name as questionnaire_name," +
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.start_time as start_time, " +
                    "e.full_marks as full_marks, " +
                    "e.score as score, " +
                    "uc.question_id as question_id, " +
                    "uc.choice_id as choice_id, " +
                    "uc.type as choice_type ," +
                    "c.description as description," +
                    "c.status  as status " +
                    "from attempts e join questionnaires q  " +
                    "on e.topic = q.topic " +
                    "and e.questionnaire_id = q.number " +
                    "join user_choices uc " +
                    "on uc.attempt_id = e.attempt_id " +
                    "join choices c on uc.topic = c.topic and uc.questionnaire_id = c.questionnaire_id and uc.question_id = c.question_id and uc.choice_id = c.number " +
                    "where e.attempt_id= ? "
            );
            preparedStatement.setInt(1, evaluationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                questionnaireName = resultSet.getString("questionnaire_name");
                topic = resultSet.getString("topic");
                questionnaireID = resultSet.getInt("questionnaire_id");
                startTime = resultSet.getTimestamp("start_time");
                duration = resultSet.getInt("duration");
                int question_id = resultSet.getInt("question_id");
                int choice_id = resultSet.getInt("choice_id");
                String choice_type = resultSet.getString("choice_type");
                String description = resultSet.getString("description");
                boolean isActive = resultSet.getString("status").equals("Active");

                choices.add(new Choice(topic, questionnaireID, question_id, choice_id, description, isActive, choice_type.equals("Right_answer")));

            }

            attempt.setTopicName(topic);
            attempt.setQuestionnaireId(questionnaireID);
            attempt.setQuestionnaireName(questionnaireName);
            attempt.setStartTime(startTime);
            attempt.setDurationInSeconds(duration);
            attempt.setId(evaluationId);
            attempt.setUserChoices(choices);
            attempt.setScore(score);
            attempt.setFullMarks(fullMarks);


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
    public void deleteAttempt(Attempt attempt) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("delete from attempts " +
                    "where attempt_id = ? "
            );
            preparedStatement.setInt(1, attempt.getId());

            int i = preparedStatement.executeUpdate();
            if (i == 0){
                throw new DaoException("Can not delete attempt ");
            }


        } catch (SQLException e) {
            throw new DaoException("delete attempts in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

    @Override
    public void deleteAttempts(String email) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("delete from attempts " +
                    "where user_email = ? "
            );
            preparedStatement.setString(1, email);

            int i = preparedStatement.executeUpdate();
            if (i == 0){
                throw new DaoException("Can not delete attempt ");
            }

        } catch (SQLException e) {
            throw new DaoException("delete attempts in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }
}
