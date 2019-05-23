package action.administrator;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Questionnaire;
import org.apache.struts2.ServletActionContext;
import postgresqlImpl.administrator.QuestionnaireImpl;

import java.util.ArrayList;


public class QuestionnaireAction extends ActionSupport {
    private ArrayList<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
    private Questionnaire questionnaire = new Questionnaire();

    QuestionnaireImpl questionnaireImpl = DaoFactory.getDaoFactoryInstance().getAdministratorQuestionnaireImpl();

    public QuestionnaireAction() throws DaoException {
    }

    public String get() {

        String topic = ServletActionContext.getRequest().getParameter("Topic");
        try {
            questionnaires = questionnaireImpl.getQuestionnaires(topic);
        } catch (DaoException e) {
            return ERROR;
        }
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
}
