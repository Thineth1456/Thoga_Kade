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
    ArrayList<Voucher> voucherArray = new ArrayList<>();
    @Override
    public boolean addVouchers(String cusId, double netTotal, String orderId) {


        if (netTotal>=10000){
            int voucherCount = (int)(netTotal/10000);
            while (voucherCount>0){
                String id = generateVoucherId();
                voucherArray.add(new Voucher(id,cusId,orderId,10.0));
                voucherCount--;


            }


            System.out.println(voucherArray);
        }
        return false;
    }

    String generateVoucherId(){
        ArrayList<String> VoucherId = new ArrayList<>();

        if (voucherArray.isEmpty()){
            return "V001";
        }
        String lstId = voucherArray.get(voucherArray.size()-1).getId();
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

    List<Order> getAll() throws SQLException {
        return repository.getAll();
    }


}
