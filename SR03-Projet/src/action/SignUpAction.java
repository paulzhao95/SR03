package action;

import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

public class SignUpAction {
    private User user = new User();

    public String execute() {

        DaoFactory daoFactoryInstance;
        try {
            daoFactoryInstance= DaoFactory.getDaoFactoryInstance();
        } catch (DaoException e) {
            return ("dataBaseConnectionFailed");
        }

        UserImpl administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();

        try {
            administratorUserImpl.addUser(user);

        } catch (DaoException e) {
            return ("signUpFailed");
        }

        return ("signUpSucceed");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
