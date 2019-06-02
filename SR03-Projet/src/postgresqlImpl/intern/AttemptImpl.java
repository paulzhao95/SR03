package postgresqlImpl.intern;

import dao.DaoException;
import dao.DaoFactory;
import dao.intern.AttemptDao;
import model.Attempt;
import model.Choice;

import java.sql.*;

public class AttemptImpl extends postgresqlImpl.AttemptImpl implements AttemptDao {


    public AttemptImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }


    @Override
    public void addAttempt(Attempt attempt) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("INSERT into attempts (topic, questionnaire_id, user_email, duration, start_time, score, full_marks) " +
                    "values " +
                    "(?,?,?,?,?,?,?)");
            preparedStatement.setString(1, attempt.getTopicName());
            preparedStatement.setInt(2, attempt.getQuestionnaireId());
            preparedStatement.setString(3, attempt.getUserEmail());
            preparedStatement.setInt(4, attempt.getDurationInSeconds());
            preparedStatement.setTimestamp(5,attempt.getStartTime());
            preparedStatement.setInt(6, attempt.getScore());
            preparedStatement.setInt(7, attempt.getFullMarks());

            int i = preparedStatement.executeUpdate();
            connection.commit();

            // get attempt id from data base

            preparedStatement = connection.prepareStatement("select attempt_id from attempts where user_email = ? and start_time = ?");
            preparedStatement.setString(1, attempt.getUserEmail());
            preparedStatement.setTimestamp(2, attempt.getStartTime());


            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                attempt.setId(resultSet.getInt("attempt_id"));
            }

            for (Choice choice : attempt.getUserChoices()) {
                preparedStatement = connection.prepareStatement("insert into user_choices (attempt_id, topic, questionnaire_id, question_id, choice_id, type) " +
                        "values (?,?,?,?,?,?)");

                preparedStatement.setInt(1, attempt.getId());
                preparedStatement.setString(2, attempt.getTopicName());
                preparedStatement.setInt(3, attempt.getQuestionnaireId());
                preparedStatement.setInt(4,choice.getQuestionId());
                preparedStatement.setInt(5, choice.getChoiceId());
                preparedStatement.setBoolean(6, choice.getIsRight());

                preparedStatement.executeUpdate();
                connection.commit();

            }

            if (i == 0){
                throw new DaoException("Can not add attempt");
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Add question in database failed :) " + e.getMessage());
        }

    }
}
