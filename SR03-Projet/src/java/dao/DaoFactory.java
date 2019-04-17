package dao;

import postgresqlHandler.UserHandler;
import postgresqlHandler.administrator.AdministratorQuestionnaireHandler;
import postgresqlHandler.administrator.AdministratorQuestionHandler;
import postgresqlHandler.administrator.TopicHandler;
import postgresqlHandler.QuestionnaireHandler;
import postgresqlHandler.intern.AttemptHandler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.*;


public class DaoFactory {

    private static final String FILE_PROPERTIES = "dao/dao.properties";
    private static final String PROPERTY_RDBMS = "rdbms";
    private static final String PROPERTY_HOST = "host";
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_DATABASE = "database";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USER_NAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private String rdbms;
    private String host;
    private String port;
    private String driver;
    private String databaseName;
    private String userName;
    private String password;

    DaoFactory(String rdbms, String host, String port, String databaseName, String driver, String userName, String password) {
        this.rdbms = rdbms;
        this.host = host;
        this.port = port;
        this.driver = driver;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDriver() {
        return driver;
    }


    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getRdbms() {
        return rdbms;
    }

    public String getUserName() {
        return userName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setRdbms(String rdbms) {
        this.rdbms = rdbms;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String getConnectionString() {
        System.out.println("jdbc:" + this.getRdbms() + "://" + this.getHost() + ":" + this.getPort() + "/" + getDatabaseName());
        return "jdbc:" + this.getRdbms() + "://" + this.getHost() + ":" + this.getPort() + "/" + getDatabaseName();
    }

    public static DaoFactory newDaoFactory() throws DaoException {

        Properties properties = new Properties();


        String rdbms;
        String host;
        String port;
        String driver;
        String databaseName;
        String userName;
        String password;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fileProperties = classLoader.getResourceAsStream(FILE_PROPERTIES);

        if (fileProperties == null) {
            throw new DaoException(FILE_PROPERTIES + " not found.");
        }

        try {
            properties.load(fileProperties);
            rdbms = properties.getProperty(PROPERTY_RDBMS);
            host = properties.getProperty(PROPERTY_HOST);
            port = properties.getProperty(PROPERTY_PORT);
            databaseName = properties.getProperty(PROPERTY_DATABASE);
            driver = properties.getProperty(PROPERTY_DRIVER);
            userName = properties.getProperty(PROPERTY_USER_NAME);
            password = properties.getProperty(PROPERTY_PASSWORD);

        } catch (IOException e) {
            throw new DaoException("Can not get connection properties" + FILE_PROPERTIES + ".");
        }

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ignored) {

        }

        return new DaoFactory(rdbms, host, port, databaseName, driver, userName, password);
    }

    public Connection getConnection() throws SQLException {
        Connection connexion = DriverManager.getConnection(getConnectionString(), getUserName(), getPassword());
        connexion.setAutoCommit(false);
        return connexion;
    }

    public postgresqlHandler.administrator.UserHandler getAdministratorUserHandler() {
        return new postgresqlHandler.administrator.UserHandler(this);
    }

    public AdministratorQuestionnaireHandler getQuestionnaireHandler() {
        return new AdministratorQuestionnaireHandler(this);
    }

    public AdministratorQuestionHandler getQuestionHandler() {
        return new AdministratorQuestionHandler(this);
    }

    public TopicHandler getTopicHandler() {
        return new TopicHandler(this);
    }

    public QuestionnaireHandler getActiveQuestionnaireHandler() {
        return new QuestionnaireHandler(this);
    }

    public AttemptHandler getShowEvaluationHandler() {
        return new AttemptHandler(this);
    }

    public UserHandler getUserLoginHandler(){
        return new UserHandler(this);
    }


    public static void main(String[] args) throws DaoException, SQLException {
        DaoFactory f = newDaoFactory();
        Connection connection = f.getConnection();

    }

}
