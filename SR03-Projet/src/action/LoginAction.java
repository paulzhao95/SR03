package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.User;
import org.apache.struts2.interceptor.SessionAware;
import postgresqlImpl.UserImpl;

import java.util.Map;

// TODO: 6/14/19 in admin questionnaire list jsp : change action name from get questionnaire to get questions when click questionnaire name. Parameters to pass : questionnaireId, topic and pageNumber
// TODO: 6/14/19 in admin questionnaire list jsp : change delete button to delete / edit button and admin have access to edit questionnaire from url of edit

// TODO: 6/14/19 visualisation intern result for admin with all attempts and best score for each questionnaire

// TODO: 6/14/19 timer



public class LoginAction extends ActionSupport implements SessionAware {
    private String email;
    private String password;
    private String type;
    private User user;
    private Map<String, Object> session;

    public String execute() throws DaoException {
        UserImpl userImpl = DaoFactory.getDaoFactoryInstance().getUserImpl();

        try{
            user = userImpl.getUser(email, password,type);
            session.put("user", user);
            if (type.equals("Administrator")) {
                return "administratorLoginSucceed";
            } else {
                return "internLoginSucceed";
            }
        } catch (DaoException e) {
            return "loginFailed";
        }

    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
