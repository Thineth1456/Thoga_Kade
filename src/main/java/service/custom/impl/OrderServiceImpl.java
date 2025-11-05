package service.custom.impl;

import model.Voucher;
import service.custom.OrderService;

import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {
    @Override
    public boolean addVouchers(String cusId, double netTotal, String orderId) {
        ArrayList<Voucher> voucherArray = new ArrayList<>();
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

        if (VoucherId.isEmpty()){
            return "V001";
        }
        String lstId = VoucherId.get(VoucherId.size()-1);
        int num = Integer.parseInt(lstId.substring(1));
        num++;
        return String.format("V%03d",num);
    }

}
