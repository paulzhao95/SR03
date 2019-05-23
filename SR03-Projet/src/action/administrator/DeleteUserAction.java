package action.administrator;

import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

// TODO: 5/23/19 debug
public class DeleteUserAction {

    private User user = new User();

    public String execute() {
        DaoFactory daoFactoryInstance;
        try {
            daoFactoryInstance = DaoFactory.getDaoFactoryInstance();
        } catch (DaoException e) {
            return "dataBaseConnectionFailed";
        }
        UserImpl administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();
        try {
            administratorUserImpl.deleteUser(user);
        } catch (DaoException e) {
            return "addUserFailed";
        }

        return "addUserSucceed";

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
