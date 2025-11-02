package service;

import service.custom.impl.CustomerServiceImpl;
import service.custom.impl.ItemServiceImpl;
import util.ServiceEnum;

public class ServiceFactory {
    private static ServiceFactory instance;
    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
                return instance == null ?new ServiceFactory():instance;
    }

    public <T extends SuperService>T getFactory(ServiceEnum type){
        switch (type){
            case CUSTOMER:return (T) new CustomerServiceImpl();
            case ITEM:return (T) new ItemServiceImpl();
        }
        return null;
    }
}
