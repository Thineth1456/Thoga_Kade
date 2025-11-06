package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private Date orderDate;

    public Order(String orderId, Date orderDate, String customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
    }

    private String customerId;
    private ArrayList<OrderDetail> orderDetails;
}
