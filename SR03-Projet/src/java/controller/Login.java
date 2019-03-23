package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

// TODO: 3/20/19 test login
public class Login extends HttpServlet {

    protected void processLogin(HttpServletRequest req, HttpServletResponse resp){
        String email = req.getParameter("User email");
        String pwd = req.getParameter("User password");
        String userType = req.getParameter("Type_user");

        Boolean loginSuccessful = false;
        try {
            loginSuccessful = DatabaseHandler.handleLogin(email,pwd,userType);
        } catch ( Exception e) {
            if (e.getMessage().equals("User does not exist")){
                /*TODO show user does not exist*/
            }
        }
        if (loginSuccessful){
            /*TODO show login successful info */
        }
        else {
            /*TODO show wrong pass word info*/
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processLogin(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processLogin(req,resp);
    }
}
