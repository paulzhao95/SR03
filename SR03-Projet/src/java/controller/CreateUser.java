package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Administrator;
import model.User;

import java.util.Date;
import java.util.Calendar;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import model.Intern;

public class CreateUser extends HttpServlet {

    public static void main(String args[]){
        Connection databaseConnection = DatabaseHandler.getDatabaseConnection();
    }
    private static Hashtable<Integer, User> usersTable = new Hashtable<Integer, User>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        if (request.getParameter("Type_User") == "Administrator") {
            usersTable.put(usersTable.size(), new Administrator(request.getParameter("User email"), request.getParameter("User password"), request.getParameter("User name"), "Active", request.getParameter("User company"), request.getParameter("User telephone"), dateFormat.format(now)));
        } else {
            usersTable.put(usersTable.size(), new Intern(request.getParameter("User email"), request.getParameter("User password"), request.getParameter("User name"), "Active", request.getParameter("User company"), request.getParameter("User telephone"), dateFormat.format(now)));
        }

        /*TODO finish database insertion*/

        User user = usersTable.get(usersTable.size() - 1);
        String sql = "INSERT INTO \"Users\" (\"Password\",\"Status\",\"Name\",\"Company\",\"Tel\",\"Creating_time\",\"Email\",\"Type_user\") VALUES (" +"'"+ user.getPwd() +"'"+"," + "'Active'" +","+"'"+user.getName()+"'"+ ","+"'"+user.getCompany()+"'"+","+"'"+user.getTel()+"'"+","+"'"+user.getCreatingTime()+"'"+"," +"'"+user.getLogin()+"'"+","+"'"+user.getType()+"'"+")";

        try {
            DatabaseHandler.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Controller:</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Utilisateur crée " + usersTable.get(usersTable.size() - 1).toString() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}