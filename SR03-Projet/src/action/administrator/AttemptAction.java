package action.administrator;

import action.GlobalVariable;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.javaws.exceptions.ErrorCodeResponseException;
import dao.DaoException;
import dao.DaoFactory;
import model.Attempt;
import model.Question;
import model.Questionnaire;
import postgresqlImpl.QuestionnaireImpl;
import postgresqlImpl.administrator.AttemptImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class AttemptAction extends ActionSupport {

    private String topic;
    private int questionnaireId;
    private Questionnaire questionnaire;
    private Attempt attempt = new Attempt();
    private ArrayList<Attempt> attempts = new ArrayList<Attempt>();
    private int attemptId;

    private String email = "";

    private QuestionnaireImpl questionnaireImpl = DaoFactory.getDaoFactoryInstance().getQuestionnaireImpl();
    private AttemptImpl attemptImpl = DaoFactory.getDaoFactoryInstance().getAdministratorAttemptImpl();

    private int pageNumber = 1;
    private int limit = GlobalVariable.NUMBER_PER_PAGE;


    public AttemptAction() throws DaoException {
    }

    public String getAttemptsByUser() {
        try {
            attempts = attemptImpl.getAttempts(email, (pageNumber - 1) * limit, limit);
        } catch (DaoException e) {
            return ERROR;
        }

        return SUCCESS;
    }

    public String getAttemptsByQuestionnaire() {
        try {
            attempts = attemptImpl.getAttemptsByQuestionnaire(topic, questionnaireId, (pageNumber - 1) * limit, limit);
        } catch (DaoException e) {
            return ERROR;
        }

        return SUCCESS;


    }

    public String getAttemptInfo() {
        try {
            attempt = attemptImpl.getAttempt(attemptId);
            String topicName = attempt.getTopicName();
            int questionnaireId = attempt.getQuestionnaireId();

            questionnaire = questionnaireImpl.getQuestionnaire(topicName, questionnaireId);

        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;

    }

    public void setAttempts(ArrayList<Attempt> attempts) {
        this.attempts = attempts;
    }

    public ArrayList<Attempt> getAttempts() {
        return attempts;
    }


    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    public Attempt getAttempt() {
        return attempt;
    }

    public void setQuestionnaire (Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }




    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

}
