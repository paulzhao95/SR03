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

        int preQuestionId = -1;
        String questionDescription = "";
        Boolean questionStatus = true;
        ArrayList<Choice> choices = new ArrayList<>();
        int index = 0;

        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "select qs.name as questionnaire_name, Q.Number as question_Id, qs.status as questionnaire_status ," +
                            "Q.Description as question_description, " +
                            "Q.Status as question_status, " +
                            "C.Number as choice_ID, " +
                            "C.Description as choice_description, " +
                            "C.Status as choice_status, " +
                            "C.Type as choice_type   " +
                            "from questionnaires qs join questions q on qs.topic = q.topic and qs.number = q.questionnaire_id join choices c on q.topic = c.topic and q.questionnaire_id = c.questionnaire_id and q.number = c.question_id " +
                            "where qs.topic = ? and qs.number = ? "
            );

            preparedStatement.setString(1,topic);
            preparedStatement.setInt(2,questionnaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int question_id = resultSet.getInt("question_id");
                questionnaireName = resultSet.getString("questionnaire_name");
                questionnaireStatus = resultSet.getBoolean("questionnaire_status");
                if (question_id != preQuestionId){
                    if (0 <= preQuestionId){
                        questions.add(new Question(topic, preQuestionId,questionDescription, questionStatus, new ArrayList<Choice>(choices)));
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
            questions.add(new Question(topic, preQuestionId, questionDescription,questionStatus,choices));

        } catch (SQLException e) {
            throw new DaoException("Get questions  tfrom database failed : "+e.getMessage());
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new DaoException("Database connection failed");
        }
        return new Questionnaire(questionnaireId, topic, questionnaireName,questionnaireStatus,questions);

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
            preparedStatement.setInt(3,questionnaire.getQuestionnaireId());
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
                preparedStatement.setString(1, questionnaire.getTopic());
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
                    callableStatement.setString(1, questionnaire.getTopic());
                    callableStatement.setInt(2, questionnaireId);
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
            callableStatement.setInt(2, questionnaire.getQuestionnaireId());


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
