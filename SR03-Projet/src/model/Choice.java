package model;

import java.io.Serializable;

public class Choice implements Serializable {
    private String topic;
    private int questionnaireId;
    private int questionId;
    private int choiceID;
    private Boolean status;
    private String description;
    private Boolean isRight;

    public Choice(String topic, int questionnaireId, int questionId, int choiceId, String description, Boolean status, Boolean isRight) {
        this.setTopic(topic);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionId(questionId);
        this.setChoiceID(choiceId);
        this.setDescription(description);
        this.setStatus(status);
        this.setIsRight(isRight);
    }

    public Choice() {
        this.setTopic("");
        this.setQuestionId(0);
        this.setQuestionnaireId(0);
        this.setChoiceID(0);
        this.setDescription("");
        this.setStatus(true);
        this.setIsRight(false);
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public String getTopic() {
        return topic;
    }

    public void setChoiceID(int choiceID) {
        this.choiceID = choiceID;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getStatus() {
        return status;
    }


    public int getChoiceID() {
        return choiceID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setChoiceID(Integer choiceID) {
        this.choiceID = choiceID;
    }


    public void setIsRight(Boolean right) {
        isRight = right;
    }

    public Boolean getIsRight() {
        return isRight;
    }
}
