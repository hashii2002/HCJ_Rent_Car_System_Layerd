package lk.ijse.hcj_car_rentsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.hcj_car_rentsystem.dto.VehicleDTO;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

public class VehicleModel {
    
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException  {
        
        boolean result = 
                CrudUtil.execute(
                        "INSERT INTO vehicles (model, brand, price_per_day, status, notes) VALUES (?,?,?,?,?)",
                        vehicleDTO.getModel(), 
                        vehicleDTO.getBrand(), 
                        vehicleDTO.getPricePerDay(),
                        vehicleDTO.getStatus(),
                        vehicleDTO.getNotes()
                );
        
        return result;
    }
    
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException {
        
        boolean result = 
                CrudUtil.execute(
                        "UPDATE vehicles SET model=?, brand=?, price_per_day=?, status=?, notes=? WHERE vehicle_id=?", 
                        vehicleDTO.getModel(), 
                        vehicleDTO.getBrand(), 
                        vehicleDTO.getPricePerDay(),
                        vehicleDTO.getStatus(),
                        vehicleDTO.getNotes(),
                        vehicleDTO.getVehicleId()
                );
        
        return result;   
    }
    
    public boolean deleteVehicle(int id) throws SQLException {
        boolean result = 
                CrudUtil.execute(
                        "DELETE FROM vehicles WHERE vehicle_id=?", 
                        id
                );
        
        return result;  
    }
    
    public VehicleDTO searchVehicle(int id) throws SQLException {
        
        ResultSet rs = 
                CrudUtil.execute(
                        "SELECT * FROM vehicles WHERE vehicle_id=?", 
                        id
                );
        
        if(rs.next()) {
            int vehicleId = rs.getInt("vehicle_id");
            String vehicleModel = rs.getString("model");
            String vehicleBrand = rs.getString("brand");
            double vehiclePrice = rs.getDouble("price_per_day");
            String vehicleStatus = rs.getString("status");
            String vehicleNotes = rs.getString("notes");
            
            return new VehicleDTO(vehicleId, vehicleModel, vehicleBrand, vehiclePrice, vehicleStatus,vehicleNotes );
        }
        
        return null;
    }
    
    public List<VehicleDTO> getVehicles() throws SQLException {
        ResultSet rs = 
                CrudUtil.execute(
                        "SELECT * FROM vehicles ORDER BY vehicle_id DESC" 
                );
        
        List<VehicleDTO> vehicleList = new ArrayList<>();
        
        while(rs.next()) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    rs.getInt("vehicle_id"), 
                    rs.getString("model"),
                    rs.getString("brand"), 
                    rs.getDouble("price_per_day"), 
                    rs.getString("status"),
                    rs.getString("notes")
                    
            );
            
            vehicleList.add(vehicleDTO);
        }
        
        return vehicleList;
    }
    
    public List<String> getAvailableVehicleIds() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT vehicle_id FROM vehicles WHERE status = 'available'"
        );

        List<String> vehicleIds = new ArrayList<>();
        while (rs.next()) {
            vehicleIds.add(rs.getString("vehicle_id"));
        }
        return vehicleIds;
    }
}
