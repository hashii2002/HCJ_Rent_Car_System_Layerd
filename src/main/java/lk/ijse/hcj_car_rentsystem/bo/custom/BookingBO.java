package lk.ijse.hcj_car_rentsystem.bo.custom;

import lk.ijse.hcj_car_rentsystem.dto.BookingDTO;
import lk.ijse.hcj_car_rentsystem.entity.Booking;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;

public interface BookingBO {

    public boolean save(Booking booking) throws SQLException;
    public boolean update(Booking booking) throws SQLException;
    public boolean delete(int bookingId, int vehicleId, int driverId) throws SQLException;
    public Booking search(int id) throws SQLException;
    public BookingDTO searchBookingByCustomerID (int cusId) throws SQLException;
    public void printBookingReports() throws JRException, SQLException;


}
