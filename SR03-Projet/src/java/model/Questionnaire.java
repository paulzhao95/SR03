package model;

public class Questionnaire {
    private Integer questionnaireID;
    private String theme;
    private String status;


    public Questionnaire(Integer questionnaireID, String theme, String status){
        this.questionnaireID = questionnaireID;
        this.theme = theme;
        this.status = status;

    }

    public Integer getQuestionnaireID() {
        return questionnaireID;
    }

    public String getStatus() {
        return status;
    }

    public String getTheme() {
        return theme;
    }

    public void setQuestionnaireID(Integer questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
