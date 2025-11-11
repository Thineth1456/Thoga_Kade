package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.logging.PlatformLogger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailFormController {

    public JFXTextArea txtMsg;
    public JFXTextField txtEmail;

    public void sendBtnOnAction(ActionEvent actionEvent) throws MessagingException {
        String msg = txtMsg.getText();
        String email = txtEmail.getText();

        sendEmail(email,msg);
    }

    private void sendEmail(String email, String msg) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myMail = "sandakelum1456@gmail.com";
        String password = "ngvu polp apsc fzdf";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myMail,password);
            }
        });
        Message message = prepareMessage(session,myMail,email,msg);
        if (message!=null){
            new Alert(Alert.AlertType.INFORMATION,"Send Email Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Not Success").show();
        }
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myMail, String email, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myMail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(email)
            });
            message.setSubject("Messages");
            message.setText(msg);
            return message;
        }catch (Exception e){
            Logger.getLogger(EmailFormController.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }
}
