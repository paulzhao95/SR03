package postgresqlHandler.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.UserManagerDao;
import model.Administrator;
import model.Attempt;
import model.Choice;
import model.Intern;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AdministratorUserHandler implements UserManagerDao {

    private DaoFactory daoFactory;

    AdministratorUserHandler(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Administrator getAdministratorByLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Administrator administrator = new Administrator();


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * from users where email = ? and type_user ='Administrator' "
            );
            preparedStatement.setString(1, login);
            ;
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String password = result.getString("password");
                Boolean status = (result.getString("Status").equals("Active"));
                String name = result.getString("Name");
                String tel = result.getString("Tel");
                String company = result.getString("Company");
                Timestamp creatingTime = result.getTimestamp("Creating_time");

                administrator.setStatus(status);
                administrator.setTel(tel);
                administrator.setName(name);
                administrator.setPwd(password);
                administrator.setCompany(company);
                administrator.setLogin(login);
                administrator.setCreatingTime(creatingTime);

            } else {
                throw new DaoException("Administrator not found.");
            }
        } catch (SQLException e) {
            throw new DaoException("Get Administrator from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return administrator;
    }

    @Override
    public Intern getIntrenByLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Intern intern = new Intern();


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * from users where email = ? and type_user ='Intern' "
            );
            preparedStatement.setString(1, login);
            ;
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String password = result.getString("password");
                Boolean status = (result.getString("Status").equals("Active"));
                String name = result.getString("Name");
                String tel = result.getString("Tel");
                String company = result.getString("Company");
                Timestamp creatingTime = result.getTimestamp("Creating_time");

                intern.setStatus(status);
                intern.setTel(tel);
                intern.setName(name);
                intern.setPwd(password);
                intern.setCompany(company);
                intern.setLogin(login);
                intern.setCreatingTime(creatingTime);

            } else {
                throw new DaoException("Intern not found.");
            }
        } catch (SQLException e) {
            throw new DaoException("Get Administrator from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return intern;
    }

    @Override
    public void addIntern(Intern intern) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("insert into Users values (?,?,?,?,?,?,NOW(),?,?)"
            );
            preparedStatement.setString(1, intern.getPwd());
            preparedStatement.setString(2, intern.getStatus() ? "Active" : "Inactive");
            preparedStatement.setString(3, intern.getName());
            preparedStatement.setString(4, intern.getCompany());
            preparedStatement.setString(5, intern.getTel());
            preparedStatement.setString(6, intern.getLogin());
            preparedStatement.setString(7, intern.getType());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not insert Intern");
            }

        } catch (SQLException e) {
            throw new DaoException("Add intern to database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

    @Override
    public void addAdministrator(Administrator administrator) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("insert into Users values (?,?,?,?,?,?,NOW(),?,?)"
            );
            preparedStatement.setString(1, administrator.getPwd());
            preparedStatement.setString(2, administrator.getStatus() ? "Active" : "Inactive");
            preparedStatement.setString(3, administrator.getName());
            preparedStatement.setString(4, administrator.getCompany());
            preparedStatement.setString(5, administrator.getTel());
            preparedStatement.setString(6, administrator.getLogin());
            preparedStatement.setString(7, administrator.getType());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not insert Administrator");
            }

        } catch (SQLException e) {
            throw new DaoException("Add Administrator from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }

    }

    @Override
    public void dropInternByLogin(String email) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("delete from Users where email = ? and type = 'Intern'"
            );
            preparedStatement.setString(1, email);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not delete intern");
            }

        } catch (SQLException e) {
            throw new DaoException("delete intern from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }


    }

    @Override
    public void dropAdministratorByLogin(String email) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("delete from Users where email = ? and type = 'Administrator'"
            );
            preparedStatement.setString(1, email);

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not delete administrator");
            }

        } catch (SQLException e) {
            throw new DaoException("delete administrator from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }

    }

    @Override
    public void modifyInternStatus(String email, boolean isActive) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("alter Users set status = ? where email = ? and type = 'Intern'");
            preparedStatement.setString(1, isActive ? "Active" : "Inactive");
            preparedStatement.setString(2, email);

            int i = preparedStatement.executeUpdate();
            connection.commit();

            if (i == 0) {
                throw new DaoException("Can not modify intern status");
            }

        } catch (SQLException e) {
            throw new DaoException("modify intern status in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

    @Override
    public void modifyAdministratorStatus(String email, boolean isActive) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("alter Users set status = ? where email = ? and type = 'Administrator'");
            preparedStatement.setString(1, isActive ? "Active" : "Inactive");
            preparedStatement.setString(2, email);

            int i = preparedStatement.executeUpdate();
            connection.commit();

            if (i == 0) {
                throw new DaoException("Can not modify administrator  status");
            }

        } catch (SQLException e) {
            throw new DaoException("modify administrator status in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

    @Override
    public void updateIntern(Intern intern) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("alter table Users set password = ?, set name = ? , set company = ?, set tel = ? where email = ? and type = 'Itern'");
            preparedStatement.setString(1, intern.getPwd());
            preparedStatement.setString(2, intern.getName());
            preparedStatement.setString(3, intern.getCompany());
            preparedStatement.setString(4, intern.getTel());
            preparedStatement.setString(5, intern.getLogin());

            int i = preparedStatement.executeUpdate();
            connection.commit();

            if (i == 0) {
                throw new DaoException("Can not modify intern ");
            }

        } catch (SQLException e) {
            throw new DaoException("modify intern in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

    @Override
    public void updateAdministrator(Administrator administrator) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("alter table Users set password = ?, set name = ? , set company = ?, set tel = ? where email = ? and type = 'Administrator'");
            preparedStatement.setString(1, administrator.getPwd());
            preparedStatement.setString(2, administrator.getName());
            preparedStatement.setString(3, administrator.getCompany());
            preparedStatement.setString(4, administrator.getTel());
            preparedStatement.setString(5, administrator.getLogin());

            int i = preparedStatement.executeUpdate();
            connection.commit();

            if (i == 0) {
                throw new DaoException("Can not modify administrator");
            }

        } catch (SQLException e) {
            throw new DaoException("modify administrator in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

    @Override
    public ArrayList<Attempt> getAttemptsByLogin(String email) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArrayList<Attempt> attempts = new ArrayList<>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select e.evaluation_id as evaluation_id, q.name as questionnaire_name,q.topic as questionnaire_topic ,q.number as questionnaire_id,e.duration as duration ,e.date as date from evaluation e join questionnaires q on e.topic = q.topic and e.questionnaire_id = q.number " +
                    "where e.user_email = ? ");
            preparedStatement.setString(1, email);


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int evaluation_id = resultSet.getInt("evaluation_id");
                int questionnaire_id = resultSet.getInt("questionnaire_id");
                String questionnaire_name = resultSet.getString("questionnaire_name");
                String topic = resultSet.getString("questionnqire_topic");
                int duration = resultSet.getInt("duration");
                Timestamp date = resultSet.getTimestamp("date");

                attempts.add(new Attempt(evaluation_id, topic, questionnaire_id, questionnaire_name, date, duration));

            }


        } catch (SQLException e) {
            throw new DaoException("modify administrator in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }

        return attempts;
    }

    @Override
    public Attempt getAttempt(String email, int evaluationId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Attempt attempt = new Attempt();

        HashMap<Integer, Choice> choices = new HashMap<Integer, Choice>();

        String topic = "";
        String questionnaireName = "";
        Timestamp startTime = new Timestamp(0);
        int questionnaireID = 0;
        int duration = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select  q.name as questionnaire_name," +
                    "q.topic as questionnaire_topic ," +
                    "q.number as questionnaire_id," +
                    "e.duration as duration ," +
                    "e.date as date, " +
                    "uc.question_id as question_id" +
                    "uc.number as choice_id, " +
                    "uc.type as choice_type " +
                    "from evaluation e join questionnaires q  " +
                    "on e.topic = q.topic and e.questionnaire_id = q.number " +
                    "join user_choice uc " +
                    "on uc.evaluation_id = e.evaluation_id  " +
                    "where e.user_email = ? and e.evaluation_id = ? "
            );
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, evaluationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                questionnaireName = resultSet.getString("questionnaire_name");
                topic = resultSet.getString("topic");
                questionnaireID = resultSet.getInt("questionnaire_id");
                startTime = resultSet.getTimestamp("date");
                duration = resultSet.getInt("duration");
                int question_id = resultSet.getInt("question_id");
                int choice_id = resultSet.getInt("choice_id");
                String choice_type = resultSet.getString("choice_type");

                choices.put(question_id, new Choice(choice_id, "", true, choice_type.equals("Right_answer")));

            }

            attempt.setTopicName(topic);
            attempt.setQuestionnaireId(questionnaireID);
            attempt.setQuestionnaireName(questionnaireName);
            attempt.setStartTime(startTime);
            attempt.setDurationInSeconds(duration);
            attempt.setId(evaluationId);
            attempt.setUserChoices(choices);


        } catch (SQLException e) {
            throw new DaoException("get attemps in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return attempt;
    }
}
