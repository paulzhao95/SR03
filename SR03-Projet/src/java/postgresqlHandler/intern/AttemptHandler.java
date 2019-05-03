package postgresqlHandler.intern;

import dao.DaoException;
import dao.DaoFactory;
import dao.intern.AttemptDao;
import model.Attempt;

import java.sql.*;

public class AttemptHandler extends postgresqlHandler.AttemptHandler implements AttemptDao {


    public AttemptHandler(DaoFactory daoFactory) {
        super(daoFactory);
    }


    @Override
    public void addAttempt(Attempt attempt) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("INSERT into attempts (attempt_id, topic, questionnaire_id, user_email, duration, start_time, score, full_marks) " +
                    "values " +
                    "(?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, attempt.getId());
            preparedStatement.setString(2, attempt.getTopicName());
            preparedStatement.setInt(3, attempt.getQuestionnaireId());
            preparedStatement.setString(4, attempt.getUserEmail());
            preparedStatement.setInt(5, attempt.getDurationInSeconds());
            preparedStatement.setTimestamp(6,attempt.getStartTime());
            preparedStatement.setInt(7, attempt.getScore());
            preparedStatement.setInt(8, attempt.getFullMarks());


            int i = preparedStatement.executeUpdate();

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
