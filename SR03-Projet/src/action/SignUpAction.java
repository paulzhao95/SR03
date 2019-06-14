package action;

import com.opensymphony.xwork2.ActionSupport;
import dao.DaoException;
import dao.DaoFactory;
import model.User;
import postgresqlImpl.administrator.UserImpl;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Random;

public class SignUpAction extends ActionSupport {
    private User user = new User();

    protected String generateRandomPassword(int n) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < n) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public String execute() throws DaoException {

        DaoFactory daoFactoryInstance = DaoFactory.getDaoFactoryInstance();

        UserImpl administratorUserImpl = daoFactoryInstance.getAdministratorUserImpl();

        String randomPassword = generateRandomPassword(6);

        user.setPassword(randomPassword);


        try {
            administratorUserImpl.addUser(user);

        } catch (DaoException e) {
            return ERROR;
        }

        sendPassword(user);

        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void sendPassword(User user) {
        // Recipient's email ID needs to be mentioned.
        String to = user.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "sr03project@gmail.com";

        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", "SR03Project1!");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");


        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "sr03project@gmail.com", "SR03Project1!");// Specify the Username and the PassWord
            }});

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Inscription Conformation");

            // Now set the actual message
            message.setText("Your email address: "+ user.getEmail() + "\n Your password : " + user.getPassword());

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SignUpAction loginAction = new SignUpAction();
        System.out.println(loginAction.generateRandomPassword(6));
    }

}
