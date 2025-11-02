package service.custom.impl;

import model.Item;
import repository.custom.ItemRepository;
import repository.repositoryFactory;
import service.ServiceFactory;
import service.custom.ItemService;
import util.RepositoryType;
import util.ServiceEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    ItemRepository repository = repositoryFactory.getInstance().getFactoryType(RepositoryType.ITEM);
    @Override
    public boolean save(Item item) throws SQLException {
        return repository.save(item);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return repository.update(item);
    }

    @Override
    public Item search(String id) throws SQLException {
        return repository.search(id);
    }

    @Override
    public List<Item> getAll() throws SQLException {
        return repository.getAll();
    }

    @Override
    public List<String> getItemCmbValues() throws SQLException {
        ArrayList<String> itemCodes = new ArrayList<>();
        getAll().forEach(item->{
            itemCodes.add(item.getCode());
        });
       return itemCodes;
    }
}
