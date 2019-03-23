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


import java.text.SimpleDateFormat;

import model.Intern;

public class CreateUser extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String user_type = request.getParameter("Type_User");

        if (user_type.equals("Administrator")) {
            usersTable.put(usersTable.size(), new Administrator(request.getParameter("User email"), request.getParameter("User password"), request.getParameter("User name"), "Active", request.getParameter("User company"), request.getParameter("User telephone"), dateFormat.format(now)));
        } else {
            usersTable.put(usersTable.size(), new Intern(request.getParameter("User email"), request.getParameter("User password"), request.getParameter("User name"), "Active", request.getParameter("User company"), request.getParameter("User telephone"), dateFormat.format(now)));
        }


        User user = usersTable.get(usersTable.size() - 1);
        String sql = "INSERT INTO \"Users\" (\"Password\",\"Status\",\"Name\",\"Company\",\"Tel\",\"Creating_time\",\"Email\",\"Type_user\") VALUES (" +"'"+ user.getPwd() +"'"+"," + "'Active'" +","+"'"+user.getName()+"'"+ ","+"'"+user.getCompany()+"'"+","+"'"+user.getTel()+"'"+","+"'"+user.getCreatingTime()+"'"+"," +"'"+user.getLogin()+"'"+","+"'"+user.getType()+"'"+")";

        if (DatabaseHandler.userExists(user.getLogin())){

            /*TODO show user already exists*/
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>error:</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1> User already exists" + usersTable.get(usersTable.size() - 1).toString() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }

        }

        else {
            DatabaseHandler.executeUpdate(sql);
        }

        /* TODO show... if user creation successes. */
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

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
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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