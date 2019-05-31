package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Attempt implements Serializable {

    private int id;
    private String userEmail;
    private String topicName;
    private String questionnaireName;
    private int questionnaireId;
    private Timestamp startTime;
    private int durationInSeconds;
    private int score = 0;
    private int fullMarks = 0;
    private ArrayList<Choice> userChoices;

    public Attempt(int id, String userEmail, String topicName, int questionnaireId, String questionnaireName,  Timestamp startTime, int durationInSeconds, int score, int fullMarks){
        this.setId(id);
        this.setUserEmail(userEmail);
        this.setTopicName(topicName);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionnaireName(questionnaireName);
        this.setStartTime(startTime);
        this.setDurationInSeconds(durationInSeconds);
        this.setFullMarks(fullMarks);
        this.setScore(score);
        this.userChoices = new ArrayList<Choice>();

    }

    public Attempt(int id, String userEmail, String topicName, int questionnaireId, String questionnaireName,  Timestamp startTime, int durationInSeconds, ArrayList<Choice> userChoices, int fullMarks) {
        this.setId(id);
        this.setUserEmail(userEmail);
        this.setTopicName(topicName);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionnaireName(questionnaireName);
        this.setStartTime(startTime);
        this.setDurationInSeconds(durationInSeconds);
        this.setFullMarks(fullMarks);
        this.setUserChoices(userChoices);
        this.calculateScore();
    }


    public Attempt(){
        this.setUserEmail("");
        this.setId(0);
        this.setFullMarks(0);
        this.setScore(0);

        this.topicName = null;
        this.questionnaireName = null;
        this.startTime = new Timestamp(new Date().getTime());
        this.durationInSeconds = 0;
        this.setUserChoices(new ArrayList<Choice>());
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getFullMarks() {
        return fullMarks;
    }

    public void setFullMarks(int fullMarks) {
        this.fullMarks = fullMarks;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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



    public Timestamp getStartTime() {
        return this.startTime;
//        String formattedAccountCreation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.startTime);
//        return formattedAccountCreation;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

//    public String getBeginingSql() {
//        String formattedAccountCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.startTime);
//        return formattedAccountCreation;
//    }


    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }


    public ArrayList<Choice> getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(ArrayList<Choice> userChoices) {
        this.userChoices = userChoices;
    }

    private void calculateScore() {
        for (Choice choice: this.userChoices  ) {
            if (choice.getIsRight()) {
                this.score += 1;
            }
        }

    }


}
