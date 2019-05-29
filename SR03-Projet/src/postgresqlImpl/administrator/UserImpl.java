package postgresqlImpl.administrator;

import dao.DaoException;
import dao.DaoFactory;
import dao.administrator.UserDao;
import model.*;

import java.sql.*;
import java.util.ArrayList;

public class UserImpl implements UserDao {

    private DaoFactory daoFactory;

    public UserImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<User> getUsers() throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement;
        ArrayList<User> users = new ArrayList<>();


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * from users "
            );
            ;
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {

                String password = result.getString("password");
                Boolean status = (result.getString("status").equals("Active"));
                String name = result.getString("name");
                String tel = result.getString("tel");
                String company = result.getString("company");
                Timestamp creatingTime = result.getTimestamp("creating_time");
                String email = result.getString("email");
                User.UserType type_user = result.getString("type_user").equals("Administrator") ? User.UserType.Administrator : User.UserType.Intern;

                users.add(new User(email, password, name, status, company, tel, creatingTime, type_user));

            }
        } catch (SQLException e) {
            throw new DaoException("Get users from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return users;
    }

    @Override
    public User getUser(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = new User();


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * " +
                    "from users " +
                    "where email = ? "
            );
            preparedStatement.setString(1, login);
            ;
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String password = result.getString("password");
                Boolean status = (result.getString("Status").equals("Active"));
                String name = result.getString("Name");
                String tel = result.getString("Tel");
                String company = result.getString("Company");
                Timestamp creatingTime = result.getTimestamp("Creating_time");
                String type_user = result.getString("type_user");

                user.setStatus(status);
                user.setTel(tel);
                user.setName(name);
                user.setPassword(password);
                user.setCompany(company);
                user.setEmail(login);
                user.setCreatingTime(creatingTime);
                user.setType(User.UserType.valueOf(type_user));
            } else {
                throw new DaoException("Intern not found.");
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




    @Override
    public void addUser(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("insert into Users(password, status, name, company, tel, creating_time, email, type_user) values (?,?,?,?,?,NOW(),?,cast(? as type_user))"
            );
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setBoolean(2, user.getStatus());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getCompany());
            preparedStatement.setString(5, user.getTel());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getType() == User.UserType.Administrator ? "Administrator":"Intern");

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not insert Administrator");
            }

        } catch (SQLException e) {
            throw new DaoException("Add Administrator from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }

    }

    @Override
    public void deleteUser(User user) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("delete from Users where email = ? "
            );
            preparedStatement.setString(1, user.getEmail());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not delete intern");
            }

        } catch (SQLException e) {
            throw new DaoException("delete intern from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }


    }

    @Override
    public void updateUser(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("update  Users " +
                    "set password = ?, " +
                    "name = ? , " +
                    "company = ?, " +
                    "tel = ?, " +
                    "type_user = cast(? as type_user)," +
                    "status = ?  "+
                    "where email = ? "
            );
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getCompany());
            preparedStatement.setString(4, user.getTel());
            preparedStatement.setString(5, user.getType() == User.UserType.Intern ? "Intern":"Administrator");
            preparedStatement.setBoolean(6,user.getStatus() );
            preparedStatement.setString(7, user.getEmail());


            int i = preparedStatement.executeUpdate();
            connection.commit();

            if (i == 0) {
                throw new DaoException("Can not modify administrator");
            }

        } catch (SQLException e) {
            throw new DaoException("modify administrator in database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

}
