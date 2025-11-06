package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;
import service.ServiceFactory;
import service.ServiceFactory1;
import service.custom.CustomerService;
import service.custom.impl.CustomerServiceImpl;
import util.ServiceEnum;
import util.serviceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

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
    private TextField cusSalary;

    @FXML
    private TableView cusTable;

    @FXML
    private Button searchById;

    @FXML
    void addButtonOnAction(ActionEvent event) {
        try {
            Customer c1 = new Customer(cusId.getText(),
                    cusName.getText(),
                    cusAddress.getText(),
                    Double.parseDouble(cusSalary.getText()));
            service.save(c1);
            System.out.println(c1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();



    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        try {
           service.delete(cusId.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }




    @FXML
    void searchButtonOnAction(ActionEvent event) {
        try {
            Customer customer = service.search(cusId.getText());
            cusId.setText(customer.getId());
            cusName.setText(customer.getName());
            cusAddress.setText(customer.getAddress());
            cusSalary.setText(customer.getSalary().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        try {
            service.update(new Customer(cusId.getText(),
                                        cusName.getText(),
                                        cusAddress.getText(),
                                        Double.parseDouble(cusSalary.getText())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }
    CustomerService service = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);;
    //CustomerService service = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);
    //CustomerService service = new CustomerServiceImpl();
    ObservableList<Customer> customerObservableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

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
