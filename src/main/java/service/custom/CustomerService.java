package service.custom;

import model.Customer;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService extends SuperService {
    List<Customer> getAll() throws SQLException;
    boolean save(Customer customer) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(Customer customer) throws SQLException;
    Customer search(String id) throws SQLException;
    List<String> getCustomerIds() throws SQLException;
}
