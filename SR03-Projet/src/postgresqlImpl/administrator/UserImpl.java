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
    public Administrator getAdministrator(String login) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement;
        Administrator administrator = new Administrator();


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * " +
                    "from users " +
                    "where email = ? " +
                    "and type_user ='Administrator' "
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

                administrator.setStatus(status);
                administrator.setTel(tel);
                administrator.setName(name);
                administrator.setPassword(password);
                administrator.setCompany(company);
                administrator.setEmail(login);
                administrator.setCreatingTime(creatingTime);

            } else {
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
                User.UserType type_user = result.getString("type_user") == "Administrator" ? User.UserType.Administrator : User.UserType.Intern;

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
    public Intern getIntern(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Intern intern = new Intern();


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * " +
                    "from users " +
                    "where email = ? " +
                    "and type_user ='Intern' "
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
            throw new DaoException("Get Administrator from database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
        return intern;
    }




    @Override
    public void addIntern(Intern intern) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("insert into Users(password, status, name, company, tel, creating_time, email, type_user) values (?,cast(? as states),?,?,?,NOW(),?,cast(? as type_user))"
            );
            preparedStatement.setString(1, intern.getPassword());
            preparedStatement.setString(2, intern.getStatus() ? "Active" : "Inactive");
            preparedStatement.setString(3, intern.getName());
            preparedStatement.setString(4, intern.getCompany());
            preparedStatement.setString(5, intern.getTel());
            preparedStatement.setString(6, intern.getEmail());
            preparedStatement.setString(7, intern.getType()== User.UserType.Intern ? "Intern":"Administrator");

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not insert Intern");
            }

        } catch (SQLException e) {
            throw new DaoException("Add intern to database failed");
        }

        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection to database failed");
        }
    }

    @Override
    public void addAdministrator(Administrator administrator) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("insert into Users(password, status, name, company, tel, creating_time, email, type_user) values (?,cast(? as states),?,?,?,NOW(),?,cast(? as type_user))"
            );
            preparedStatement.setString(1, administrator.getPassword());
            preparedStatement.setString(2, administrator.getStatus() ? "Active" : "Inactive");
            preparedStatement.setString(3, administrator.getName());
            preparedStatement.setString(4, administrator.getCompany());
            preparedStatement.setString(5, administrator.getTel());
            preparedStatement.setString(6, administrator.getEmail());
            preparedStatement.setString(7, administrator.getType() == User.UserType.Administrator ? "Administrator":"Intern");

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
    public void deleteIntern(Intern intern) throws DaoException {
        Connection connection ;
        PreparedStatement preparedStatement ;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("delete from Users where email = ? and type = 'Intern'"
            );
            preparedStatement.setString(1, intern.getEmail());

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
    public void deleteAdministrator(Administrator administrator) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement("delete from Users where email = ? and type = 'Administrator'"
            );
            preparedStatement.setString(1, administrator.getEmail());

            int i = preparedStatement.executeUpdate();
            connection.commit();
            if (i == 0) {
                throw new DaoException("Can not delete administrator");
            }

        } catch (SQLException e) {
            throw new DaoException("delete administrator from database failed");
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

            preparedStatement = connection.prepareStatement("update table Users " +
                    "set password = ?, " +
                    "name = ? , " +
                    "company = ?, " +
                    "tel = ?, " +
                    "type_user = cast(? as type_user)," +
                    "status = cast(? as states) "+
                    "where email = ? "
            );
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getCompany());
            preparedStatement.setString(4, user.getTel());
            preparedStatement.setString(5, user.getType() == User.UserType.Intern ? "Intern":"Administrator");
            preparedStatement.setString(6,user.getStatus() ? "Active":"Inactive");
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
