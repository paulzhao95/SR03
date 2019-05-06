package controller;

import dao.DaoException;
import dao.DaoFactory;
import postgresqlImpl.UserImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends HttpServlet {

    private DaoFactory daoFactory;
    private UserImpl userImpl;


    public void init() {
        try {
            this.daoFactory = DaoFactory.getDaoFactoryInstance();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        userImpl = daoFactory.getAdministratorUserImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // just for test
        Connection connection = null;
        ResultSet re = null;
        try {
            connection = daoFactory.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            re = statement.executeQuery("select * from users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            req.setAttribute("eList", userImpl.getIntern("fdfa","aaa"));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("index1.jsp").forward(req,resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
