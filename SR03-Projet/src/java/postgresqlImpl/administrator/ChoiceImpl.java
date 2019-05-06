package postgresqlImpl.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.ChoiceDao;
import model.Choice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call delete_choice(?,?,?,?)" );
            preparedStatement.setString(1,choice.getTopic());
            preparedStatement.setInt(2,choice.getQuestionnaireId());
            preparedStatement.setInt(3,choice.getQuestionId());
            preparedStatement.setInt(4, choice.getChoiceID());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not delete choice ");
            }

        } catch (SQLException e) {
            throw new DaoException("Delete choice in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void addChoice(Choice choice) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        String type = choice.getRight() ? "Right_choice" : "Wrong_choice";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call insert_choice(?,?,?,?,?)" );
            preparedStatement.setString(1,choice.getTopic());
            preparedStatement.setInt(2,choice.getQuestionnaireId());
            preparedStatement.setInt(3,choice.getQuestionId());
            preparedStatement.setString(4,choice.getDescription());
            preparedStatement.setString(5, type);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not add choice ");
            }

        } catch (SQLException e) {
            throw new DaoException("Add choice in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
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
            preparedStatement.setString(2,choice.getRight()? "Right_choice":"Wrong_Choice");
            preparedStatement.setString(2,choice.getStatus()? "Active":"Inactive");
            preparedStatement.setString(2,choice.getTopic());
            preparedStatement.setInt(3,choice.getQuestionnaireId());
            preparedStatement.setInt(4, choice.getQuestionId());
            preparedStatement.setInt(5, choice.getChoiceID());

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
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call exchange_choice_order(?,?,?,?,?) " );
            preparedStatement.setString(1,choice1.getTopic());
            preparedStatement.setInt(2,choice1.getQuestionnaireId());
            preparedStatement.setInt(3,choice1.getQuestionId());
            preparedStatement.setInt(4, choice1.getChoiceID());
            preparedStatement.setInt(4, choice2.getChoiceID());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not change choice order");
            }

        } catch (SQLException e) {
            throw new DaoException("change choice order in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

    }
}
