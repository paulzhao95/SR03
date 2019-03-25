package model;

import java.util.ArrayList;

public class Question {
    private Integer questionID;
    private Boolean status ;
    private String Description;
    private Choice rightChoice;
    private ArrayList<Choice> wrongChoices;

    public Question(Integer id, String description, Boolean status) {
        this.Description = description;
        this.questionID = id;
        this.status = status;
    }

    public Question(Integer id, String description, Boolean status, Choice rightChoice, ArrayList<Choice> wrongChoices) {
        this.Description = description;
        this.questionID = id;
        this.status = status;
        this.rightChoice = rightChoice;
        this.wrongChoices = wrongChoices;
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

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public ArrayList<Choice> getWrongChoices() {
        return wrongChoices;
    }

    public Choice getRightChoice() {
        return rightChoice;
    }

    public void setRightChoice(Choice rightChoice) {
        this.rightChoice = rightChoice;
    }

    public void setWrongChoices(ArrayList<Choice> wrongChoices) {
        this.wrongChoices = wrongChoices;
    }


}
