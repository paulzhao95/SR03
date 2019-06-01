package action.intern;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Attempt;
import model.Question;
import model.Questionnaire;
import model.User;
import org.apache.struts2.interceptor.SessionAware;
import postgresqlImpl.QuestionnaireImpl;

import java.util.Map;

public class AttemptAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private Questionnaire questionnaire;
    private Question question;
    private String topic;
    private int questionnaireId;
    private Attempt attempt;

    private QuestionnaireImpl questionnaireImpl = DaoFactory.getDaoFactoryInstance().getAdministratorQuestionnaireImpl();

    public AttemptAction() throws DaoException {
    }


    public String start() {
        try {
            questionnaireImpl.getQuestionnaire(topic, questionnaireId);
        } catch (DaoException e) {
            return ERROR;
        }
        User user = (User) session.get("user");


        attempt.setFullMarks(questionnaire.getQuestions().size());
        attempt.setUserEmail(user.getEmail());
        attempt.setQuestionnaireId(questionnaireId);
        attempt.setQuestionnaireName(questionnaire.getName());


        session.put("questionnaire", this.questionnaire);
        session.put("currentNumber", -1);
        session.put("attempt", this.attempt);

        return SUCCESS;
    }


    public String prepare() {
        return SUCCESS;
    }

//    public String previous() {
//        // save current question
//
//        questionnaire = (Questionnaire) session.get("questionnaire");
//    }
//
//    public String next() {
//        // save current question
//
//        // get next question
//    }
//
//    public String finish() {
//
//    }

    @Override

    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
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

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    public Attempt getAttempt() {
        return attempt;
    }


}
