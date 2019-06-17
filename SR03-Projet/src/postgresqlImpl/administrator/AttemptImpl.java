package postgresqlImpl.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.AttemptDao;
import model.Attempt;

import java.sql.*;
import java.util.ArrayList;

public class AttemptImpl extends postgresqlImpl.AttemptImpl implements AttemptDao {

    public AttemptImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public ArrayList<Attempt> getAttempts(String email, int offset, int limit) throws DaoException {
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
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.start_time as start_time," +
                    "e.score as score ," +
                    "e.full_marks as full_marks " +
                    "from " +
                    "(select * from attempts where user_email = ? offset ? limit ? ) as e join questionnaires q " +
                    "on e.topic = q.topic " +
                    "and e.questionnaire_id = q.number " +

                    "order by e.questionnaire_id, e.score/e.duration desc ");

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
                    "(select attempts.*, questionnaires.name from attempts join questionnaires on attempts.topic = questionnaires.topic and attempts.questionnaire_id = questionnaires.number  where user_email = ? and name = ? and questionnaires.topic = ? ) as e join questionnaires q " +
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


    @Override
    public ArrayList<Attempt> getAttemptsByQuestionnaire(String topic, int questionnaireId, int offset, int limit ) throws DaoException {
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
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.start_time as start_time," +
                    "e.score as score ," +
                    "e.full_marks as full_marks " +
                    "from " +
                    "(select * from attempts where topic = ? and questionnaire_id = ? offset ? limit ? ) as e join questionnaires q " +
                    "on e.topic = q.topic " +
                    "and e.questionnaire_id = q.number " +
                    "order by  e.score/e.duration desc");

            preparedStatement.setString(1, topic);
            preparedStatement.setInt(2, questionnaireId);
            preparedStatement.setInt(3, offset);
            preparedStatement.setInt(4, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int attempt_id = resultSet.getInt("attempt_id");
                String email = resultSet.getString("email");
                String questionnaire_name = resultSet.getString("questionnaire_name");
                int duration = resultSet.getInt("duration");
                Timestamp date = resultSet.getTimestamp("start_time");
                int score = resultSet.getInt("score");
                int full_marks = resultSet.getInt("full_marks");
                attempts.add(new Attempt(attempt_id , email, topic, questionnaireId, questionnaire_name, date, duration,score,full_marks));

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

    public ArrayList<Attempt> getAttemptsByQuestionnaireByUser(String topic, int questionnaireId, String email) throws DaoException {
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
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.start_time as start_time," +
                    "e.score as score ," +
                    "e.full_marks as full_marks " +
                    "from " +
                    "(select * from attempts where topic = ? and questionnaire_id = ? and user_email = ? ) as e join questionnaires q " +
                    "on e.topic = q.topic " +
                    "and e.questionnaire_id = q.number " +
                    "order by  e.score/e.duration desc");

            preparedStatement.setString(1, topic);
            preparedStatement.setInt(2, questionnaireId);
            preparedStatement.setString(3, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int attempt_id = resultSet.getInt("attempt_id");
                String questionnaire_name = resultSet.getString("questionnaire_name");
                int duration = resultSet.getInt("duration");
                Timestamp date = resultSet.getTimestamp("start_time");
                int score = resultSet.getInt("score");
                int full_marks = resultSet.getInt("full_marks");
                attempts.add(new Attempt(attempt_id , email, topic, questionnaireId, questionnaire_name, date, duration,score,full_marks));

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
