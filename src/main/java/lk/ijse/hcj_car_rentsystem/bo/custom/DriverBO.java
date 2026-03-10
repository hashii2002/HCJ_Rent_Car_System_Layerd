package lk.ijse.hcj_car_rentsystem.bo.custom;

import lk.ijse.hcj_car_rentsystem.bo.SuperBO;
import lk.ijse.hcj_car_rentsystem.dto.DriverDTO;

import java.sql.SQLException;
import java.util.List;

public interface DriverBO extends SuperBO {
    public boolean saveDriver(DriverDTO driverDTO) throws SQLException;
    public boolean updateDriver(DriverDTO driverDTO) throws SQLException;
    public boolean deleteDriver(String id) throws SQLException;
    public DriverDTO searchDriver(String id) throws SQLException;
    public DriverDTO searchDriverByName(String driverName) throws SQLException;
    public List<DriverDTO> getDrivers() throws SQLException;
    public List<String> getAvailableDriverIds() throws SQLException;
}
