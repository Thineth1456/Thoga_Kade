package service;

import repository.RepositoryFactory1;
import repository.SuperRepository;
import repository.custom.impl.CustomerRepositoryImpl;
import service.custom.impl.CustomerServiceImpl;
import util.repoTypes;
import util.serviceType;

public class ServiceFactory1 {
    private static ServiceFactory1 instance;
    private ServiceFactory1(){}

    public static ServiceFactory1 getInstance(){
        return instance == null? new ServiceFactory1():instance;
    }

    public <T extends SuperService>T getFactory(serviceType types){
        switch (types){
            case CUSTOMER:return (T) new CustomerServiceImpl();

        }
        return null;
    }


}
