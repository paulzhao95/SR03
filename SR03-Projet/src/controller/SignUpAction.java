package controller;

import dao.DaoException;
import dao.DaoFactory;
import model.Administrator;
import model.Intern;
import model.User;
import postgresqlImpl.administrator.ChoiceImpl;
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
            if (user.getType().toString().equals("Administrator")) {
                administratorUserImpl.addAdministrator(new Administrator(user));
            } else {
                administratorUserImpl.addIntern(new Intern(user));
            }
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
