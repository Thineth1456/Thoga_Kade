package service.custom;

import model.Order;
import model.Voucher;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface OrderService extends SuperService {
    boolean placeOrder(Order order) throws SQLException;
    boolean addVouchers(String cusId,double netTotal,String orderId) throws SQLException;
    String generateOrderId() throws SQLException;
    List<String> getVouchersByCustomer(String customerId) throws SQLException;
    boolean deleteVoucher(String id) throws SQLException;
    List<Order> getAll() throws SQLException;
}
