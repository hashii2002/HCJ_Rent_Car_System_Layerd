package lk.ijse.hcj_car_rentsystem.bo;

import lk.ijse.hcj_car_rentsystem.bo.custom.impl.*;

public class BOFactory {

    public static BOFactory instance;
    private BOFactory() {
    }
    public static BOFactory getInstance() {
        return instance ==null?new BOFactory():instance;
    }
    public enum BOType {
        CUSTOMER,BOOKING,DRIVER,USER,VEHICLE,RENTALPAYMENT
    }

    public SuperBO getBO(BOType type){
        switch(type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case BOOKING:
                return new BookingBOImpl();
            case DRIVER:
                return new DriverBOImpl();
            case USER:
                return new UserBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case RENTALPAYMENT:
                return new RentalPaymentBOImpl();
            default:
                return null;
        }
    }

}
