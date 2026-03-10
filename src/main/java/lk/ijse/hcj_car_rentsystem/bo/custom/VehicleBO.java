package lk.ijse.hcj_car_rentsystem.bo.custom;

import lk.ijse.hcj_car_rentsystem.bo.SuperBO;
import lk.ijse.hcj_car_rentsystem.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.List;

public interface VehicleBO extends SuperBO {
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException;
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException;
    public boolean deleteVehicle(int id) throws SQLException;
    public VehicleDTO searchVehicle(int id) throws SQLException;
    public List<VehicleDTO> getVehicles() throws SQLException;
    public List<String> getAvailableVehicleIds() throws SQLException;
}
