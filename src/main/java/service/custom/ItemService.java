package service.custom;

import model.Item;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ItemService extends SuperService {
    boolean save(Item item) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(Item item) throws SQLException;
    Item search(String id) throws SQLException;
    List<Item> getAll() throws SQLException;
    List<String> getItemCmbValues() throws SQLException;
}
