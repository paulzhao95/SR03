package controller;

import model.Questionnaire;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import model.Questionnaire;
import java.sql.*;


public class CreateQuestionnaire extends HttpServlet {
    
    private static Hashtable<Integer, Questionnaire> questionnaire = new Hashtable<Integer, Questionnaire>();
    
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
            
            String competence = request.getParameter("competences");
            System.out.println(competence);
            ResultSet result = null;
            
            
            String sql_select_skills = "select * from \"Skills\" where \"Skill\" ='" + competence+"';";
            result = DatabaseHandler.Query(sql_select_skills);
            Boolean next = result.next();
            if (!next){
            String sql_insert = "INSERT INTO \"Skills\" (\"Skill\") VALUES ('"+ competence +"');";
            //System.out.println(sql_insert);
            DatabaseHandler.executeUpdate(sql_insert);
            }
            
            
            
            
            String sql_select = "select * from \"Questionnaires\"";
            result = DatabaseHandler.Query(sql_select);
            next = result.next();
            System.out.println(next);
            int i = 1;
            if (!next){
                questionnaire.put(questionnaire.size(), new Questionnaire(1,competence,"Active"));
                
            }
            else{
                while (result.next()){
                 i = i+1;
                }
                questionnaire.put(questionnaire.size(), new Questionnaire(i+1,competence,"Active"));
                
            }
            Questionnaire q = questionnaire.get(questionnaire.size() - 1);
            String sql_insert = "INSERT INTO \"Questionnaires\" (\"Number\", \"Theme\" , \"Status\") VALUES ("+q.getQuestionnaireID()+",'" +q.getTheme()+"','Active');";
            System.out.println(sql_insert);
            DatabaseHandler.executeUpdate(sql_insert);
            
            response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Controller:</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Questionnnaire crée </h1>");
            out.println("</body>");
            out.println("</html>");
        }
            
        } 
        
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
