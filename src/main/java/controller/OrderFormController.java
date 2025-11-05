package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import service.ServiceFactory;
import service.SuperService;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import service.custom.impl.CustomerServiceImpl;
import util.ServiceEnum;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public JFXButton removeBtn;
    public JFXTextField orderId;
    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn colUPrice;

    @FXML
    private JFXComboBox comBItem;

    @FXML
    private JFXComboBox combCustomer;

    @FXML
    private Label lblNetPrice;

    @FXML
    private TableView orderTbl;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCusName;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtUnitPrice;

    CustomerService service = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);
    ItemService itmService = ServiceFactory.getInstance().getFactory(ServiceEnum.ITEM);
    OrderService orderService = ServiceFactory.getInstance().getFactory(ServiceEnum.ORDER);
    //CustomerService service =  new CustomerServiceImpl();
    ArrayList<OrderTM> cartList = new ArrayList<>();
    @FXML
    void addToCartBtnOnAction(ActionEvent event) {

        Integer qty = Integer.parseInt(txtQuantity.getText());
        Double unitPrice =  Double.parseDouble(txtUnitPrice.getText());
        Double total = qty*unitPrice;
        cartList.add(new OrderTM(comBItem.getValue().toString(),
                                txtDescription.getText(),
                                qty,
                                unitPrice,
                                total));
        orderTbl.setItems(FXCollections.observableArrayList(cartList));
        calNetTotal();

    }

    @FXML
    void placeOrderBtnOnAction(ActionEvent event) {
        String orderIds = orderId.getText();
        Date orderDate = new Date();
        String customerId = combCustomer.getValue().toString();

        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        cartList.forEach(orders->{
            orderDetails.add(new OrderDetail(orderIds,
                            orders.getCode(),
                            orders.getQty(),
                            orders.getUnitPrice())
                    );
        });

        Order order = new Order(orderIds,orderDate,customerId,orderDetails);
        System.out.println(order);
        orderService.addVouchers(order.getCustomerId(),Double.parseDouble(lblNetPrice.getText()),order.getOrderId());


    }




    void setCustomerCombValue(){
        try {
            combCustomer.setItems(FXCollections.observableArrayList(service.getCustomerIds()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removeBtn.setVisible(false);

        setCustomerCombValue();
        setItemComboValue();
        combCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(oldValue);
            System.out.println(newValue);
            if (newValue!=null){
                setTextToValuesCustomer((String) newValue);
            }
        });

        comBItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                setItemTextValue((String) newValue);
            }
        });
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void setItemTextValue(String newValue) {
        try {
            Item itm = itmService.search(newValue);
            txtDescription.setText(itm.getDescription());
            txtUnitPrice.setText(itm.getUnitPrice().toString());
            txtQtyOnHand.setText(itm.getQtyOnHand().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setItemComboValue() {
        try {
            comBItem.setItems(FXCollections.observableArrayList(itmService.getItemCmbValues()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void setTextToValuesCustomer(String newValue){

        try {
            Customer customer = service.search(newValue);
            txtCusName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(customer.getSalary().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectValue(MouseEvent mouseEvent) {

    }

   void getCustomerId(MouseEvent mouseEvent){

   }
   void calNetTotal(){
        Double netTotal = 0.0;
        for (OrderTM orderTM:cartList){
            netTotal += orderTM.getTotal();
        }
        lblNetPrice.setText(netTotal.toString());
   }

    public void removeCartBtnOnAction(ActionEvent actionEvent) {
        OrderTM removeOrder;
        for(int i = 0;i<cartList.size();i++){
            if (cartList.get(i).getCode()==selectedItem.getCode()){
                cartList.remove(cartList.get(i));
            }
        }
        orderTbl.setItems(FXCollections.observableArrayList(cartList));
        removeBtn.setVisible(false);
    }
    OrderTM selectedItem;
    public void selectRow(MouseEvent mouseEvent) {
        removeBtn.setVisible(true);
        selectedItem =(OrderTM) orderTbl.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);


    }
}

