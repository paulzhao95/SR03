package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Questionnaire implements Serializable {
    private Integer questionnaireId;
    private String name;
    private String topic;
    private Boolean status;
    private ArrayList<Question> questions;


    public Questionnaire(Integer questionnaireId, String topic, String  name, Boolean status, ArrayList<Question> questions){
        this.setQuestionnaireId(questionnaireId);
        this.setName(name);
        this.setQuestions(questions);
        this.setTopic(topic);
        this.setStatus(status);
    }

    public Questionnaire(String topic, String name){
        this.setName(name);
        this.setQuestionnaireId(0);
        this.setTopic(topic);
        this.setStatus(true);
        this.setQuestions(new ArrayList<Question>());

    }

    public Questionnaire(String topic, int questionnaireId){
        this.setName("");
        this.setQuestionnaireId(questionnaireId);
        this.setTopic(topic);
        this.setStatus(true);
        this.setQuestions(new ArrayList<Question>());

    }

    public Questionnaire(int questionnaireId, String topic, String name, Boolean status) {
        this.setName(name);
        this.setQuestionnaireId(questionnaireId);
        this.setTopic(topic);
        this.setStatus(status);
        this.setQuestions(new ArrayList<Question>());
    }

    public Questionnaire(){
        this.setName("NoName");
        this.setQuestionnaireId(0);
        this.setTopic("NoTopic");
        this.setStatus(true);
        this.setQuestions(new ArrayList<Question>());
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

}
