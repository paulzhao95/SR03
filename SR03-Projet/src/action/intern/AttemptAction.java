package action.intern;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.*;
import org.apache.struts2.interceptor.SessionAware;
import postgresqlImpl.intern.AttemptImpl;
import postgresqlImpl.QuestionnaireImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AttemptAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private Questionnaire questionnaire;
    private Question question;
    private String topic;
    private int choiceId;
    private int questionnaireId;
    private Attempt attempt = new Attempt();
    private ArrayList<Attempt> attempts = new ArrayList<Attempt>();
    private int attemptId;
    private String changePage;

    private String userEmail = "";
    private Timestamp startTime = null;

    private QuestionnaireImpl questionnaireImpl = DaoFactory.getDaoFactoryInstance().getQuestionnaireImpl();
    private AttemptImpl attemptImpl = DaoFactory.getDaoFactoryInstance().getInternAttemptImpl();

    public AttemptAction() throws DaoException {
    }


    public String start() {
        try {
            questionnaire = questionnaireImpl.getQuestionnaire(topic, questionnaireId);
        } catch (DaoException e) {
            return ERROR;
        }
        User user = (User) session.get("user");

        attempt.setTopicName(topic);
        attempt.setFullMarks(questionnaire.getQuestions().size());
        attempt.setUserEmail(user.getEmail());
        attempt.setQuestionnaireId(questionnaireId);
        attempt.setQuestionnaireName(questionnaire.getName());
        attempt.setUserChoices(new ArrayList<Choice>(questionnaire.getQuestions().size()));
        for (int i = 0; i < questionnaire.getQuestions().size(); i++) {
            attempt.addChoice(new Choice());

        }

        session.put("questionnaire", this.questionnaire);
        session.put("currentNumber", 0);
        session.put("attempt", this.attempt);

        question = questionnaire.getQuestions().get(0);

        return SUCCESS;
    }


    public String prepare() {
        return SUCCESS;
    }

    public String previous() {
        // save current question
        int currentQuestionNumber = (int) session.get("currentNumber");
        questionnaire = (Questionnaire) session.get("questionnaire");
        attempt = (Attempt)session.get("attempt");
        attempt.setChoice(currentQuestionNumber, questionnaire.getQuestions().get(currentQuestionNumber).getChoices().get(choiceId));


        question = questionnaire.getQuestions().get(currentQuestionNumber - 1);
        session.put("currentNumber", currentQuestionNumber-1);
        session.put("attempt", this.attempt);
        return SUCCESS;
    }

    public String next() {
        // save current question

        int currentQuestionNumber = (int) session.get("currentNumber");
        questionnaire = (Questionnaire) session.get("questionnaire");
        attempt = (Attempt)session.get("attempt");
        attempt.setChoice(currentQuestionNumber, questionnaire.getQuestions().get(currentQuestionNumber).getChoices().get(choiceId));

        switch (changePage) {
            case "Next":
                question = questionnaire.getQuestions().get(currentQuestionNumber + 1);
                session.put("currentNumber", currentQuestionNumber+1);
                break;
            case "Previous":
                question = questionnaire.getQuestions().get(currentQuestionNumber - 1);
                session.put("currentNumber", currentQuestionNumber-1);
                break;
            case "Finish":
                long time = new Date().getTime();
                int duration = (int) (time - attempt.getStartTime().getTime())/1000;
                attempt.setDurationInSeconds(duration);
                attempt.calculateScore();

                try {
                    attemptId = attemptImpl.addAttempt(attempt);
                    attempt.setId(attemptId);
                } catch (DaoException e) {
                    return ERROR;
                }
                session.put("attempt", this.attempt);

                return "finish";
        }

        session.put("attempt", this.attempt);
        return SUCCESS;
    }

    public String get() {
        User user = (User)session.get("user");


        try {
            attempts = attemptImpl.getAttempts(user.getEmail());
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String getAttemptInfo() {
        User user = (User)session.get("user");
        try {
            attempt = attemptImpl.getAttempt(attemptId);
            String topicName = attempt.getTopicName();
            int questionnaireId = attempt.getQuestionnaireId();

            questionnaire = questionnaireImpl.getQuestionnaire(topic, questionnaireId);

        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;

    }

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

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public ArrayList<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(ArrayList<Attempt> attempts) {
        this.attempts = attempts;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public String getChangePage() {
        return changePage;
    }

    public void setChangePage(String changePage) {
        this.changePage = changePage;
    }


}
