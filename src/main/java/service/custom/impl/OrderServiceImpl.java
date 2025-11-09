package service.custom.impl;

import model.Order;
import model.Voucher;
import repository.custom.OrderRepository;
import repository.repositoryFactory;
import service.custom.OrderService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderRepository repository =repositoryFactory.getInstance().getFactoryType(RepositoryType.ORDER);;


    @Override
    public boolean placeOrder(Order order) throws SQLException {
        System.out.println("service : "+order);
        return repository.save(order);
    }

    // ArrayList<Voucher> voucherArray = new ArrayList<>();
    @Override
    public boolean addVouchers(String cusId, double netTotal, String orderId) throws SQLException {


        if (netTotal>=20000){
            int voucherCount = (int)(netTotal/20000);
            while (voucherCount>0){
                String id = generateVoucherId();
                Voucher voucher = new Voucher(id, cusId, orderId, 10.0);
                repository.saveVoucher(voucher);
                voucherCount--;


            }
        }
        return false;
    }

    String generateVoucherId() throws SQLException {
        ArrayList<String> VoucherId = new ArrayList<>();
        List<Voucher> allVouchers = repository.getAllVouchers();
        if (allVouchers.isEmpty()){
            return "V001";
        }
        String lstId = allVouchers.get(allVouchers.size()-1).getId();
        int num = Integer.parseInt(lstId.substring(1));
        num++;
        return String.format("V%03d",num);
    }

    public String generateOrderId() throws SQLException {
        if (getAll().isEmpty()){
            return "D001";
        }
        String lstId = getAll().get(getAll().size()-1).getOrderId();
        int num = Integer.parseInt(lstId.substring(1));
        num++;
        return String.format("D%03d",num);
    }

    @Override
    public List<String> getVouchersByCustomer(String customerId) throws SQLException {
        ArrayList<String> customerVoucher  = new ArrayList<>();
        repository.getAllVouchers().forEach(voucher -> {
            if (voucher.getCustomerId().equals(customerId)){
                customerVoucher.add(voucher.getId());
            }
        });
        return customerVoucher;
    }

    @Override
    public boolean deleteVoucher(String id) throws SQLException {
        return repository.deleteVoucher(id);
    }

    List<Order> getAll() throws SQLException {
        return repository.getAll();
    }





}
