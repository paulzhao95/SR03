package controller;

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
        String sql = "select * from \"User\" where \"Email\" = "+"'" +userLogin+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.close();
        statement.close();
        databaseConnection.close();
        Boolean next = resultSet.next();
        return next;
    }


    /*TODO test handle login*/
    public static Boolean handleLogin(String email, String pwd,String userType) throws SQLException, Exception {
        if (!userExists(email)){
            throw new Exception ("User does not exist");
        }

        Connection databaseConnection = getDatabaseConnection();
        Statement statement = databaseConnection.createStatement();
        String sql = "select * from \"User\" where \"Email\" = "+"'" +email+"'" + "and \"Password\" = "  +"'"  +pwd +"'" +"and \"Type_user\" =" +"'" +userType+"'" ;
        ResultSet resultSet = statement.executeQuery(sql);

        Boolean login = resultSet.next();

        resultSet.close();
        statement.close();
        databaseConnection.close();
        return login;







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
