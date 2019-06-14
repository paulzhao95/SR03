package action.intern;

import action.GlobalVariable;
import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.Topic;
import postgresqlImpl.TopicImpl;

import java.util.ArrayList;

public class TopicAction extends ActionSupport {
    private Topic topic = new Topic();
    private ArrayList<Topic> topics = new ArrayList<>();
    private  TopicImpl internTopicImpl = DaoFactory.getDaoFactoryInstance().getTopicImpl();

    private int topicNumber = 0;
    private int limit = GlobalVariable.NUMBER_PER_PAGE;
    private int pageNumber = 1;
    public TopicAction() throws DaoException {
    }



    public String get() throws DaoException {
        topicNumber = internTopicImpl.getTopicCount();
        topics = internTopicImpl.getTopics((pageNumber-1)*limit, limit);
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


    public int getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(int topicNumber) {
        this.topicNumber = topicNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

