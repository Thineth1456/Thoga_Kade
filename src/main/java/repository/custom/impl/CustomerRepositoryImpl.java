package repository.custom.impl;

import model.Customer;
import repository.custom.CustomerRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {


    @Override
    public boolean save(Customer customer) throws SQLException {
        return CrudUtil.execute("INSERT INTO Customer VALUES(?,?,?,?)", customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary());
    }


    @Override
    public List<Customer> getAll() throws SQLException {

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");
            ArrayList<Customer> customerList = new ArrayList<>();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
                customerList.add(customer);
            }


        return customerList;
    }

    @Override
    public boolean delete(String s) throws SQLException {
       return CrudUtil.execute("DELETE FROM customer WHERE id = ?",s);
    }

    @Override
    public Customer search(String s) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE id = ?",s);
            resultSet.next();
       return  new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
       return CrudUtil.execute("UPDATE customer SET name=?,address = ? salary = ? WHERE id = ?",customer.getName(),customer.getAddress(),customer.getSalary(),customer.getId());
    }
}