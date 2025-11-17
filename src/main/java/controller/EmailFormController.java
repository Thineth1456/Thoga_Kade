package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Customer;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.ServiceEnum;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailFormController implements Initializable {

    CustomerService customerService = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);

    public JFXTextArea txtMsg;
    public JFXTextField txtEmail;
    public JFXComboBox combCusId;

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
        String passkey = "ngvu polp apsc fzdf";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myMail,passkey);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            combCusId.setItems(FXCollections.observableArrayList(customerService.getCustomerIds()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        combCusId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                getEmail((String)newValue);
            }
        });
    }

    private void getEmail(String newValue) {
        try {
            Customer customer = customerService.search(newValue);
            txtEmail.setText(customer.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
