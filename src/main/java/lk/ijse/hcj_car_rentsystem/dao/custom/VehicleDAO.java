package lk.ijse.hcj_car_rentsystem.dao.custom;

import lk.ijse.hcj_car_rentsystem.dao.CrudDAO;
import lk.ijse.hcj_car_rentsystem.dto.VehicleDTO;
import lk.ijse.hcj_car_rentsystem.entity.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleDAO extends CrudDAO<Vehicle> {

    //public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException;
   // public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException;
   // public boolean deleteVehicle(int id) throws SQLException;
    public VehicleDTO searchVehicle(int id) throws SQLException;
   // public List<VehicleDTO> getVehicles() throws SQLException;
    public List<String> getAvailableVehicleIds() throws SQLException;
    boolean updateVehicleStatus(int vehicleId, String rented);
}
