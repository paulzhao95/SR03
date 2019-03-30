package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

// TODO: 3/20/19 test createSkill
public class CreateTopic extends HttpServlet {

    protected void processCreateTopic(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        /*TODO get skill name */
        String skillName = "";

        String sql = "ALTER TYPE Skills ADD VALUE " +"'" +skillName +"'";
        DatabaseHandler.executeUpdate(sql);

        /*TODO show skill added */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processCreateTopic(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processCreateTopic(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
