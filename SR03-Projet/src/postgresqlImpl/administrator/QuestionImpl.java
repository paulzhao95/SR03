package postgresqlImpl.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.QuestionDao;
import jdk.nashorn.internal.codegen.CompilerConstants;
import model.Choice;
import model.Question;

import java.sql.*;
import java.util.ArrayList;

public class QuestionImpl extends postgresqlImpl.QuestionImpl implements QuestionDao {

    public QuestionImpl(DaoFactory daoFactory) {
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
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3,questionNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                questionDescription = resultSet.getString("question_description");
                questionStatus = resultSet.getBoolean("question_status");

                int choiceId = resultSet.getInt("choice_id");
                String choiceDescriptin = resultSet.getString("choice_description");
                boolean choiceStatus = resultSet.getBoolean("choice_status");
                boolean choiceRight = resultSet.getBoolean("choice_type");

                choices.add(new Choice(topic, questionnaireId, questionNumber, choiceId, choiceDescriptin, choiceStatus, choiceRight));
            }

            question = new Question(topic,questionnaireId, questionNumber, questionDescription, questionStatus, choices);
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
    public ArrayList<Question> getQuestions(String topic, int questionnaireId , int offset, int limit ) throws DaoException {
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
                            "(select * from questions where topic = ? and questionnaire_id = ? order by number  offset ? limit ?) as Q join Choices C  " +
                            "on Q.Topic = C.Topic " +
                            "and Q.Questionnaire_Id = C.Questionnaire_Id " +
                            "and Q.Number = C.Question_Id  " +
                            "order by Q.number, C.number"

            );

            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);
            preparedStatement.setInt(3, offset);
            preparedStatement.setInt(4, limit);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int question_id = resultSet.getInt("question_id");

                if (question_id != preQuestionId){
                    if (0 <= preQuestionId){
                        questions.add(new Question(topic, questionnaireId, preQuestionId,questionDescription, questionStatus, new ArrayList<Choice>(choices)));
                        choices.clear();
                    }
                    index++;
                    preQuestionId = question_id;
                }

                questionDescription = resultSet.getString("question_description");
                questionStatus = resultSet.getString("question_status").equals("Active");

                int choice_id = resultSet.getInt("choice_id");
                String choice_description = resultSet.getString("choice_description");
                boolean choice_status = resultSet.getString("choice_status").equals("Active");
                boolean isRight = resultSet.getBoolean("choice_type");


                choices.add(new Choice(topic,questionnaireId,question_id, choice_id,choice_description,choice_status,isRight));
            }
            questions.add(new Question(topic, questionnaireId, preQuestionId, questionDescription,questionStatus,choices));

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
        CallableStatement callableStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareCall("select insert_question(?,?,?,?) as question_number");
            preparedStatement.setString(1, question.getTopic());
            preparedStatement.setInt(2,question.getQuestionnaireId());
            preparedStatement.setString(3, question.getDescription());
            preparedStatement.setBoolean(4,question.getStatus());

            ResultSet resultSet = preparedStatement.executeQuery();

            connection.commit();

            int questionId = -1;
            while ( resultSet.next()) {
                questionId = resultSet.getInt("question_number");
            }

            for (Choice choice : question.getChoices()) {
                callableStatement = connection.prepareCall("call insert_choice(?,?,?,?,?,?)");
                callableStatement.setString(1, question.getTopic());
                callableStatement.setInt(2, question.getQuestionnaireId());
                callableStatement.setInt(3, questionId);
                callableStatement.setString(4, choice.getDescription());
                callableStatement.setBoolean(5, choice.getIsRight());
                callableStatement.setBoolean(6,choice.getStatus());

                callableStatement.executeUpdate();
                connection.commit();
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
        CallableStatement callableStatement;

        try {
            connection = daoFactory.getConnection();
            callableStatement = connection.prepareCall("call delete_question(?,?,?)" );
            callableStatement.setString(1,question.getTopic());
            callableStatement.setInt(2,question.getQuestionnaireId());
            callableStatement.setInt(3,question.getQuestionId());

            callableStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            throw new DaoException("Delete question in database failed :) " + e.getMessage());
        }

        try {
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    // TODO: 6/15/19 right choice does not show
    @Override
    public void updateQuestion(Question question) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update questions " +
                    "set description = ? " +
                    "where topic = ? " +
                    "and questionnaire_id = ? " +
                    "and number = ? " );
            preparedStatement.setString(1,question.getDescription());
            preparedStatement.setString(2,question.getTopic());
            preparedStatement.setInt(3,question.getQuestionnaireId());
            preparedStatement.setInt(4, question.getQuestionId());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not change question description");
            }


            preparedStatement = connection.prepareStatement("delete FROM choices where topic = ? and questionnaire_id = ? and question_id = ?");
            preparedStatement.setString(1, question.getTopic());
            preparedStatement.setInt(2, question.getQuestionnaireId());
            preparedStatement.setInt(3, question.getQuestionId());

            i = preparedStatement.executeUpdate();
            connection.commit();
            if(i == 0){
                throw new DaoException("Can not change question description");
            }

            for (Choice choice : question.getChoices()) {
                CallableStatement callableStatement = connection.prepareCall("call insert_choice(?,?,?,?,?,?)");
                callableStatement.setString(1, question.getTopic());
                callableStatement.setInt(2, question.getQuestionnaireId());
                callableStatement.setInt(3, question.getQuestionId());
                callableStatement.setString(4, choice.getDescription());
                callableStatement.setBoolean(5, choice.getIsRight());
                callableStatement.setBoolean(6,choice.getStatus());

                callableStatement.executeUpdate();
                connection.commit();
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
        CallableStatement callableStatement;
        try {
            connection = daoFactory.getConnection();
            callableStatement = connection.prepareCall("call exchange_question_order(?,?,?,?) " );
            callableStatement.setString(1,question1.getTopic());
            callableStatement.setInt(2,question1.getQuestionnaireId());
            callableStatement.setInt(3,question1.getQuestionId());
            callableStatement.setInt(4, question2.getQuestionId());

            callableStatement.executeUpdate();
            connection.commit();


        } catch (SQLException e) {
            throw new DaoException("change question order in database failed :) " + e.getMessage());
        }

        try {
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }

    public int getQuestionNumber(String topic, int questionnaireId) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        int questionNumber = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select count(*) as question_number from questions where topic = ? and questionnaire_id = ?");
            preparedStatement.setString(1, topic);
            preparedStatement.setInt(2, questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questionNumber = resultSet.getInt("question_number");
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

        return questionNumber;

    }




}
