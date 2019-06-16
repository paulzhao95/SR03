package action.administrator;

import action.GlobalVariable;
import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Attempt;
import model.Question;
import model.Questionnaire;
import postgresqlImpl.QuestionnaireImpl;
import postgresqlImpl.administrator.AttemptImpl;
import postgresqlImpl.QuestionImpl;

import java.util.ArrayList;

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
    private QuestionImpl questionImpl = DaoFactory.getDaoFactoryInstance().getQuestionImpl();


    private int pageNumber = 1;
    private int limit = GlobalVariable.NUMBER_PER_PAGE;

    private int attemptNumber = 0;

    private ArrayList<Question> questions = new ArrayList<Question>();

    private String userEmailSearched = "";
    private String questionnaireNameSearched = "";
    private String topicNameSearched = "";



    public AttemptAction() throws DaoException {
    }

    public String getAttemptsByUser() {
        if (questionnaireNameSearched.equals("") | topicNameSearched.equals("")) {
            try {
                attempts = attemptImpl.getAttempts(email, (pageNumber - 1) * limit, limit);
            } catch (DaoException e) {
                return ERROR;
            }
        } else {
            try {
                attempts = attemptImpl.getAttemptsByUserByQuestionnaire(email, topicNameSearched, questionnaireNameSearched);
            } catch (DaoException e) {
                return ERROR;
            }
        }

        return SUCCESS;
    }

    public String getAttemptsByQuestionnaire() {
        if (userEmailSearched.equals("")) {
            try {
                attempts = attemptImpl.getAttemptsByQuestionnaire(topic, questionnaireId, (pageNumber - 1) * limit, limit);
            } catch (DaoException e) {
                return ERROR;
            }
        } else {
            try {
                attempts = attemptImpl.getAttemptsByQuestionnaireByUser(topic, questionnaireId, userEmailSearched);
            } catch (DaoException e) {
                return ERROR;
            }
        }


        return SUCCESS;


    }

    // TODO: 6/15/19 check whether work or not
    public String getAttemptInfo() {
        try {
            attempt = attemptImpl.getAttempt(attemptId,(pageNumber - 1) * limit, limit);
            String topicName = attempt.getTopicName();
            int questionnaireId = attempt.getQuestionnaireId();

            questions = questionImpl.getQuestions(topicName, questionnaireId, (pageNumber - 1) * limit, limit);
//            questionnaire = questionnaireImpl.getQuestionnaire(topicName, questionnaireId);

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

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getQuestionnaireNameSearched() {
        return questionnaireNameSearched;
    }

    public void setQuestionnaireNameSearched(String questionnaireNameSearched) {
        this.questionnaireNameSearched = questionnaireNameSearched;
    }

    public String getUserEmailSearched() {
        return userEmailSearched;
    }

    public void setUserEmailSearched(String userEmailSearched) {
        this.userEmailSearched = userEmailSearched;
    }

    public String getTopicNameSearched() {
        return topicNameSearched;
    }

    public void setTopicNameSearched(String topicNameSearched) {
        this.topicNameSearched = topicNameSearched;
    }
}
