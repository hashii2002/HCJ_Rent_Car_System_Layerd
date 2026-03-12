package lk.ijse.hcj_car_rentsystem.bo.custom.impl;

import lk.ijse.hcj_car_rentsystem.bo.custom.BookingBO;
import lk.ijse.hcj_car_rentsystem.dao.DAOFactory;
import lk.ijse.hcj_car_rentsystem.dao.custom.BookingDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.DriverDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.QueryDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.VehicleDAO;
import lk.ijse.hcj_car_rentsystem.db.DBConnection;
import lk.ijse.hcj_car_rentsystem.dto.BookingDTO;
import lk.ijse.hcj_car_rentsystem.dto.CustomDTO;
import lk.ijse.hcj_car_rentsystem.entity.Booking;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookingBOImpl implements BookingBO {
    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BOOKING);

    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

    DriverDAO driverDAO = (DriverDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DRIVER);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public boolean saveBooking(BookingDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String bookingSql = "INSERT INTO bookings (customer_id, booking_date, start_date, end_date, booking_status, advance_amount, notes, driver_assign) VALUES (?,?,?,?,?,?,?,?)";

            java.sql.PreparedStatement pstm = connection.prepareStatement(bookingSql, java.sql.Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, dto.getCustomerId());
            pstm.setString(2, dto.getBookingDate());
            pstm.setString(3, dto.getStartDate());
            pstm.setString(4, dto.getEndDate());
            pstm.setString(5, dto.getBookingStatus());
            pstm.setDouble(6, dto.getAdvanceAmount());
            pstm.setString(7, dto.getNotes());
            pstm.setString(8, dto.getDriverAssign());

            if (pstm.executeUpdate() > 0) {
                var rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    int newBookingId = rs.getInt(1);

                    boolean isVehicleAssigned = CrudUtil.execute(
                            "INSERT INTO vehicle_assign_details VALUES (?,?)",
                            newBookingId, dto.getVehicleId()
                    );

                    boolean isVehicleUpdated = CrudUtil.execute(
                            "UPDATE vehicles SET status = 'rented' WHERE vehicle_id = ?",
                            dto.getVehicleId()
                    );

                    if (isVehicleAssigned && isVehicleUpdated) {

                        if (dto.getDriverAssign().equalsIgnoreCase("Yes")) {
                            boolean isDriverAssigned = CrudUtil.execute(
                                    "INSERT INTO driver_assign_details VALUES (?,?,?)",
                                    newBookingId, dto.getVehicleId(), dto.getDriverId()
                            );
                            boolean isDriverUpdated = CrudUtil.execute(
                                    "UPDATE drivers SET status = 'assigned' WHERE driver_id = ?",
                                    dto.getDriverId()
                            );

                            if (!isDriverAssigned || !isDriverUpdated) {
                                connection.rollback();
                                return false;
                            }
                        }
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updateBooking(BookingDTO dto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();

        try {

            connection.setAutoCommit(false);

            boolean isBookingUpdated = bookingDAO.update(new Booking(
                    dto.getCustomerId(),
                    dto.getBookingDate(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    dto.getBookingStatus(),
                    dto.getAdvanceAmount(),
                    dto.getNotes(),
                    dto.getDriverAssign(),
                    dto.getBookingId()
            ));

            if (isBookingUpdated) {

                // reset vehicle status
                boolean isVehicleUpdated = vehicleDAO.updateVehicleStatus(
                        dto.getVehicleId(),
                        "rented"
                );

                boolean isDriverHandled = true;

                if (dto.getDriverAssign().equalsIgnoreCase("Yes")) {

                    isDriverHandled = driverDAO.updateDriverStatus(
                            dto.getDriverId(),
                            "assigned"
                    );
                }

                if (isVehicleUpdated && isDriverHandled) {
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;

        } catch (Exception e) {

            connection.rollback();
            throw e;

        } finally {

            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean deleteBooking(int bookingId, int vehicleId, int driverId) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();

        try {

            connection.setAutoCommit(false);

            boolean isBookingDeleted = bookingDAO.delete(String.valueOf(bookingId));

            if (isBookingDeleted) {

                boolean isVehicleReleased =
                        vehicleDAO.updateVehicleStatus(vehicleId, "available");

                boolean isDriverReleased = true;

                if (driverId > 0) {

                    isDriverReleased =
                            driverDAO.updateDriverStatus(driverId, "available");
                }

                if (isVehicleReleased && isDriverReleased) {

                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;

        } catch (Exception e) {

            connection.rollback();
            throw e;

        } finally {

            connection.setAutoCommit(true);
        }
    }

    @Override
    public BookingDTO searchBooking(String id) throws SQLException {

        Booking booking = bookingDAO.search(String.valueOf(id));

        if (booking == null) {
            return null;
        }

        return new BookingDTO(
                booking.getBookingId(),
                booking.getCustomerId(),
                booking.getVehicleId(),
                booking.getDriverId(),
                booking.getBookingDate(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getBookingStatus(),
                booking.getAdvanceAmount(),
                booking.getNotes(),
                booking.getDriverAssign()
        );
    }

    @Override
    public BookingDTO searchBookingByCustomerID(int cusId) throws SQLException {

        BookingDTO booking = bookingDAO.searchBookingByCustomerID(cusId);

        if (booking == null) {
            return null;
        }

        return new BookingDTO(
                booking.getBookingId(),
                booking.getCustomerId(),
                booking.getVehicleId(),
                booking.getDriverId(),
                booking.getBookingDate(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getBookingStatus(),
                booking.getAdvanceAmount(),
                booking.getNotes(),
                booking.getDriverAssign()
        );
    }

    @Override
    public List<BookingDTO> getBookings() throws SQLException {

        List<CustomDTO> bookingList = queryDAO.get();

        List<BookingDTO> dtoList = new java.util.ArrayList<>();

        for (CustomDTO booking : bookingList) {
            dtoList.add(
                    new BookingDTO(
                            booking.getBookingId(),
                            booking.getCustomerId(),
                            booking.getVehicleId(),
                            booking.getDriverId(),
                            booking.getBookingDate(),
                            booking.getStartDate(),
                            booking.getEndDate(),
                            booking.getBookingStatus(),
                            booking.getAdvanceAmount(),
                            booking.getNotes(),
                            booking.getDriverAssign()
                    )
            );
        }

        return dtoList;
    }

    @Override
    public void printBookingReports() throws SQLException, JRException {

        Connection conn = DBConnection.getInstance().getConnection();

        // Step 01 - Load JRXML file
        InputStream reportObject = getClass().getResourceAsStream(
                "/lk/ijse/hcj_car_rentsystem/reports/BookingR.jrxml"
        );

        // Step 02 - Compile report
        JasperReport jr = JasperCompileManager.compileReport(reportObject);

        // Step 03 - Fill report
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        // Step 04 - View report
        JasperViewer.viewReport(jp, false);
    }

}
