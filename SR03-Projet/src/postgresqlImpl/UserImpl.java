package postgresqlImpl;

import dao.DaoException;
import dao.DaoFactory;
import dao.UserDao;
import model.User;

import java.sql.*;

public class UserImpl implements UserDao {

    protected DaoFactory daoFactory;

    public UserImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public User getUser(String login, String password, String typeUser) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = new User();


        try{
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * " +
                    "from users " +
                    "where email = ? " +
                    "and password = ? " +
                    "and type_user = cast (? as type_user) " +
                    "and status = 'Active'"
            );
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, typeUser);
            ;
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                Boolean status= (result.getString("Status").equals("Active"));
                String name = result.getString("Name");
                String tel = result.getString("Tel");
                String company = result.getString("Company");
                Timestamp creatingTime = result.getTimestamp("Creating_time");


                user.setStatus(status);
                user.setTel(tel);
                user.setName(name);
                user.setPassword(password);
                user.setCompany(company);
                user.setEmail(login);
                user.setCreatingTime(creatingTime);

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
        return user;
    }


}
