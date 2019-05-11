package postgresqlImpl;

import dao.DaoException;
import dao.DaoFactory;
import dao.UserDao;
import model.Administrator;
import model.Intern;
import java.sql.*;

public class UserImpl implements UserDao {

    protected DaoFactory daoFactory;

    public UserImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Administrator getAdministrator(String login, String password) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Administrator administrator = new Administrator();


        try{
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * " +
                    "from users " +
                    "where email = ? " +
                    "and password = ? " +
                    "and type_user ='Administrator' "
            );
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password)
            ;
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                Boolean status= (result.getString("Status").equals("Active"));
                String name = result.getString("Name");
                String tel = result.getString("Tel");
                String company = result.getString("Company");
                Timestamp creatingTime = result.getTimestamp("Creating_time");

                administrator.setStatus(status);
                administrator.setTel(tel);
                administrator.setName(name);
                administrator.setPassword(password);
                administrator.setCompany(company);
                administrator.setEmail(login);
                administrator.setCreatingTime(creatingTime);

            }else{
                throw new DaoException("Administrator not found.");
            }
        } catch (SQLException e) {
            throw new DaoException("Get Administrator from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return administrator;
    }

    @Override
    public Intern getIntern(String login, String password) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Intern intern = new Intern();


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * " +
                    "from Users " +
                    "where Email = ? " +
                    "and Password = ? " +
                    "and Type_user ='Intern' "
            );
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password)
            ;
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                Boolean status = (result.getString("Status").equals("Active"));
                String name = result.getString("Name");
                String tel = result.getString("Tel");
                String company = result.getString("Company");
                Timestamp creatingTime = result.getTimestamp("Creating_time");

                intern.setStatus(status);
                intern.setTel(tel);
                intern.setName(name);
                intern.setPassword(password);
                intern.setCompany(company);
                intern.setEmail(login);
                intern.setCreatingTime(creatingTime);

            } else {
                throw new DaoException("Intern not found.");
            }
        } catch (SQLException e) {
            throw new DaoException("Get intern from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return intern;
    }

}