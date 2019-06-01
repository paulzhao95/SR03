package action.intern;

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

    public TopicAction() throws DaoException {
    }



    public String get() throws DaoException {
        topics = internTopicImpl.getTopics();

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


}
