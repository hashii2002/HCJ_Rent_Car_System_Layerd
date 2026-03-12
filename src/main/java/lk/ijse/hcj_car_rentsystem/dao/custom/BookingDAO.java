package lk.ijse.hcj_car_rentsystem.dao.custom;

import lk.ijse.hcj_car_rentsystem.dao.CrudDAO;
import lk.ijse.hcj_car_rentsystem.dto.BookingDTO;
import lk.ijse.hcj_car_rentsystem.entity.Booking;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;

public interface BookingDAO extends CrudDAO<Booking> {
    //public boolean saveBooking(BookingDTO dto) throws SQLException;
    //public boolean updateBooking(BookingDTO dto) throws SQLException;
    //public boolean deleteBooking(int bookingId, int vehicleId, int driverId) throws SQLException;
    //public BookingDTO searchBooking(int id) throws SQLException;
    public BookingDTO searchBookingByCustomerID (int cusId) throws SQLException;
    //public List<BookingDTO> getBookings() throws SQLException;
    public void printBookingReports() throws JRException, SQLException;

    Booking search(Booking id) throws SQLException;

}
