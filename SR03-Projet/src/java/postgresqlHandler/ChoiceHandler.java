package postgresqlHandler;

import dao.DaoException;
import dao.ChoiceDao;
import dao.DaoFactory;
import model.Choice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChoiceHandler implements ChoiceDao {

    protected DaoFactory daoFactory;

    public ChoiceHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Choice getChoice(String topic, int questionnaireId, int questionId, int choiceId) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        Choice choice = new Choice();

        choice.setTopic(topic);
        choice.setQuestionnaireId(questionnaireId);
        choice.setQuestionId(questionId);
        choice.setChoiceID(choiceId);

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select description, " +
                    "status, " +
                    "type " +
                    "from choices " +
                    "where topic = ? " +
                    "and questionnaire_id = ? " +
                    "and question_id = ? " +
                    "and number = ? " +
                    "and status = 'Active'" );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionId);
            preparedStatement.setInt(4, choiceId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                choice.setDescription(resultSet.getString("description"));
                choice.setStatus(resultSet.getString("status").equals("Active"));
                choice.setRight(resultSet.getString("type").equals("Right_choice"));
            }

        } catch (SQLException e) {
            throw new DaoException("Get choice in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

        return choice;

    }

    @Override
    public ArrayList<Choice> getChoices(String topic, int questionnaireId, int questionId) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        ArrayList<Choice> choices = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select description, " +
                    "status, " +
                    "type, " +
                    "number "+
                    "from choices " +
                    "where topic = ? " +
                    "and questionnaire_id = ? " +
                    "and question_id = ? " +
                    "and status = 'Active'"
            );

            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String description = resultSet.getString("description");
                int number = resultSet.getInt("number");
                boolean isAvtive = resultSet.getString("status").equals("Active");
                boolean isRight = resultSet.getString("type").equals("Right_choice");

                choices.add(new Choice(topic, questionnaireId, questionId, number, description, isAvtive, isRight));
            }

        } catch (SQLException e) {
            throw new DaoException("Get choices from database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

        return choices;
    }


}
