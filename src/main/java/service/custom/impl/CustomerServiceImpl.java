package service.custom.impl;

import model.Customer;
import repository.CrudRepository;
import repository.RepositoryFactory1;
import repository.custom.CustomerRepository;
import repository.custom.impl.CustomerRepositoryImpl;
import repository.repositoryFactory;
import service.custom.CustomerService;
import util.RepositoryType;
import util.repoTypes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    CustomerRepository repository = repositoryFactory.getInstance().getFactoryType(RepositoryType.CUSTOMER);
    @Override
    public List<Customer> getAll() throws SQLException {
        return repository.getAll();
    }

    @Override
    public boolean save(Customer customer) throws SQLException {
        return repository.save(customer);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return repository.update(customer);
    }

    @Override
    public Customer search(String id) throws SQLException {
        return repository.search(id);
    }

    @Override
    public List<String> getCustomerIds() throws SQLException {
        ArrayList<String> cusIds = new ArrayList<>();
        getAll().forEach(customer -> {
            cusIds.add(customer.getId());
        });
        return cusIds;
    }
}
