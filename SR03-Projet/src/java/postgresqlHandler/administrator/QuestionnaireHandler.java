package postgresqlHandler.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.QuestionnaireManagerDao;
import model.Question;
import model.Questionnaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionnaireHandler implements QuestionnaireManagerDao {

    private DaoFactory daoFactory;

    public QuestionnaireHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;}

    @Override
    public Questionnaire getQuestionnaireById(String topic, int questionnaireId) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        String questionnaireName = "";
        boolean questionnaireStatus = false;
        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select  Q.Number as Questionnaire_id,Q.Name as Questionnaire_Name , Q.Status as Questionnaire_Status ,Q.Topic as Topic, " +
                            " Qs.Number as question_id, Qs.Description as description ,Qs.Status as question_status "+
                            "from Questionnaires  Q  join Questions Qs " +
                            "on Q.Topic = Qs.Topic and Q.Number = Qs.Questionnaire_Id "+
                            "where Q.Topic = ? and Q.Number = ?"
            );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questionnaireName = resultSet.getString("questionnaire_name");
                questionnaireStatus = resultSet.getString("questionnaire_status").equals("Active");
                int questionId = resultSet.getInt("question_id");
                String desctiption = resultSet.getString("description");
                Boolean questionStatus = resultSet.getString("question_status").equals("Active");

                questions.add(new Question(questionId,desctiption,questionStatus));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Get Questionnaire from database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return new Questionnaire(questionnaireId, topic, questionnaireName, questionnaireStatus, questions);

    }

    @Override
    public void changeQuestionnaireStatus(int questionnaireId, String topic, Boolean status) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update Questionnaires set Status = ? where Number = ? and Topic = ?" );
            preparedStatement.setString(1,status? "Active":"Inactive");
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setString(3,topic);
            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not change questionnaire status");
            }

        } catch (SQLException e) {
            throw new DaoException("Change questionnaire status in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

    }

    @Override
    public void addQuestionnaire( String topic, String name) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call insert_questionnaire(?,?)" );
            preparedStatement.setString(2,topic);
            preparedStatement.setString(3,name);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not add questionnaire");
            }

        } catch (SQLException e) {
            throw new DaoException("Add questionnaire in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

    }

    @Override
    public void deleteQuestionnaire(int questionnaireId ,String topic) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call delete_questionnaire(?,?)");
            preparedStatement.setString(2,topic);
            preparedStatement.setInt(1,questionnaireId);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not delete questionnaire");
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
    }

    @Override
    public void alterQuestionnaireName(String oldQuestionnaireName, String newQuestionnaireName) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update Questionnaires set Name = ? where Name = ?");
            preparedStatement.setString(1, newQuestionnaireName);
            preparedStatement.setString(2, oldQuestionnaireName);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not alter questionnaire name ");
            }

        } catch (SQLException e) {
            throw new DaoException("Alter questionnaire in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }


}
