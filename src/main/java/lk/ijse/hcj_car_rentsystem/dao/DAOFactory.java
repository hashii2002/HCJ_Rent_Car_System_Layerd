package lk.ijse.hcj_car_rentsystem.dao;

import lk.ijse.hcj_car_rentsystem.dao.custom.impl.*;

public class DAOFactory {
    public static DAOFactory daoFactory;
    private DAOFactory(){}
    public static DAOFactory getInstance(){
        return daoFactory==null? new DAOFactory():daoFactory;
    }
    public enum DAOType{
        CUSTOMER,BOOKING,DRIVER,USER,VEHICLE, PAYMENT, RENTALPAYMENT
    }

    public SuperDAO getDAO(DAOType daoType){
        switch(daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case BOOKING:
                return new BookingDAOImpl();
            case DRIVER:
                return new DriverDAOImpl();
            case USER:
                return new UserDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case RENTALPAYMENT:
                return new RentalPaymentDAOImpl();
            default:
                return null;
            }
        }

}
