package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import service.ServiceFactory;
import service.custom.ItemService;
import util.ServiceEnum;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    public Button btnUpdate;
    public Button deleteBtn;
    public Button searchBtn;
    @FXML
    private Button addButton;

    @FXML
    private ComboBox<?> boxCombo;

    @FXML
    private ComboBox<?> boxCombo1;

    @FXML
    private TableColumn colCategory;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colQuantity;

    @FXML
    private TableColumn colUprice;

    @FXML
    private TableColumn culId;

    @FXML
    private TableView itemTable;

    @FXML
    private Button loadTableButton;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    ItemService service = ServiceFactory.getInstance().getFactory(ServiceEnum.ITEM);
    @FXML
    void addButtonOnAction(ActionEvent event) {
        if(!txtId.getText().isEmpty()&&!txtName.getText().isEmpty()&&!txtQuantity.getText().isEmpty()&&!txtUnitPrice.getText().isEmpty()){
            try {
                service.save(new Item(txtId.getText(),
                        txtName.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQuantity.getText()))
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new Alert(Alert.AlertType.INFORMATION,"Added Success").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Added Not Success").show();
        }

    }

    @FXML
    void loadTableButtonOnAction(ActionEvent event) {
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        culId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        loadTable();
//        ObservableList titleList = FXCollections.observableArrayList();
//        titleList.add("ELECTRONIC");
//        titleList.add("CLOTHING");
//        titleList.add("STATIONARY");
//        titleList.add("GROCERY");
//        boxCombo.setItems(titleList);
//        boxCombo1.setItems(titleList);

    }
    ObservableList<Item> itemList;
    private void loadTable(){

        try {
           itemList = FXCollections.observableArrayList(service.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        itemTable.setItems(itemList);
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        if(!txtId.getText().isEmpty()&&!txtName.getText().isEmpty()&&!txtQuantity.getText().isEmpty()&&!txtUnitPrice.getText().isEmpty()){
            try {
                service.update(new Item(txtId.getText(),txtName.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQuantity.getText())));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Update Not Success").show();
        }


    }

    public void deleteBtnButtonOnAction(ActionEvent actionEvent) {
        try {
            service.delete(txtId.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchBtnButtonOnAction(ActionEvent actionEvent) {
        try {
            Item item = service.search(txtId.getText());
            txtId.setText(item.getCode());
            txtName.setText(item.getDescription());
            txtUnitPrice.setText(item.getUnitPrice().toString());
            txtQuantity.setText(item.getQtyOnHand().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
