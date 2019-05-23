package action.administrator;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

import java.util.ArrayList;

public class UserAction extends ActionSupport {
    private User user = new User();
    private ArrayList<User> users = new ArrayList<User>();

    private UserImpl administratorUserImpl = DaoFactory.getDaoFactoryInstance().getAdministratorUserImpl();


    public UserAction() throws DaoException {
    }

    public String update() {
        try {
            user.setStatus(!user.getStatus());
            administratorUserImpl.updateUser(user);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;

    }


    // TODO: 5/23/19 test
    public String add() {
        try {
            administratorUserImpl.addUser(user);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }


    public String get() {
        try {
            users = administratorUserImpl.getUsers();

        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    // TODO: 5/23/19  test
    public String delete() {
        try {
            administratorUserImpl.deleteUser(user);
        } catch (DaoException e) {
            return ERROR;
        }

        return SUCCESS;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
