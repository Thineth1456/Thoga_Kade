package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import util.ServiceEnum;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class OrderFormController implements Initializable {

    public JFXButton removeBtn;
    public JFXTextField orderId;
    public Label lblOrderId;
    public JFXComboBox cmbVoucher;
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
        String orderIds = lblOrderId.getText();
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
        try {
            orderService.addVouchers(order.getCustomerId(),Double.parseDouble(lblNetPrice.getText()),order.getOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (voucherId!=null){
            try {
                orderService.deleteVoucher(voucherId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        try {
            if (orderService.placeOrder(order)){
                new Alert(Alert.AlertType.INFORMATION,"Order Completed").show();
                generateBill(orderDetails,customerId,orderIds);
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Order Not Completed").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
        try {
            lblOrderId.setText(orderService.generateOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            txtSalary.setText(customer.getEmail().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            cmbVoucher.setItems(FXCollections.observableArrayList(orderService.getVouchersByCustomer(newValue)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectValue(MouseEvent mouseEvent) {

    }

   void getCustomerId(MouseEvent mouseEvent){

   }
   double calNetTotal(){
        Double netTotal = 0.0;
        for (OrderTM orderTM:cartList){
            netTotal += orderTM.getTotal();
        }
        lblNetPrice.setText(netTotal.toString());
       return netTotal;
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

    public void selectCustomerOnAction(ActionEvent actionEvent) {
        cmbVoucher.getSelectionModel().selectedItemProperty().addListener((observableValue,oldValue,newValue)->{
            if (newValue!=null){
                addDiscount((String)newValue);
            }
        });
    }
    String voucherId;
    private void addDiscount(String value) {
        Double netTotal = calNetTotal();
        if(netTotal>10000){
            netTotal = netTotal - 1000;
        }
        else{
            netTotal = netTotal-netTotal*0.1;
        }
        lblNetPrice.setText(netTotal.toString());
        voucherId = value;
    }

    private void generateBill(ArrayList<OrderDetail> orderDetails, String customerId, String orderIds) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/Blank_A4_3I.jrxml");
            Map<String, Object> params = new HashMap<>();
            params.put("orderIds", orderIds);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

