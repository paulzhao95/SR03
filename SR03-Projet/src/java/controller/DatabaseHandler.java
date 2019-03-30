package controller;

import model.Question;
import model.Questionnaire;

import java.sql.*;


public class DatabaseHandler {

    public static Connection getDatabaseConnection(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://172.17.0.2:5432/postgres",
                            "postgres", "123456");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }
    /**
     * Execute no-return query : create, insert
     *
     * @param sql
     */
    public static void executeUpdate(String sql) throws SQLException {
        Connection databaseConnection = getDatabaseConnection();
        Statement statement = databaseConnection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        /*no need to commit cause auto commit is on*/
        databaseConnection.close();
    }

    public static Boolean userExists(String userLogin) throws SQLException {
        Connection databaseConnection = getDatabaseConnection();
        Statement statement = databaseConnection.createStatement();
        String sql = "select * from Users where Email = "+"'" +userLogin+"'";

        ResultSet resultSet = statement.executeQuery(sql);
        Boolean next = resultSet.next();
        resultSet.close();
        statement.close();
        databaseConnection.close();
        return next;
    }


    /*TODO test handle login*/
    public static Boolean handleLogin(String email, String pwd,String userType) throws SQLException, Exception {
        if (!userExists(email)){
            throw new Exception ("User does not exist");
        }

        Connection databaseConnection = getDatabaseConnection();
        Statement statement = databaseConnection.createStatement();
        String sql = "select * from User where Email = "+"'" +email+"'" + "and Password = "  +"'"  +pwd +"'" +"and Type_user =" +"'" +userType+"'" ;
        ResultSet resultSet = statement.executeQuery(sql);

        Boolean login = resultSet.next();

        resultSet.close();
        statement.close();
        databaseConnection.close();
        return login;

    }

    public static void InsertQuestionnaire(Questionnaire questionnaire) throws SQLException {
        // insert questionnqire info
        Connection databaseConnection = getDatabaseConnection();
        Statement statement = databaseConnection.createStatement();
        statement.executeUpdate("insert into Questionnaires VALUE (" + "'" + questionnaire.getQuestionnaireID() + "'," + "'" + questionnaire.getTopic()+"',"+ "'" + questionnaire.getStatus()+"')" );
        // TODO: 3/20/19 insert componants of questionnaire
    }

    public static void InsertQuestion(Question question) throws SQLException {
        Connection databaseConnection = getDatabaseConnection();
        Statement statement = databaseConnection.createStatement();
//        statement.executeUpdate("insert into Questions value ("+ "'" + question.get() + "',");
    }


//    public static void executeQuery(String sql) throws SQLException {
//        Connection databaseConnection = getDatabaseConnection();
//        Statement statement = databaseConnection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        ResultSetMetaData rsmd = resultSet.getMetaData();
//
//        Integer index = 0;
//        while (resultSet.next()){
//            index ++;
//            String columnName = rsmd.getColumnName(index);
//            resultSet.get
//        }
//
//    }




}
