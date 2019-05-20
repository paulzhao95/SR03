package controller.administrator;

import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

public class UpdateUserAction {
    private User user = new User();

    public String execute() {
        UserImpl administratorUserImpl;
        try {
            DaoFactory daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
            administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();
        } catch (DaoException e) {
            return "dataBaseConnectionFailed";
        }

        try {
            administratorUserImpl.updateUser(user);
            return "updateUserSucceed";
        } catch (DaoException e) {
            return "updateUserFailed";
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
