package backend.email;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailHTML {

    public static void sendmail(String username, String password, String EMAIL_TO, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        //Session session =null;
        Message message = new MimeMessage(session);
        System.out.println(message);
        message.setFrom(new InternetAddress("werfoodsaver@gmail.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(EMAIL_TO));
        message.setSubject(subject);
        // HTML email
        message.setDataHandler(new DataHandler(new HTMLDataSource(content)));
        //message.setText("<h1>Hello Java Mail \\n ABC123</h1>");

        Transport.send(message);

        System.out.println("Mail Sent Successfully");


    }


}