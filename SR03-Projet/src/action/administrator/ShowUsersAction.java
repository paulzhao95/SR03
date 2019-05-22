package action.administrator;

import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

import java.util.ArrayList;

public class ShowUsersAction {
    ArrayList<User> users = new ArrayList<User>();

    public String execute() {
        UserImpl administratorUserImpl;
        try {
            DaoFactory daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
            administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();
            users = administratorUserImpl.getUsers();

        } catch (DaoException e) {
            return "dataBaseConnectionFailed";
        }
        return "success";
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
