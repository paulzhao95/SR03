package postgresqlImpl.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.QuestionnaireDao;
import model.Choice;
import model.Question;
import model.Questionnaire;

import java.sql.*;
import java.util.ArrayList;

public class QuestionnaireImpl extends postgresqlImpl.QuestionnaireImpl implements QuestionnaireDao {


    public QuestionnaireImpl(DaoFactory daoFactory) {
        super(daoFactory);}


    @Override
    public ArrayList<Questionnaire> getQuestionnaires(String topic) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select  Q.Number as Number," +
                            "Q.Name as Name , " +
                            "Q.Status as Status ," +
                            "Q.Topic as Topic " +
                            "from " +
                            "Questionnaires Q " +
                            "where Q.Topic = ?"
            );
            preparedStatement.setString(1,topic);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String topicName = resultSet.getString("topic");
                int questionnaireId = resultSet.getInt("number");
                String questionnaireName = resultSet.getString("name");
                Boolean status = resultSet.getString("status").equals("Active");

                questionnaires.add(new Questionnaire(questionnaireId,topicName,questionnaireName,status));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Get topics from database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return questionnaires;
    }

    @Override
    public Questionnaire getQuestionnaire(String topic, int questionnaireId) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;
        String questionnaireName = "";
        boolean questionnaireStatus = false;
        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            connection = daoFactory.getConnection();
//            preparedStatement = connection.prepareStatement(
//                    "select  Q.Number as Questionnaire_id," +
//                            "Q.Name as Questionnaire_Name , " +
//                            "Q.Status as Questionnaire_Status ," +
//                            "Q.Topic as Topic, " +
//                            "Qs.Number as question_id, " +
//                            "Qs.Description as description ," +
//                            "Qs.Status as question_status "+
//                            "from " +
//                            "Questionnaires  Q  join Questions Qs " +
//                            "on Q.Topic = Qs.Topic " +
//                            "and Q.Number = Qs.Questionnaire_Id "+
//                            "where Q.Topic = ? " +
//                            "and Q.Number = ?"
//            );

            preparedStatement = connection.prepareStatement("select qs.number, qs.name, qs.topic, qs.status, " +
                    "q.status, q.number, q.description , " +
                    "c.number, c.status, c.type, c.description " +
                    "from questionnaires qs join questions q on qs.topic = q.topic and qs.number = q.questionnaire_id join choices c on q.topic = c.topic and q.questionnaire_id = c.questionnaire_id and q.number = c.question_id");
//            preparedStatement.setString(1,topic);
//            preparedStatement.setInt(2,questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questionnaireName = resultSet.getString("questionnaire_name");
                questionnaireStatus = resultSet.getString("questionnaire_status").equals("Active");
                int questionId = resultSet.getInt("question_id");
                String desctiption = resultSet.getString("description");
                Boolean questionStatus = resultSet.getString("question_status").equals("Active");

                questions.add(new Question(topic, questionnaireId, questionId,desctiption,questionStatus));
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
    public void updateQuestionnaire(Questionnaire questionnaire) throws DaoException {

        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("update Questionnaires " +
                    "set status = ? , " +
                    "name = ?  " +
                    "where number = ? " +
                    "and Topic = ?"                    );
            preparedStatement.setBoolean(1,questionnaire.getStatus());
            preparedStatement.setString(2, questionnaire.getName());
            preparedStatement.setInt(3,questionnaire.getQuestionnaireID());
            preparedStatement.setString(4,questionnaire.getTopic());
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
    public void addQuestionnaire( Questionnaire questionnaire) throws DaoException {
        Connection connection;
        PreparedStatement preparedStatement;
        CallableStatement callableStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement= connection.prepareStatement("select insert_questionnaire(?,?,?) as questionnaireID");
            preparedStatement.setString(1,questionnaire.getTopic());
            preparedStatement.setString(2,questionnaire.getName());
            preparedStatement.setBoolean(3,questionnaire.getStatus());

            ResultSet resultSet = preparedStatement.executeQuery();

            connection.commit();

            int questionnaireId = 0;
            while (resultSet.next()) {
                questionnaireId = resultSet.getInt("questionnaireID");
            }


            for (Question question : questionnaire.getQuestions()) {
                preparedStatement = connection.prepareCall("select insert_question(?,?,?,?) as questionID");
                preparedStatement.setString(1, question.getTopic());
                preparedStatement.setInt(2,questionnaireId);
                preparedStatement.setString(3, question.getDescription());
                preparedStatement.setBoolean(4,question.getStatus());

                resultSet = preparedStatement.executeQuery();

                connection.commit();

                int questionId = 0;
                while (resultSet.next()) {
                    questionId = resultSet.getInt("questionID");
                }

                for (Choice choice : question.getChoices()) {
                    callableStatement = connection.prepareCall("call insert_choice(?,?,?,?,?,?)");
                    callableStatement.setString(1, choice.getTopic());
                    callableStatement.setInt(2, choice.getQuestionnaireId());
                    callableStatement.setInt(3, questionId);
                    callableStatement.setString(4, choice.getDescription());
                    callableStatement.setBoolean(5, choice.getIsRight());
                    callableStatement.setBoolean(6,choice.getStatus());

                    callableStatement.executeUpdate();
                    connection.commit();
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Add questionnaire in database failed :) " + e.getMessage());
        }

        try {
            preparedStatement.close();
            if (callableStatement != null) {
                callableStatement.close();
            }
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }

    }

    @Override
    public void deleteQuestionnaire(Questionnaire questionnaire) throws DaoException {

        Connection connection;
//        PreparedStatement preparedStatement;
        CallableStatement callableStatement;

        try {
            connection = daoFactory.getConnection();
//            preparedStatement = connection.prepareStatement("call delete_questionnaire(?,?)");
//            preparedStatement.setString(2,questionnaire.getTopic());
//            preparedStatement.setInt(1,questionnaire.getQuestionnaireId());
            callableStatement = connection.prepareCall("call delete_questionnaire(?,?)");
            callableStatement.setString(1, questionnaire.getTopic());
            callableStatement.setInt(2, questionnaire.getQuestionnaireID());


//            int i = preparedStatement.executeUpdate();

            callableStatement.executeUpdate();
            connection.commit();
//            if(i == 0){
//                throw new DaoException("Can not delete questionnaire");
//            }

        } catch (SQLException e) {
            throw new DaoException("Delete questionnaire in database failed :) " + e.getMessage());
        }

        try {
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Database connection failed");
        }
    }



}
