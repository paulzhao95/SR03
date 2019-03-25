package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Attempt {

    private int id;
    private String topicName;
    private String questionnaireName;
    private int questionnaireId;
    private int score;
    private Timestamp startTime;
    private Timestamp endTime;
    private int durationInSeconds;
    private ArrayList<Choice> userChoices;

    public Attempt(int id, String topicName, String questionnaireName, int score, Timestamp startTime, Timestamp endTime, int durationInSeconds, double scoreDivByDurationTimes100){
        this.setId(id);
        this.setTopicName(topicName);
        this.setQuestionnaireName(questionnaireName);
        this.setScore(score);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setDurationInSeconds(durationInSeconds);
    }

    public Attempt(){
        this.setId(0);
        this.topicName = null;
        this.questionnaireName = null;
        this.score = 0;
        this.startTime = new Timestamp(new Date().getTime());
        this.endTime = new Timestamp(new Date().getTime());
        this.durationInSeconds = 0;
        this.setUserChoices(new ArrayList<Choice>());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getQuestionnaireId() {
        return questionnaireId;
    }
    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }
    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getStartTime() {
        String formattedAccountCreation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.startTime);
        return formattedAccountCreation;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getBeginingSql() {
        String formattedAccountCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime);
        return formattedAccountCreation;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }
    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
    public void setDurationInSeconds() {
        Duration duration = Duration.between(this.startTime.toLocalDateTime(), this.endTime.toLocalDateTime()) ;
        this.durationInSeconds = (int) duration.getSeconds();
    }

    public ArrayList<Choice> getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(ArrayList<Choice> userChoices) {
        this.userChoices = userChoices;
    }

    public void increaseScore(){
        this.score += 1;
    }
    public void decreaseScore(){
        this.score -= 1;
    }


    public String getEndSql() {
        String formattedAccountCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime);
        return formattedAccountCreation;
    }

    public String getEndTime() {
        String formattedAccountCreation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.endTime);
        return formattedAccountCreation;
    }



    //To delete doublon
    public void setAnswersUnique() {
        Set<Choice> hs = new HashSet<>();
        hs.addAll(this.userChoices);
        this.userChoices.clear();
        this.userChoices.addAll(hs);
    }

//
//
//    public Map<String, Choice> compareAnswer(Question q){
//        Map<String, Choice> answerList = new HashMap<String, Choice>();
//        ArrayList<Choice> wrongChoices = q.getWrongChoices();
//        ArrayList<Choice> rightChoice= q.getRightChoice();
//
//        for(Answer attemptedAnswer : this.attemptedAnswers){
//            if(attemptedAnswer.getQuestionId() == q.getId()){
//                if(attemptedAnswer.getClass() == GoodAnswer.class){
//                    answerList.put("trueAnswer", attemptedAnswer);
//                }
//                else{
//                    answerList.put("zfalseAnswer", attemptedAnswer);
//                    for(Answer answer : answers){
//                        if(answer.getClass() == GoodAnswer.class){
//                            answerList.put("answerGood", answer);
//                            break;
//                        }
//                    }
//                }
//                break;
//            }
//        }
//        return answerList;
//    }


}
