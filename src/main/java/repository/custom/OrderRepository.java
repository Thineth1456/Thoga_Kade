package repository.custom;

import model.Order;
import model.Voucher;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order,String> {
    boolean saveVoucher(Voucher voucher) throws SQLException;
    List<Voucher> getAllVouchers() throws SQLException;
    boolean deleteVoucher(String id) throws SQLException;
}
