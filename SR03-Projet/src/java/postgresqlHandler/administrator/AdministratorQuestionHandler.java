package postgresqlHandler.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.QuestionDao;
import model.Choice;
import model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministratorQuestionHandler extends postgresqlHandler.QuestionHandler implements QuestionDao {

    public AdministratorQuestionHandler(DaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Question getQuestion(String topic , int questionnaireId, int questionNumber) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        Question question;

        String questionDescription = "";
        Boolean questionStatus = true;
        ArrayList<Choice> choices = new ArrayList<Choice>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select Q.Topic as topic,  " +
                    "Q.Questionnaire_Id as Questionnaire_Id, " +
                    "Q.Number as question_Id, " +
                    "Q.Description as question_description, " +
                    "Q.Status as question_status, " +
                    "C.Number as choice_ID, C.Description as choice_description, " +
                    "C.Status as choice_status, " +
                    "C.Type as choice_type   " +
                    "from " +
                    "Questions Q join Choices C  " +
                    "on Q.Topic = C.Topic " +
                    "and Q.Questionnaire_Id = C.Questionnaire_Id " +
                    "and Q.Number = C.Question_Id  " +
                    "where " +
                    "Q.Topic = ? " +
                    "and Q.Questionnaire_Id  = ? " +
                    "and Q.Number = ? ");
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(1,questionnaireId);
            preparedStatement.setInt(1,questionNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                questionDescription = resultSet.getString("question_description");
                questionStatus = resultSet.getString("question_status").equals("Active");

                int choiceId = resultSet.getInt("choice_id");
                String choiceDescriptin = resultSet.getString("choice_description");
                boolean choiceStatus = resultSet.getString("choice_status").equals("Active");
                boolean choiceRight = resultSet.getString("choice_type").equals("Right_choice");

                choices.add(new Choice(topic, questionnaireId, questionNumber, choiceId, choiceDescriptin, choiceStatus, choiceRight));
            }

            question = new Question(questionNumber, questionDescription, questionStatus, choices);
        } catch (SQLException e) {
            throw new DaoException("get  question in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
        return question;
    }

    @Override
    public ArrayList<Question> getQuestions(String topic, int questionnaireId) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        int preQuestionId = -1;
        String questionDescription = "";
        Boolean questionStatus = true;
        ArrayList<Choice> choices = new ArrayList<>();
        int index = 0;

        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select Q.Number as question_Id, " +
                            "Q.Description as question_description, " +
                            "Q.Status as question_status, " +
                            "C.Number as choice_ID, " +
                            "C.Description as choice_description, " +
                            "C.Status as choice_status, " +
                            "C.Type as choice_type   " +
                            "from " +
                            "Questions Q join Choices C  " +
                            "on Q.Topic = C.Topic " +
                            "and Q.Questionnaire_Id = C.Questionnaire_Id " +
                            "and Q.Number = C.Question_Id  " +
                            "where Q.Topic = ? " +
                            "and Q.Questionnaire_Id  = ? "
            );

            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int question_id = resultSet.getInt("question_id");
                questionDescription = resultSet.getString("question_description");
                questionStatus = resultSet.getString("question_status").equals("Active");

                int choice_id = resultSet.getInt("choice_id");
                String choice_description = resultSet.getString("choice_description");
                boolean choice_status = resultSet.getString("choice_status").equals("Active");
                boolean isRight = resultSet.getString("choice_type").equals("Right_choice");

                if (question_id != preQuestionId){
                    if (0 <= preQuestionId){
                        questions.add(new Question(preQuestionId,questionDescription, questionStatus, choices));
                        choices.clear();
                    }
                    index++;
                    preQuestionId = question_id;
                }
                choices.add(new Choice(topic,questionnaireId,question_id, choice_id,choice_description,choice_status,isRight));
            }
            questions.add(new Question(preQuestionId, questionDescription,questionStatus,choices));

        } catch (SQLException e) {
            throw new DaoException("Get questions  tfrom database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return questions;
    }


    @Override
    public void addQuestion(Question question) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;


        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call insert_question(?,?,?)" );
            preparedStatement.setString(1,question.getTopic());
            preparedStatement.setInt(2,question.getQuestionnaireId());
            preparedStatement.setString(3,question.getDescription());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not add question");
            }

        } catch (SQLException e) {
            throw new DaoException("Add question in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void deleteQuestion(Question question) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call delete_question(?,?,?)" );
            preparedStatement.setString(1,question.getTopic());
            preparedStatement.setInt(2,question.getQuestionnaireId());
            preparedStatement.setInt(3,question.getQuestionID());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not delete question");
            }

        } catch (SQLException e) {
            throw new DaoException("Delete question in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void updateQuestion(Question question) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update table questions " +
                    "set description = ? " +
                    "where topic = ? " +
                    "and questionnaire_id = ? " +
                    "and number = ? " );
            preparedStatement.setString(1,question.getDescription());
            preparedStatement.setString(2,question.getTopic());
            preparedStatement.setInt(3,question.getQuestionnaireId());
            preparedStatement.setInt(4, question.getQuestionID());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not change question description");
            }

        } catch (SQLException e) {
            throw new DaoException("change question description in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void exchangeQuestions(Question question1, Question question2) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call exchange_question_order(?,?,?,?) " );
            preparedStatement.setString(1,question1.getTopic());
            preparedStatement.setInt(2,question1.getQuestionnaireId());
            preparedStatement.setInt(3,question1.getQuestionID());
            preparedStatement.setInt(4, question2.getQuestionID());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not change question order");
            }

        } catch (SQLException e) {
            throw new DaoException("change question order in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }




}
