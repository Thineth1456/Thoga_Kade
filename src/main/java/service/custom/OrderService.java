package service.custom;

import service.SuperService;

public interface OrderService extends SuperService {
    boolean addVouchers(String cusId,double netTotal,String orderId);
}
