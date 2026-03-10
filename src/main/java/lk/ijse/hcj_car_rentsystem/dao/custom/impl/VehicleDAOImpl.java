package lk.ijse.hcj_car_rentsystem.dao.custom.impl;

import lk.ijse.hcj_car_rentsystem.dao.custom.VehicleDAO;
import lk.ijse.hcj_car_rentsystem.dto.VehicleDTO;
import lk.ijse.hcj_car_rentsystem.entity.Vehicle;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {

    public boolean save(Vehicle vehicle) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "INSERT INTO vehicles (model, brand, price_per_day, status, notes) VALUES (?,?,?,?,?)",
                        vehicle.getModel(),
                        vehicle.getBrand(),
                        vehicle.getPricePerDay(),
                        vehicle.getStatus(),
                        vehicle.getNotes()
                );

        return result;
    }

    public boolean update(Vehicle vehicle) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "UPDATE vehicles SET model=?, brand=?, price_per_day=?, status=?, notes=? WHERE vehicle_id=?",
                        vehicle.getModel(),
                        vehicle.getBrand(),
                        vehicle.getPricePerDay(),
                        vehicle.getStatus(),
                        vehicle.getNotes(),
                        vehicle.getVehicleId()
                );

        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result =
                CrudUtil.execute(
                        "DELETE FROM vehicles WHERE vehicle_id=?",
                        id
                );

        return result;
    }

    public Vehicle search(int id) throws SQLException {

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

            return new Vehicle(vehicleId, vehicleModel, vehicleBrand, vehiclePrice, vehicleStatus,vehicleNotes );
        }

        return null;
    }

    public List<Vehicle> get() throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM vehicles ORDER BY vehicle_id DESC"
                );

        List<Vehicle> vehicleList = new ArrayList<>();

        while(rs.next()) {
            Vehicle vehicle = new Vehicle(
                    rs.getInt("vehicle_id"),
                    rs.getString("model"),
                    rs.getString("brand"),
                    rs.getDouble("price_per_day"),
                    rs.getString("status"),
                    rs.getString("notes")

            );

            vehicleList.add(vehicle);
        }

        return vehicleList;
    }

    @Override
    public VehicleDTO searchVehicle(int id) throws SQLException {
        return null;
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

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Vehicle search(Vehicle id) throws SQLException {
        return null;
    }
}
