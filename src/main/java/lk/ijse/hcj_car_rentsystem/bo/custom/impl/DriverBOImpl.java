package lk.ijse.hcj_car_rentsystem.bo.custom.impl;

import lk.ijse.hcj_car_rentsystem.bo.custom.DriverBO;
import lk.ijse.hcj_car_rentsystem.dao.DAOFactory;
import lk.ijse.hcj_car_rentsystem.dao.custom.DriverDAO;
import lk.ijse.hcj_car_rentsystem.dto.DriverDTO;
import lk.ijse.hcj_car_rentsystem.entity.Driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverBOImpl implements DriverBO {
    DriverDAO driverDAO = (DriverDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DRIVER);

    @Override
    public boolean saveDriver(DriverDTO driverDTO) throws SQLException{
        return driverDAO.save(new Driver(
                driverDTO.getName(),
                driverDTO.getContact(),
                driverDTO.getNic(),
                driverDTO.getLicenseNo(),
                driverDTO.getAddress(),
                driverDTO.getStatus(),
                driverDTO.getNotes()
        ));
    }

    @Override
    public boolean updateDriver(DriverDTO driverDTO) throws SQLException{
        return driverDAO.update(new Driver(
                driverDTO.getDriverId(),
                driverDTO.getName(),
                driverDTO.getContact(),
                driverDTO.getNic(),
                driverDTO.getLicenseNo(),
                driverDTO.getAddress(),
                driverDTO.getStatus(),
                driverDTO.getNotes()
        ));
    }

    @Override
    public boolean deleteDriver(String id) throws SQLException{
        return driverDAO.delete(id);
    }

    @Override
    public DriverDTO searchDriver(String id) throws SQLException{
        Driver driver = driverDAO.search(id);

        if(driver != null){
            return new DriverDTO(
                    driver.getDriverId(),
                    driver.getName(),
                    driver.getContact(),
                    driver.getNic(),
                    driver.getLicenseNo(),
                    driver.getAddress(),
                    driver.getStatus(),
                    driver.getNotes()
            );
        }
        return null;
    }

    @Override
    public DriverDTO searchDriverByName(String driverName) throws SQLException{
        DriverDTO driver = driverDAO.searchDriverByName(driverName);
        if(driver != null){
            return new DriverDTO(
                    driver.getDriverId(),
                    driver.getName(),
                    driver.getContact(),
                    driver.getNic(),
                    driver.getLicenseNo(),
                    driver.getAddress(),
                    driver.getStatus(),
                    driver.getNotes()
            );
        }
        return null;
    }

    @Override
    public List<DriverDTO> getDrivers() throws SQLException{
        List<Driver> drivers = driverDAO.get();
        List<DriverDTO> driverDTOList = new ArrayList<>();

        for (Driver driver : drivers) {
            driverDTOList.add(new DriverDTO(
                    driver.getDriverId(),
                    driver.getName(),
                    driver.getContact(),
                    driver.getNic(),
                    driver.getLicenseNo(),
                    driver.getAddress(),
                    driver.getStatus(),
                    driver.getNotes()
            ));
        }
        return driverDTOList;
    }
    @Override
    public List<String> getAvailableDriverIds() throws SQLException{
        return driverDAO.getAvailableDriverIds();
    }
}
