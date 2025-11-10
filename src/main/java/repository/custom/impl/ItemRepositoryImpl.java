package repository.custom.impl;

import model.Item;
import model.OrderDetail;
import repository.custom.ItemRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public boolean save(Item item) throws SQLException {
        return CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?)",item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public List<Item> getAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM item");
        ArrayList<Item> itemArray = new ArrayList<>();
        while (resultSet.next()){
            Item item = new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
            itemArray.add(item);
        }
        return itemArray;
    }

    @Override
    public boolean delete(String code) throws SQLException {
        return CrudUtil.execute("DELETE FROM item WHERE code = ?",code);
    }

    @Override
    public Item search(String code) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM item WHERE code = ?",code);
        resultSet.next();
        return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4));
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return CrudUtil.execute("UPDATE item SET description = ?,unitPrice = ?,qtyOnHand = ? WHERE code = ?");
    }

    @Override
    public boolean updateStock(ArrayList<OrderDetail> orderDetails) throws SQLException {
        for(OrderDetail orderDetail:orderDetails){
            boolean isTrue = updateStock(orderDetail);
            if (!isTrue){
                return false;
            }
        }
        return true;

    }

    boolean updateStock(OrderDetail orderDetail) throws SQLException {
        return CrudUtil.execute("UPDATE item set qtyOnHand  = qtyOnHand - ? WHERE code = ?",
                orderDetail.getQty(),
                orderDetail.getItemCode());
    }
}
