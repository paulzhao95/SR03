package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Attempt {

    private int id;
    private String topicName;
    private String questionnaireName;
    private int questionnaireId;
    private Timestamp startTime;
    private int durationInSeconds;
    private HashMap<Integer,Choice> userChoices;

    public Attempt(int id, String topicName, int questionnaireId, String questionnaireName,  Timestamp startTime, int durationInSeconds){
        this.setId(id);
        this.setTopicName(topicName);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionnaireName(questionnaireName);
        this.setStartTime(startTime);
        this.setDurationInSeconds(durationInSeconds);
    }

    public Attempt(){
        this.setId(0);
        this.topicName = null;
        this.questionnaireName = null;
        this.startTime = new Timestamp(new Date().getTime());
        this.durationInSeconds = 0;
        this.setUserChoices(new HashMap<Integer,Choice>());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getQuestionnaireId() {
        return questionnaireId;
    }
    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }
    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }



    public String getStartTime() {
        String formattedAccountCreation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.startTime);
        return formattedAccountCreation;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getBeginingSql() {
        String formattedAccountCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.startTime);
        return formattedAccountCreation;
    }


    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }


    public HashMap<Integer,Choice> getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(HashMap<Integer,Choice> userChoices) {
        this.userChoices = userChoices;
    }




}
