package lk.ijse.hcj_car_rentsystem.bo.custom;

import lk.ijse.hcj_car_rentsystem.bo.SuperBO;
import lk.ijse.hcj_car_rentsystem.dto.BookingDTO;
import lk.ijse.hcj_car_rentsystem.entity.Booking;
import net.sf.jasperreports.engine.JRException;


import java.sql.SQLException;
import java.util.List;

public interface BookingBO extends SuperBO {

    boolean saveBooking(BookingDTO dto) throws SQLException;

    boolean updateBooking(BookingDTO dto) throws SQLException;

    boolean deleteBooking(int bookingId, int vehicleId, int driverId) throws SQLException;

    BookingDTO searchBooking(String id) throws SQLException;

    BookingDTO searchBookingByCustomerID(int cusId) throws SQLException;

    List<BookingDTO> getBookings() throws SQLException;

    void printBookingReports() throws SQLException, JRException;

}
