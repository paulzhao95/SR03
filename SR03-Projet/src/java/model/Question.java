package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String topic;
    private int questionnaireId;
    private int questionID;
    private Boolean status;
    private String Description;
    private ArrayList<Choice> choices;

    public Question(String topic, int questionnaireId, Integer id, String description, Boolean status) {
        this.setTopic(topic);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionID(id);
        this.setDescription(description);
        this.setStatus(status);
    }

    public Question(Integer id, String description, Boolean status, ArrayList<Choice> choices) {
        this.setTopic(topic);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionID(id);
        this.setDescription(description);
        this.setStatus(status);
        this.setChoices(choices);
    }

    public Question() {
        this.setTopic("");
        this.setQuestionnaireId(0);
        this.setQuestionID(0);
        this.setDescription("");
        this.setStatus(false);
        this.setChoices(new ArrayList<Choice>());
    }

    public String getTopic() {
        return topic;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public Boolean getStatus() {
        return status;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public String getDescription() {
        return Description;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public ArrayList<Choice> getWrongChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }

}
