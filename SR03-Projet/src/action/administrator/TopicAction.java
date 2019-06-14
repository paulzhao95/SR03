package action.administrator;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Topic;
import postgresqlImpl.administrator.TopicImpl;

import java.util.ArrayList;




public class TopicAction extends ActionSupport {
    private Topic topic = new Topic();
    private ArrayList<Topic> topics = new ArrayList<>();
    private TopicImpl administratorTopicImpl = DaoFactory.getDaoFactoryInstance().getAdministratorTopicImpl();
    private String newTopicName = "";
    private int topicNumber = 0;
    private int limit = 5;
    private int pageNumber = 1;

    public TopicAction() throws DaoException {
    }

    public String add() {
        try {
            administratorTopicImpl.addTopic(topic);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String delete() {
        try {
            administratorTopicImpl.deleteTopic(topic);

        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String update() {
        try {
            administratorTopicImpl.updateTopic(topic.getName(), newTopicName);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String get() throws DaoException {
        topicNumber = administratorTopicImpl.getTopicCount();
        topics = administratorTopicImpl.getTopics((pageNumber-1)*limit, limit);
        return SUCCESS;
    }


    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public String getNewTopicName() {
        return newTopicName;
    }

    public void setNewTopicName(String newTopicName) {
        this.newTopicName = newTopicName;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(int topicNumber) {
        this.topicNumber = topicNumber;
    }

}
