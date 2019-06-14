package action.intern;

import action.GlobalVariable;
import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Choice;
import model.Question;
import model.Questionnaire;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import postgresqlImpl.QuestionnaireImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;


public class QuestionnaireAction extends ActionSupport implements SessionAware, ServletRequestAware {
    private ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
    private Questionnaire questionnaire = new Questionnaire();
    private String topic = "";
    private int questionnaireId;


    private int limit = GlobalVariable.NUMBER_PER_PAGE;
    private int pageNumber = 1;
    private int questionnaireNumber = 0;

    private QuestionnaireImpl questionnaireImpl = DaoFactory.getDaoFactoryInstance().getAdministratorQuestionnaireImpl();

    private Map<String, Object> session;

    private ArrayList<Question> questions = new ArrayList<Question>();

    private HttpServletRequest httpServletRequest;

    public QuestionnaireAction() throws DaoException {
    }

    public String get() {

        if (topic.equals("")) {
            topic = (String) session.get("topic");
        }
        try {
            questionnaireNumber = questionnaireImpl.getQuestionnaireCount(topic);
            questionnaires = questionnaireImpl.getQuestionnaires(topic, (pageNumber-1)*limit, limit);
        } catch (DaoException e) {
            return ERROR;
        }
        session.put("topic", topic);
        return SUCCESS;
    }

    public String getQuestionnaireInfo() {
        if (questionnaireId == -1) {
            topic = (String) session.get("topic");
            questionnaireId = (int)session.get("questionnaireId");
        }

        try {
            questionnaire = questionnaireImpl.getQuestionnaire(topic, questionnaireId);
        } catch (DaoException e) {
            return ERROR;
        }

        topic = (String) session.get("topic");
        session.put("questionnaireId", questionnaireId);
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

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }


    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getQuestionnaireNumber() {
        return questionnaireNumber;
    }

    public void setQuestionnaireNumber(int questionnaireNumber) {
        this.questionnaireNumber = questionnaireNumber;
    }


    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}
