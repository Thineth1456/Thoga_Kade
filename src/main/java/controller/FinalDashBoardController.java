package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class FinalDashBoardController implements Initializable {

    public Label lblDate;
    public Label lblTime;
    @FXML
    private Label MainLiable;

    @FXML
    private Button OrderBtn;

    @FXML
    private Button cusbtn;

    @FXML
    private Button itmbtn;

    @FXML
    private AnchorPane root;

    @FXML
    void bookBtnOnAction(ActionEvent event) throws IOException {



    }

    @FXML
    void cusBtnOnAction(ActionEvent event) throws IOException {
        MainLiable.setText("Customer Form");
        Parent load = FXMLLoader.load(getClass().getResource("../view/customerForm.fxml"));
        assert root != null;
        root.getChildren().clear();
        root.getChildren().add(load);
    }

    @FXML
    void itmBtnOnAction(ActionEvent event) throws IOException {
        MainLiable.setText("Item Form");
        Parent load = FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"));
        assert root!= null;
        root.getChildren().clear();
        root.getChildren().add(load);
    }

    public void aboutBtnOnAction(ActionEvent actionEvent) {

    }

    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        MainLiable.setText("Order Form");

        Parent load = FXMLLoader.load(getClass().getResource("../view/orderForm.fxml"));
        assert  root!= null;
        root.getChildren().clear();
        root.getChildren().add(load);
    }

    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        lblDate.setText(simpleDateFormat.format(date));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,e->{
                    LocalTime now = LocalTime.now();
                    lblTime.setText(now.getHour()+":"+now.getMinute()+":"+now.getSecond());
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void DashBtnOnAction(ActionEvent actionEvent) {

    }

    public void ItmBtnOnAction(ActionEvent actionEvent) {

    }

    public void customerBtnOnAction(ActionEvent actionEvent) {

    }

    public void aboutUsBtnOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
    }
}
