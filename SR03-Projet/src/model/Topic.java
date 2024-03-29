package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable {
    private String name;
    private ArrayList<Questionnaire> questionnaires;

    public Topic(String name, ArrayList<Questionnaire> questionnaires){
        this.setName(name);
        this.setQuestionnaires(questionnaires);

    }

    public Topic(String name){
        this.setName(name);
        this.setQuestionnaires(null);
    }

    public Topic(){
        this.setName("NoName");
        this.setQuestionnaires(null);
    }


    public String getName() {
        return name;
    }

    public ArrayList<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestionnaires(ArrayList<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }

}
