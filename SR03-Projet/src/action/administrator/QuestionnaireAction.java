package action.administrator;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Questionnaire;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import postgresqlImpl.administrator.QuestionnaireImpl;

import java.util.ArrayList;
import java.util.Map;


public class QuestionnaireAction extends ActionSupport implements SessionAware {
    private ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
    private Questionnaire questionnaire = new Questionnaire();
    private String topic = "";

    private QuestionnaireImpl questionnaireImpl = DaoFactory.getDaoFactoryInstance().getAdministratorQuestionnaireImpl();

    private Map<String, Object> session;

    public QuestionnaireAction() throws DaoException {
    }

    public String get() {

//        String topic = ServletActionContext.getRequest().getParameter("Topic");
        if (topic.equals( "")) {
            topic = (String)session.get("topic");
        }
        try {
            questionnaires = questionnaireImpl.getQuestionnaires(topic);
        } catch (DaoException e) {
            return ERROR;
        }
        session.put("topic", topic);
        return SUCCESS;
    }

    public String add() {
        try {
            questionnaireImpl.addQuestionnaire(questionnaire);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String delete() {
        try {
            questionnaireImpl.deleteQuestionnaire(questionnaire);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String update() {
        try {
            questionnaireImpl.updateQuestionnaire(questionnaire);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;

    }


    public ArrayList<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public void setQuestionnaires(ArrayList<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
