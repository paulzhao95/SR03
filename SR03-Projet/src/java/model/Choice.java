package model;

import java.io.Serializable;

public class Choice implements Serializable {
    private Integer choiceID;
    private Boolean status;
    private String description;
    private Boolean isRight;

    public Choice(Integer choiceId, String description, Boolean status, Boolean isRight){
        this.choiceID = choiceId;
        this.description = description;
        this.status = status;
        this.isRight = isRight;

    }

    public Choice(){
        this.choiceID = 0;
        this.description = "";
        this.status = false;
        this.isRight = true;
    }


    public String getDescription() {
        return description;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getRight() {
        return isRight;
    }

    public Integer getChoiceID() {
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

    public void setRight(Boolean right) {
        isRight = right;
    }

}
