package repository;

import repository.custom.impl.CustomerRepositoryImpl;
import repository.custom.impl.ItemRepositoryImpl;
import repository.custom.impl.OrderRepositoryImpl;
import util.RepositoryType;

public class repositoryFactory {
    private static repositoryFactory instance;
    private repositoryFactory(){}

    public static repositoryFactory getInstance(){
     return    instance==null?new repositoryFactory():instance;
    }

    public <T extends SuperRepository>T getFactoryType(RepositoryType type){
        switch (type){
            case CUSTOMER:return (T) new  CustomerRepositoryImpl();
            case ITEM:return (T) new ItemRepositoryImpl();
            case ORDER:return (T) new OrderRepositoryImpl();
        }
        return null;
    }
}
