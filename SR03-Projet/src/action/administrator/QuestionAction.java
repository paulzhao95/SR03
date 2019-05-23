package action.administrator;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.javaws.exceptions.ErrorCodeResponseException;
import dao.DaoException;
import dao.DaoFactory;
import model.Question;
import postgresqlImpl.administrator.QuestionImpl;

import java.util.ArrayList;

public class QuestionAction extends ActionSupport {
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Question question = new Question();
    private QuestionImpl questionImpl = DaoFactory.getDaoFactoryInstance().getdAdministratorQuestionImpl();
    private String topic = "";
    private int questionnaireId ;


    public QuestionAction() throws DaoException {
    }

    public String get() {
        try {
            questionImpl.getQuestions(topic, questionnaireId);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

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

    public String update() {
        try {
            questionImpl.updateQuestion(question);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
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

}
