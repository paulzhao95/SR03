package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CreateQuestion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
        
        String Question = request.getParameter("Question3");
        String AnswerA2 = request.getParameter("AnswerA4");
        String AnswerB2 = request.getParameter("AnswerB3");
        String AnswerC2 = request.getParameter("AnswerC1");
        String AnswerCorrect2 = request.getParameter("AnswerCorrect5");
        System.out.println(Question);
        System.out.println(AnswerA2);
        System.out.println(AnswerB2);
        System.out.println(AnswerC2);
        System.out.println(AnswerCorrect2);
        
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
