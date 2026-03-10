package lk.ijse.hcj_car_rentsystem.dao.custom;

import lk.ijse.hcj_car_rentsystem.dao.SuperDAO;
import lk.ijse.hcj_car_rentsystem.dto.*;
import lk.ijse.hcj_car_rentsystem.entity.Booking;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    public List<CustomDTO> get() throws SQLException;
    public List<BookingDTO> getBookings() throws SQLException;

}
