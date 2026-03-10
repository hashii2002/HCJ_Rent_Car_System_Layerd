package lk.ijse.hcj_car_rentsystem.dao.custom.impl;

import lk.ijse.hcj_car_rentsystem.dao.custom.DriverDAO;
import lk.ijse.hcj_car_rentsystem.dto.DriverDTO;
import lk.ijse.hcj_car_rentsystem.entity.Driver;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {

    public boolean save(Driver driver) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "INSERT INTO drivers (name, contact, nic, license_no, address, status, notes) VALUES (?,?,?,?,?,?,?)",
                        driver.getName(),
                        driver.getContact(),
                        driver.getNic(),
                        driver.getLicenseNo(),
                        driver.getAddress(),
                        driver.getStatus(),
                        driver.getNotes()
                );

        return result;
    }

    public boolean update(Driver driver) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "UPDATE drivers SET name=?, contact=?, nic=?, license_no=?, address=?, status=?, notes=? WHERE driver_id=?",
                        driver.getName(),
                        driver.getContact(),
                        driver.getNic(),
                        driver.getLicenseNo(),
                        driver.getAddress(),
                        driver.getStatus(),
                        driver.getNotes(),
                        driver.getDriverId()

                );

        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result =
                CrudUtil.execute(
                        "DELETE FROM drivers WHERE driver_id=?",
                        id
                );

        return result;
    }

    public Driver search(int id) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM drivers WHERE driver_id=?",
                        id
                );

        if(rs.next()) {
            int driverId = rs.getInt("driver_id");
            String name = rs.getString("name");
            String contact = rs.getString("contact");
            String nic = rs.getString("nic");
            String licenseNo = rs.getString("license_no");
            String address = rs.getString("address");
            String status = rs.getString("status");
            String notes= rs.getString("notes");

            return new Driver(driverId, name, contact, nic, licenseNo, address, status, notes );
        }

        return null;
    }

    public DriverDTO searchDriverByName(String driverName) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM drivers WHERE name=?",
                        driverName
                );

        if(rs.next()) {
            int driverId = rs.getInt("driver_id");
            String name = rs.getString("name");
            String contact = rs.getString("contact");
            String nic = rs.getString("nic");
            String licenseNo = rs.getString("license_no");
            String address = rs.getString("address");
            String status = rs.getString("status");
            String notes= rs.getString("notes");

            return new DriverDTO(driverId, name, contact, nic, licenseNo, address, status, notes);
        }

        return null;
    }

    public List<Driver> get() throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM drivers ORDER BY driver_id DESC"
                );

        List<Driver> driverList = new ArrayList<>();

        while(rs.next()) {
            Driver driver = new Driver(
                    rs.getInt("driver_id"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("nic"),
                    rs.getString("license_no"),
                    rs.getString("address"),
                    rs.getString("status"),
                    rs.getString("notes")
            );

            driverList.add(driver);
        }

        return driverList;
    }

    public List<String> getAvailableDriverIds() throws SQLException {
        List<String> driverIds = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT driver_id FROM drivers WHERE status = 'available'");
        while (rs.next()) {
            driverIds.add(rs.getString("driver_id"));
        }
        return driverIds;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Driver search(Driver id) throws SQLException {
        return null;
    }
}
