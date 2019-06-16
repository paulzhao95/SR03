package postgresqlImpl;

import dao.DaoException;
import dao.AttemptDao;
import dao.DaoFactory;
import model.Attempt;
import model.Choice;

import java.sql.*;
import java.util.ArrayList;

public class AttemptImpl implements AttemptDao {

    protected DaoFactory daoFactory;

    public AttemptImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Attempt> getAttempts(String email, int offset, int limit ) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<Attempt> attempts = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select e.attempt_id as attempt_id, " +
                    "e.user_email as email, " +
                    "q.name as questionnaire_name," +
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.start_time as start_time," +
                    "e.score as score ," +
                    "e.full_marks as full_marks " +
                    "from " +
                    "(select * from attempts where user_email = ? offset ? limit ? ) as e join questionnaires q " +
                    "on e.topic = q.topic " +
                    "and e.questionnaire_id = q.number ");

            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int attempt_id = resultSet.getInt("attempt_id");
                int questionnaire_id = resultSet.getInt("questionnaire_id");
                String questionnaire_name = resultSet.getString("questionnaire_name");
                String topic = resultSet.getString("questionnaire_topic");
                int duration = resultSet.getInt("duration");
                Timestamp date = resultSet.getTimestamp("start_time");
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
    public Attempt getAttempt(int attemptId , int offset, int limit  ) throws DaoException {
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
            preparedStatement = connection.prepareStatement("select " +
                    "a.start_time as start_time, " +
                    "a.user_email as user_email, " +
                    "a.topic as topic, " +
                    "a.questionnaire_id as questionnaire_id, " +
                    "a.duration as duration, " +
                    "a.score as score, " +
                    "a.full_marks as full_marks," +
                    "uc.question_id as question_id , " +
                    "uc.choice_id as choice_id , " +
                    "uc.type as choice_type "+
                    "from user_choices uc join attempts a " +
                    "on uc.attempt_id = a.attempt_id " +
                    "where a.attempt_id = ? " +
                    "order by question_id " +
                    "offset ? " +
                    "limit  ? "
            );
            preparedStatement.setInt(1, attemptId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topic = resultSet.getString("topic");
                questionnaireID = resultSet.getInt("questionnaire_id");
                startTime = resultSet.getTimestamp("start_time");
                duration = resultSet.getInt("duration");
                score = resultSet.getInt("score");
                int question_id = resultSet.getInt("question_id");
                int choice_id = resultSet.getInt("choice_id");
                boolean choice_type= resultSet.getBoolean("choice_type");


                choices.add(new Choice(topic, questionnaireID, question_id, choice_id, choice_type));

            }

            attempt.setTopicName(topic);
            attempt.setQuestionnaireId(questionnaireID);
            attempt.setQuestionnaireName(questionnaireName);
            attempt.setStartTime(startTime);
            attempt.setDurationInSeconds(duration);
            attempt.setId(attemptId);
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


    public int getAttemptNumber(String email) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement;
        int attemptNumber = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select count(*) as attempt_number from attempts " +
                    "where user_email = ? "
            );
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                attemptNumber = resultSet.getInt("attempt_number");
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
        return attemptNumber;
    }

    public ArrayList<Attempt> getAttemptsByUserByQuestionnaire(String email, String topic, String questionnaireName) throws DaoException {

        /**
         * Get attempts of specific user and ordered by score / duration for each questionnaire
         * */

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<Attempt> attempts = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select e.attempt_id as attempt_id, " +
                    "e.user_email as email, " +
                    "q.name as questionnaire_name," +
                    "e.topic as questionnaire_topic ," +
                    "e.questionnaire_id as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.start_time as start_time," +
                    "e.score as score ," +
                    "e.full_marks as full_marks " +
                    "from " +
                    "(select attempts.*, questionnaires.name from attempts join questionnaires on attempts.topic = questionnaires.topic and attempts.questionnaire_id = questionnaires.number  where user_email = ? and name = ? and questionnaires.topic = ? and questionnaires.status = TRUE) as e join questionnaires q " +
                    "on e.topic = q.topic " +
                    "and e.questionnaire_id = q.number " +

                    "order by e.questionnaire_id, e.score/e.duration desc ");

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, questionnaireName);
            preparedStatement.setString(3, topic);

//            preparedStatement.setInt(2, offset);
//            preparedStatement.setInt(3, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int attempt_id = resultSet.getInt("attempt_id");
                int questionnaire_id = resultSet.getInt("questionnaire_id");
                String questionnaire_name = resultSet.getString("questionnaire_name");
                int duration = resultSet.getInt("duration");
                Timestamp date = resultSet.getTimestamp("start_time");
                int score = resultSet.getInt("score");
                int full_marks = resultSet.getInt("full_marks");
                attempts.add(new Attempt(attempt_id, email, topic, questionnaire_id, questionnaire_name, date, duration, score, full_marks));

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




}
