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
    private String email;

    private int limit = 10;
    private int pageNumber = 1;
    private int userNumber = 0;

    private UserImpl administratorUserImpl = DaoFactory.getDaoFactoryInstance().getAdministratorUserImpl();


    public UserAction() throws DaoException {
    }

    // TODO: 5/27/19 change status failed
    public String update() {
        try {
            administratorUserImpl.updateUser(user);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public String getUserInfo() {
        try {
            user = administratorUserImpl.getUser(email);
        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;

    }

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
            userNumber = administratorUserImpl.getUserCount();
            users = administratorUserImpl.getUsers((pageNumber-1)*limit, limit);

        } catch (DaoException e) {
            return ERROR;
        }
        return SUCCESS;
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
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

    public void setPageNumber(int offset) {
        this.pageNumber = offset;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }
}
