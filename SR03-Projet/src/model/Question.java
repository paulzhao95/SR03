package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String topic;
    private int questionnaireId;
    private int questionId;
    private Boolean status;
    private String description;
    private ArrayList<Choice> choices;

    public Question(String topic, int questionnaireId, Integer id, String description, Boolean status) {
        this.setTopic(topic);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionId(id);
        this.setDescription(description);
        this.setStatus(status);
    }

    public Question(String topic, int questionnaireId, int id, String description, Boolean status, ArrayList<Choice> choices) {
        this.setTopic(topic);
        this.setQuestionnaireId(questionnaireId);
        this.setQuestionId(id);
        this.setDescription(description);
        this.setStatus(status);
        this.setChoices(choices);
    }


    public Question() {
        this.setTopic("");
        this.setQuestionnaireId(0);
        this.setQuestionId(0);
        this.setDescription("");
        this.setStatus(true);
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

    public Integer getQuestionId() {
        return questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public ArrayList<Choice> getWrongChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

}
