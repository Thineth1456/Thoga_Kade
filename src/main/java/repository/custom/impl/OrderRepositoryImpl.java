package repository.custom.impl;

import db.DBConnection;
import model.Order;
import model.OrderDetail;
import model.Voucher;
import repository.custom.ItemRepository;
import repository.custom.OrderRepository;
import repository.repositoryFactory;
import util.CrudUtil;
import util.RepositoryType;

import java.nio.Buffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    ItemRepository itemRepository = repositoryFactory.getInstance().getFactoryType(RepositoryType.ITEM);
    @Override
    public boolean save(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            PreparedStatement psTM = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?)");
            psTM.setObject(1,order.getOrderId());
            psTM.setObject(2,order.getOrderDate());
            psTM.setObject(3,order.getCustomerId());
            if (psTM.executeUpdate()>0){
                boolean isTrue = OrderDetailRepositoryImpl.getInstance().saveOrderDetail(order.getOrderDetails());
                if (isTrue){
                    boolean isUpdated = itemRepository.updateStock(order.getOrderDetails());
                    if (isUpdated){
                        connection.commit();
                        return true;
                    }
                }
            }

            connection.rollback();
            return false;
        }
        finally {
            connection.setAutoCommit(true);
        }
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

    @Override
    public boolean saveVoucher(Voucher voucher) throws SQLException {
       return CrudUtil.execute("INSERT INTO voucher VALUES(?,?,?,?)",voucher.getId(),voucher.getCustomerId(),voucher.getOrderId(),voucher.getDiscountPercentage());
    }

    @Override
    public List<Voucher> getAllVouchers() throws SQLException {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        ResultSet resultSet =  CrudUtil.execute("SELECT * FROM voucher");
        while (resultSet.next()){
            vouchers.add(new Voucher(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3), resultSet.getDouble(4)));
        }
        return vouchers;
    }

    @Override
    public boolean deleteVoucher(String id) throws SQLException {
        CrudUtil.execute("DELETE FROM voucher WHERE voucherNo = ?",id);
        return false;
    }
}
