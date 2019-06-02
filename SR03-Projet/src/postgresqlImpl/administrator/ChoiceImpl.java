package postgresqlImpl.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.ChoiceDao;
import model.Choice;

import java.sql.*;
import java.util.ArrayList;

public class ChoiceImpl extends postgresqlImpl.ChoiceImpl implements ChoiceDao {


    public ChoiceImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }


    @Override
    public Choice getChoice(String topic, int questionnaireId, int questionId, int choiceId) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        Choice choice = new Choice();

        choice.setTopic(topic);
        choice.setQuestionnaireId(questionnaireId);
        choice.setQuestionId(questionId);
        choice.setChoiceId(choiceId);

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select description, " +
                    "status, " +
                    "type " +
                    "from choices " +
                    "where topic = ? " +
                    "and questionnaire_id = ? " +
                    "and question_id = ? " +
                    "and number = ? "
            );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionId);
            preparedStatement.setInt(4, choiceId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                choice.setDescription(resultSet.getString("description"));
                choice.setStatus(resultSet.getString("status").equals("Active"));
                choice.setIsRight(resultSet.getString("type").equals("Right_choice"));
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
                    "and question_id = ? "
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

    @Override
    public void deleteChoice(Choice choice) throws DaoException {
        Connection connection;
        CallableStatement callableStatement;
        try {
            connection = daoFactory.getConnection();
            callableStatement = connection.prepareCall("call delete_choice(?,?,?,?)" );
            callableStatement.setString(1,choice.getTopic());
            callableStatement.setInt(2,choice.getQuestionnaireId());
            callableStatement.setInt(3,choice.getQuestionId());
            callableStatement.setInt(4, choice.getChoiceId());

            callableStatement.executeUpdate();
            connection.commit();


        } catch (SQLException e) {
            throw new DaoException("Delete choice in database failed :) " + e.getMessage());
        }

        try {
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }
    // todo correct
    @Override
    public void addChoice(Choice choice) throws DaoException {
        Connection connection;
        CallableStatement callableStatement;
        String type = choice.getIsRight() ? "Right_choice" : "Wrong_choice";

        try {
            connection = daoFactory.getConnection();
            callableStatement = connection.prepareCall("call insert_choice(?,?,?,?,?)" );
            callableStatement.setString(1,choice.getTopic());
            callableStatement.setInt(2,choice.getQuestionnaireId());
            callableStatement.setInt(3,choice.getQuestionId());
            callableStatement.setString(4,choice.getDescription());
            callableStatement.setString(5, type);

            int i = callableStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            throw new DaoException("Add choice in database failed :) " + e.getMessage());
        }

        try {
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void updateChoice(Choice choice) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update  choices " +
                    "set description = ? ," +
                    "type = ? ," +
                    "status = ? "+
                    "where topic = ? " +
                    "and questionnaire_id = ? " +
                    "and question_id = ? " +
                    "and number = ?" );
            preparedStatement.setString(1,choice.getDescription());
            preparedStatement.setString(2,choice.getIsRight()? "Right_choice":"Wrong_Choice");
            preparedStatement.setString(2,choice.getStatus()? "Active":"Inactive");
            preparedStatement.setString(2,choice.getTopic());
            preparedStatement.setInt(3,choice.getQuestionnaireId());
            preparedStatement.setInt(4, choice.getQuestionId());
            preparedStatement.setInt(5, choice.getChoiceId());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not change choice description");
            }

        } catch (SQLException e) {
            throw new DaoException("change choice description in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void exchangeChoices(Choice choice1, Choice choice2) throws DaoException {
        Connection connection;
        CallableStatement callableStatement;

        try {
            connection = daoFactory.getConnection();
            callableStatement = connection.prepareCall("call exchange_choice_order(?,?,?,?,?) " );
            callableStatement.setString(1,choice1.getTopic());
            callableStatement.setInt(2,choice1.getQuestionnaireId());
            callableStatement.setInt(3,choice1.getQuestionId());
            callableStatement.setInt(4, choice1.getChoiceId());
            callableStatement.setInt(4, choice2.getChoiceId());

            callableStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            throw new DaoException("change choice order in database failed :) " + e.getMessage());
        }

        try {
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

    }
}
