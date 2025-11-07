package repository.custom.impl;

import model.Order;
import model.OrderDetail;
import repository.custom.OrderRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public boolean save(Order order) throws SQLException {
        return false;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM orders");
        while (resultSet.next()){
            ArrayList<OrderDetail> orderItems = new ArrayList<>();
           ResultSet resultSet1 = CrudUtil.execute("SELECT * FROM orderDetail WHERE orderId =?",resultSet.getString(1));
           while (resultSet1.next()){
               orderItems.add(new OrderDetail(resultSet1.getString(1),resultSet1.getString(2),resultSet1.getInt(3),resultSet1.getDouble(4)));
           }
            orders.add(new Order(resultSet.getString(1),resultSet.getDate(2),resultSet.getString(3),orderItems));
        }
        System.out.println(orders);
        return orders;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public Order search(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Order order) throws SQLException {
        return false;
    }
}
