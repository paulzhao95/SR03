package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;
import sun.misc.SignalHandler;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class SignUpAction extends ActionSupport {
    private User user = new User();

    public String execute() throws DaoException {

        DaoFactory daoFactoryInstance = DaoFactory.getDaoFactoryInstance();


        UserImpl administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();

        try {
            administratorUserImpl.addUser(user);

        } catch (DaoException e) {
            return ERROR;
        }

        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void sendEmail(User user) {
//        // Recipient's email ID needs to be mentioned.
//        String to = user.getEmail();
//
//        // Sender's email ID needs to be mentioned
//        String from = "web@gmail.com";
//
//        // Assuming you are sending email from localhost
//        String host = "localhost";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.setProperty("mail.smtp.host", host);
//
//        // Get the default Session object.
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
//
//            // Now set the actual message
//            message.setText("This is actual message");
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
    }

    public static void main(String[] args) {

        SignUpAction signUpAction = new SignUpAction();
        signUpAction.sendEmail(new User("longen.zhao@outlook.com"));
    }

}
