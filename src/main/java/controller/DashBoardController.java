package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Order;
import model.OrderDetail;
import repository.custom.ItemRepository;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import util.ServiceEnum;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashBoardController implements Initializable {
    ItemService itemService = ServiceFactory.getInstance().getFactory(ServiceEnum.ITEM);
    CustomerService customerService = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);
    OrderService orderService = ServiceFactory.getInstance().getFactory(ServiceEnum.ORDER);

    @FXML
    private Label customerCountlbl;

    @FXML
    private BarChart lowerItemGraph;

    @FXML
    private Label todayOrderlbl;

    @FXML
    private Label todaySaleslbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBars();
        setLabelValues();
        try {
            Integer noOfCustomers = customerService.getAll().size();
            customerCountlbl.setText(noOfCustomers.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setBars(){
        XYChart.Series set = new XYChart.Series<>();
        try {
            itemService.getAll().forEach(item->{
                if (item.getQtyOnHand()<50){
                    set.getData().add(new XYChart.Data(item.getDescription(),item.getQtyOnHand()));
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lowerItemGraph.getData().addAll(set);
    }

    public void setLabelValues(){
        Double totalSale=0.0;
        Integer orderCount=0;
        Date orderDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Order> all = null;
        try {
            all = orderService.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(int i=0;i<all.size();i++){
            System.out.println(all.get(i).getOrderDate());
            System.out.println(simpleDateFormat.format(orderDate));
            String dbFormat = simpleDateFormat.format(all.get(i).getOrderDate());
            String todayDate = simpleDateFormat.format(orderDate);
            if (dbFormat.equals(todayDate)){
                System.out.println("ok");
                ArrayList<OrderDetail> orderDetails = all.get(i).getOrderDetails();
                for (int j=0;j<orderDetails.size();j++){
                    totalSale += orderDetails.get(j).getQty() * orderDetails.get(j).getUnitPrice();
                }
            orderCount++;
            }
        }
        todayOrderlbl.setText(orderCount.toString());
        todaySaleslbl.setText(totalSale.toString());


    }
}
