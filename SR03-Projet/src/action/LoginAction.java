package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.User;
import org.apache.struts2.interceptor.SessionAware;
import postgresqlImpl.UserImpl;

import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
    private String email;
    private String password;
    private String type;
    private Map<String, Object> session;

    public String execute() throws DaoException {
        UserImpl userImpl = DaoFactory.getDaoFactoryInstance().getUserImpl();

        User user;
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
