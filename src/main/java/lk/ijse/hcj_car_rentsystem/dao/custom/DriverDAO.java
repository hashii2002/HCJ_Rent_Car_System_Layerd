package lk.ijse.hcj_car_rentsystem.dao.custom;

import lk.ijse.hcj_car_rentsystem.dao.CrudDAO;
import lk.ijse.hcj_car_rentsystem.dto.DriverDTO;
import lk.ijse.hcj_car_rentsystem.entity.Driver;

import java.sql.SQLException;
import java.util.List;

public interface DriverDAO extends CrudDAO<Driver> {

   // public boolean saveDriver(DriverDTO driverDTO) throws SQLException;
   // public boolean updateDriver(DriverDTO driverDTO) throws SQLException;
    //public boolean deleteDriver(int id) throws SQLException;
   // public DriverDTO searchDriver(int id) throws SQLException;
    public DriverDTO searchDriverByName(String driverName) throws SQLException;
   // public List<DriverDTO> getDrivers() throws SQLException;
    public List<String> getAvailableDriverIds() throws SQLException;
    boolean updateDriverStatus(int driverId, String assigned);
}
