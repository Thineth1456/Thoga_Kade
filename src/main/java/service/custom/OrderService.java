package service.custom;

import service.SuperService;

import java.sql.SQLException;

public interface OrderService extends SuperService {
    boolean addVouchers(String cusId,double netTotal,String orderId);
    String generateOrderId() throws SQLException;
}
