package action.administrator;

import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

public class AddUserAction {
    User user = new User();

    public String execute() {
        DaoFactory daoFactoryInstance = null;
        try {
            daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
        } catch (DaoException e) {
            return "dataBaseConnectionFailed";
        }
        UserImpl administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();


        return "success";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
