package action.administrator;

import dao.DaoException;
import dao.DaoFactory;
import model.Topic;
import postgresqlImpl.administrator.TopicImpl;

public class TopicAction {
    private Topic topic = new Topic();
    private  TopicImpl administratorTopicImpl = DaoFactory.getDaoFactoryInstance().getAdministratorTopicImpl();
    private String newTopicName = "";
    public TopicAction() throws DaoException {
    }

    public String add() {
        try {
            administratorTopicImpl.addTopic(topic);
        } catch (DaoException e) {
            return "addTopicFailed";
        }
        return "addTopicSucceed";
    }

    public String delete() {
        try {
            administratorTopicImpl.deleteTopic(topic);

        } catch (DaoException e) {
            return "deleteTopicFailed";
        }
        return "deleteTopicSucceed";
    }

    public String update() {
        try {
            administratorTopicImpl.updateTopic(topic.getName(), newTopicName);
        } catch (DaoException e) {
            return "updateTopicFailed";
        }
        return "updateTopicSucceed";
    }

    public String getTopics() {
        try {
            administratorTopicImpl.getTopics();
        } catch (DaoException e) {
            return "getTopicFailed";
        }
        return "getTopicSucceed";
    }


    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getNewTopicName() {
        return newTopicName;
    }

    public void setAdministratorTopicImpl(TopicImpl administratorTopicImpl) {
        this.administratorTopicImpl = administratorTopicImpl;
    }
}
