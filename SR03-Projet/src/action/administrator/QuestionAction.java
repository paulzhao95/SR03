package action.administrator;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Question;
import org.apache.struts2.interceptor.SessionAware;
import postgresqlImpl.administrator.QuestionImpl;

import java.util.ArrayList;
import java.util.Map;

public class QuestionAction extends ActionSupport implements SessionAware {
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Question question = new Question();
    private QuestionImpl questionImpl = DaoFactory.getDaoFactoryInstance().getdAdministratorQuestionImpl();
    private String topic = "";
    private int questionnaireId = -1;
    private Boolean status = true;
    private String name = "";

    private Map<String, Object> session;

    public QuestionAction() throws DaoException {
    }

    public String get() {

        if (questionnaireId == -1) {
            topic = (String) session.get("topic");
            questionnaireId = (int)session.get("questionnaireId");
        }

        try {
            questions = questionImpl.getQuestions(topic, questionnaireId);
        } catch (DaoException e) {
            return ERROR;
        }
        topic = (String) session.get("topic");
        session.put("questionnaireId", questionnaireId);
        return SUCCESS;
    }

    // TODO: 5/31/19  test
    public String add() {
        try {
            questionImpl.addQuestion(question);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }


    public String delete() {
        try {
            questionImpl.deleteQuestion(question);
        } catch (DaoException e) {
            return ERROR;
        }

        return SUCCESS;
    }

    //todo set status failed
    public String update() {
        try {
            questionImpl.updateQuestion(question);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String getTopic() {
        if (topic.equals("")) {
            topic = (String) session.get("topic");
        }
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQuestionnaireId() {
        if (questionnaireId == -1) {
            questionnaireId = (int) session.get("questionnaireId");
        }
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        session = map;
    }
}
