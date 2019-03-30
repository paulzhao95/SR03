package model;

import java.util.ArrayList;

public class Question {
    private Integer questionID;
    private Boolean status ;
    private String Description;
    private ArrayList<Choice> choices;

    public Question(Integer id, String description, Boolean status) {
        this.Description = description;
        this.questionID = id;
        this.status = status;
    }

    public Question(Integer id, String description, Boolean status, ArrayList<Choice> choices) {
        this.Description = description;
        this.questionID = id;
        this.status = status;
        this.choices = choices;
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
        return choices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }


}
