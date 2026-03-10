package lk.ijse.hcj_car_rentsystem.bo.custom.impl;

import lk.ijse.hcj_car_rentsystem.bo.custom.BookingBO;
import lk.ijse.hcj_car_rentsystem.bo.custom.VehicleBO;
import lk.ijse.hcj_car_rentsystem.dao.DAOFactory;
import lk.ijse.hcj_car_rentsystem.dao.custom.VehicleDAO;
import lk.ijse.hcj_car_rentsystem.dto.VehicleDTO;
import lk.ijse.hcj_car_rentsystem.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

    @Override
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.save(new Vehicle(
                vehicleDTO.getModel(),
                vehicleDTO.getBrand(),
                vehicleDTO.getPricePerDay(),
                vehicleDTO.getStatus(),
                vehicleDTO.getNotes()
        ));

    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.update(new Vehicle(
                vehicleDTO.getVehicleId(),
                vehicleDTO.getModel(),
                vehicleDTO.getBrand(),
                vehicleDTO.getPricePerDay(),
                vehicleDTO.getStatus(),
                vehicleDTO.getNotes()
        ));
    }

    @Override
    public boolean deleteVehicle(int id) throws SQLException{
        return vehicleDAO.delete(String.valueOf(id));
    }

    @Override
    public VehicleDTO searchVehicle(int id) throws SQLException {
        Vehicle vehicle = vehicleDAO.search(String.valueOf(id));

        if (vehicle != null) {
            return new VehicleDTO(
                    vehicle.getVehicleId(),
                    vehicle.getModel(),
                    vehicle.getBrand(),
                    vehicle.getPricePerDay(),
                    vehicle.getStatus(),
                    vehicle.getNotes()
            );
        }
        return null;
    }

    @Override
    public List<VehicleDTO> getVehicles() throws SQLException{
        List<Vehicle> vehicles = vehicleDAO.get();
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            vehicleDTOList.add(new VehicleDTO(
                    vehicle.getVehicleId(),
                    vehicle.getModel(),
                    vehicle.getBrand(),
                    vehicle.getPricePerDay(),
                    vehicle.getStatus(),
                    vehicle.getNotes()
            ));
        }
        return vehicleDTOList;
    }

    @Override
    public List<String> getAvailableVehicleIds() throws SQLException{
        return vehicleDAO.getAvailableVehicleIds();
    }
}
