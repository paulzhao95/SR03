package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Questionnaire implements Serializable {
    private Integer questionnaireID;
    private String name;
    private String topic;
    private Boolean status;
    private ArrayList<Question> questions;


    public Questionnaire(Integer questionnaireID, String topic, String  name, Boolean status, ArrayList<Question> questions){
        this.setQuestionnaireID(questionnaireID);
        this.setName(name);
        this.setQuestions(questions);
        this.setTopic(topic);
        this.setStatus(status);
    }

    public Questionnaire(Integer questionnaireID, String topic, String name){
        this.setName(name);
        this.setQuestionnaireID(questionnaireID);
        this.setTopic(topic);
        this.setStatus(true);
        this.setQuestions(new ArrayList<Question>());

    }

    public Questionnaire(int questionnaireID, String topic, String name, Boolean status) {
        this.setName(name);
        this.setQuestionnaireID(questionnaireID);
        this.setTopic(topic);
        this.setStatus(status);
        this.setQuestions(new ArrayList<Question>());
    }

    public Questionnaire(){
        this.setName("NoName");
        this.setQuestionnaireID(0);
        this.setTopic("NoTopic");
        this.setStatus(false);
        this.setQuestions(new ArrayList<Question>());
    }

    public Integer getQuestionnaireID() {
        return questionnaireID;
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

    public void setQuestionnaireID(Integer questionnaireID) {
        this.questionnaireID = questionnaireID;
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
