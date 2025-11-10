package repository.custom;

import model.Item;
import model.OrderDetail;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemRepository extends CrudRepository<Item,String> {
    boolean updateStock(ArrayList<OrderDetail> orderDetails) throws SQLException;
}
