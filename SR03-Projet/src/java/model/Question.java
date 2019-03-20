package model;

import java.util.ArrayList;

public class Question {
    private Integer questionID;
    private String status = "Active";
    private String Description;
    private Integer score = 1;
    private Choice rightChoice;
    private ArrayList<Choice> wrongChoices;

    public Question(Integer id, String description, Integer score,Choice rightChoice, ArrayList<Choice> wrongChoices) {
        this.Description = description;
        this.questionID = id;
        this.score = score;
        this.rightChoice = rightChoice;
        this.wrongChoices  =wrongChoices;
    }

    public String getStatus() {
        return status;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public Integer getScore() {
        return score;
    }

    public String getDescription() {
        return Description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


}
