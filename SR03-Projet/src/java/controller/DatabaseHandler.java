package controller;
import model.Intern;

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
