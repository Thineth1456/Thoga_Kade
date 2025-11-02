package repository;

import repository.custom.impl.CustomerRepositoryImpl;
import util.repoTypes;

public class RepositoryFactory1 {
    private static RepositoryFactory1 instance;
    private RepositoryFactory1(){}

    public static RepositoryFactory1 getInstance(){
       return instance == null? new RepositoryFactory1():instance;
    }

    public <T extends SuperRepository>T getFactory(repoTypes types){
        switch (types){
            case CUSTOMER:return (T) new CustomerRepositoryImpl();

        }
        return null;
    }


}
