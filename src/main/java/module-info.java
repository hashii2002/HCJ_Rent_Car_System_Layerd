module lk.ijse.hcj_car_rentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires lk.ijse.hcj_car_rentsystem;

    opens lk.ijse.hcj_car_rentsystem.controller to javafx.fxml;
    opens lk.ijse.hcj_car_rentsystem.dto to javafx.base;
    exports lk.ijse.hcj_car_rentsystem;
    exports lk.ijse.hcj_car_rentsystem.controller;
    exports lk.ijse.hcj_car_rentsystem.dto;
}
