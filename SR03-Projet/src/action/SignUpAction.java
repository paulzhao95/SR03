package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

public class SignUpAction extends ActionSupport {
    private User user = new User();

    public String execute() throws DaoException {

        DaoFactory daoFactoryInstance = DaoFactory.getDaoFactoryInstance();


        UserImpl administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();

        try {
            administratorUserImpl.addUser(user);

        } catch (DaoException e) {
            return ERROR;
        }

        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
