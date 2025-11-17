package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ServiceFactory;
import service.ServiceFactory1;
import service.custom.CustomerService;
import service.custom.impl.CustomerServiceImpl;
import util.ServiceEnum;
import util.serviceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public JFXTextField cusEmail;

    public Label lblCustomerId;
    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TextField cusAddress;

    @FXML
   private TextField cusId;

    @FXML
    private TextField cusName;

    @FXML
    private TableView cusTable;

    @FXML
    private Button searchById;

    @FXML
    void addButtonOnAction(ActionEvent event) {
       if(!cusName.getText().isEmpty()&&!cusAddress.getText().isEmpty()&&!cusEmail.getText().isEmpty()){
           try {
               Customer c1 = new Customer(lblCustomerId.getText(),
                       cusName.getText(),
                       cusAddress.getText(),
                       cusEmail.getText());
               service.save(c1);
               System.out.println(c1);
               new Alert(Alert.AlertType.ERROR,"Customer Added Successful").show();
           } catch (SQLException e) {
               new Alert(Alert.AlertType.ERROR,"Customer Added Not Successfully").show();
           }
           loadTable();

           try {
               lblCustomerId.setText(service.generateCustomerId());
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           new Alert(Alert.AlertType.INFORMATION,"Added Success").show();
       }else {
           new Alert(Alert.AlertType.ERROR,"Added Not Success").show();
           cusName.setText("");
           cusAddress.setText("");
           cusEmail.setText("");
       }

    }



    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        try {
           service.delete(cusId.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
        try {
            lblCustomerId.setText(service.generateCustomerId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void searchButtonOnAction(ActionEvent event) {
        try {
            Customer customer = service.search(cusId.getText());
            cusId.setText(customer.getId());
            cusName.setText(customer.getName());
            cusAddress.setText(customer.getAddress());
            cusEmail.setText(customer.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        if(!cusName.getText().isEmpty()&&!cusAddress.getText().isEmpty()&&!cusEmail.getText().isEmpty()&&!cusId.getText().isEmpty()){
            try {
                service.update(new Customer(cusId.getText(),
                        cusName.getText(),
                        cusAddress.getText(),
                        cusEmail.getText()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            loadTable();
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update Not Success").show();
        }


    }
    CustomerService service = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);;
    //CustomerService service = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);
    //CustomerService service = new CustomerServiceImpl();
    ObservableList<Customer> customerObservableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblCustomerId.setText(service.generateCustomerId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadTable();
    }


    public void loadTable(){
        try {
            customerObservableList = FXCollections.observableArrayList(service.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cusTable.setItems(customerObservableList);
    }

    public void mousePressed(MouseEvent mouseEvent) {

    }

    public void selectRow(MouseEvent mouseEvent) {
        Customer selectedItem =(Customer) cusTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
    }
}
