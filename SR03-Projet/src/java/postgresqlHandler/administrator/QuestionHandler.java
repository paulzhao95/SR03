package postgresqlHandler.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.QuestionManagerDao;
import model.Choice;
import model.Question;
import model.Questionnaire;
import model.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionHandler implements QuestionManagerDao {
    private DaoFactory daoFactory;

    QuestionHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;}


    @Override
    public Question getQuestionById(String topic , int questionnaireId, int questionNumber) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        Question question;

        String questionDescription = "";
        Boolean questionStatus = true;
        ArrayList<Choice> choices = new ArrayList<Choice>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select Q.Topic as topic,  Q.Questionnaire_Id as Questionnaire_Id, Q.Number as question_Id, Q.Description as question_description, Q.Status as question_status, C.Number as choice_ID, C.Description as choice_description, C.Status as choice_status, C.Type as choice_type   from " +
                    "Questions Q join Choices C  " +
                    "on Q.Topic = C.Topic and Q.Questionnaire_Id = C.Questionnaire_Id and Q.Number = C.Question_Id  " +
                    "where Q.Topic = ? and Q.Questionnaire_Id  = ?, and Q.Number = ?" );
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

                choices.add(new Choice(choiceId, choiceDescriptin, choiceStatus, choiceRight));
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
    public ArrayList<Question> getQuestionsById(String topic, int questionnaireId) throws DaoException {
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
                    "select Q.Number as question_Id, Q.Description as question_description, Q.Status as question_status, C.Number as choice_ID, C.Description as choice_description, C.Status as choice_status, C.Type as choice_type   from " +
                            "Questions Q join Choices C  " +
                            "on Q.Topic = C.Topic and Q.Questionnaire_Id = C.Questionnaire_Id and Q.Number = C.Question_Id  " +
                            "where Q.Topic = ? and Q.Questionnaire_Id  = ? "
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
                choices.add(new Choice(choice_id,choice_description,choice_status,isRight));
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
    public void addQuestion(String topic, int questionnaireId, String questionDescription) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call insert_question(?,?,?)" );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setString(3,questionDescription);

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
    public void deleteQuestion(String topic, int questionnaireId, int questionNumber) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call delete_question(?,?,?)" );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionNumber);

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
    public void setQuestionStatus(String topic, int questionnaireId, int questionNumber, Boolean status) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        String isActive = status? "Active":"Inactive";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update questions set status = ? where topic = ? and questionnaire_id = ? and number = ?" );
            preparedStatement.setString(1,isActive);
            preparedStatement.setString(2,topic);
            preparedStatement.setInt(3,questionnaireId);
            preparedStatement.setInt(4,questionNumber);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not set question status");
            }

        } catch (SQLException e) {
            throw new DaoException("Set question status in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void setChoiceStatus(String topic, int questionnaireId, int questionId, int choiceNumber, Boolean status) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        String isActive = status? "Active":"Inactive";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update choices set status = ? where topic =? and questionnaire_id = ? and question_id = ? and number = ?" );
            preparedStatement.setString(1,isActive);
            preparedStatement.setString(2,topic);
            preparedStatement.setInt(3,questionnaireId);
            preparedStatement.setInt(4,questionId);
            preparedStatement.setInt(5, choiceNumber);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not set choice type");
            }

        } catch (SQLException e) {
            throw new DaoException("Set choice type in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    @Override
    public void deleteChoice(String topic, int questionnaireId, int questionId, int choiceNumber) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call delete_choice(?,?,?,?)" );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionId);
            preparedStatement.setInt(4, choiceNumber);

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
    public void addChoice(String topic, int questionnaireId, int questionId, String choiceDescription, boolean isRight) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        String type = isRight ? "Right_choice" : "Wrong_choice";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call insert_choice(?,?,?,?,?)" );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionId);
            preparedStatement.setString(4,choiceDescription);
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
    public void changeChoiceType(String topic, int questionnaireId, int questionId, int choiceNumber, boolean isRight) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        String choiceType = isRight ? "Right_answer" : "Wrong_answer";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("alter table choices set type = ? where topic = ? and questionnaire_id = ? and question_id = ? and number = ?" );
            preparedStatement.setString(1,choiceType);
            preparedStatement.setString(2,topic);
            preparedStatement.setInt(3,questionnaireId);
            preparedStatement.setInt(4, questionId);
            preparedStatement.setInt(5, choiceNumber);

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
    public void updateChoice(String topic, int questionnaireId, int questionId, int choiceNumber, String choiceDescription) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("alter table choices set description = ? where topic = ? and questionnaire_id = ? and question_id = ? and number = ?" );
            preparedStatement.setString(1,choiceDescription);
            preparedStatement.setString(2,topic);
            preparedStatement.setInt(3,questionnaireId);
            preparedStatement.setInt(4, questionId);
            preparedStatement.setInt(5, choiceNumber);

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
    public void updateQuestion(String topic, int questionnaireId, int questionId, String questionDescription) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("alter table questions set description = ? where topic = ? and questionnaire_id = ? and number = ? " );
            preparedStatement.setString(1,questionDescription);
            preparedStatement.setString(2,topic);
            preparedStatement.setInt(3,questionnaireId);
            preparedStatement.setInt(4, questionId);

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
    public void exchangeQuestions(String topic, int questionnaireId, int question1Number, int question2Number) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call exchange_question_order(?,?,?,?) " );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,question1Number);
            preparedStatement.setInt(4, question2Number);

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

    @Override
    public void exchangeChoices(String topic, int questionnaireId, int questionId, int choice1Number, int choice2Number) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("call exchange_choice_order(?,?,?,?,?) " );
            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionId);
            preparedStatement.setInt(4, choice1Number);
            preparedStatement.setInt(4, choice2Number);

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
