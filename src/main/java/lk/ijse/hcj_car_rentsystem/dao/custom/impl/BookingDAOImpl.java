package lk.ijse.hcj_car_rentsystem.dao.custom.impl;

import lk.ijse.hcj_car_rentsystem.dao.custom.BookingDAO;
import lk.ijse.hcj_car_rentsystem.db.DBConnection;
import lk.ijse.hcj_car_rentsystem.dto.BookingDTO;
import lk.ijse.hcj_car_rentsystem.entity.Booking;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

//    public boolean save(Booking booking) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//
//            String bookingSql = "INSERT INTO bookings (customer_id, booking_date, start_date, end_date, booking_status, advance_amount, notes, driver_assign) VALUES (?,?,?,?,?,?,?,?)";
//
//            java.sql.PreparedStatement pstm = connection.prepareStatement(bookingSql, java.sql.Statement.RETURN_GENERATED_KEYS);
//
//            pstm.setInt(1, booking.getCustomerId());
//            pstm.setString(2, booking.getBookingDate());
//            pstm.setString(3, booking.getStartDate());
//            pstm.setString(4, booking.getEndDate());
//            pstm.setString(5, booking.getBookingStatus());
//            pstm.setDouble(6, booking.getAdvanceAmount());
//            pstm.setString(7, booking.getNotes());
//            pstm.setString(8, booking.getDriverAssign());
//
//            if (pstm.executeUpdate() > 0) {
//                var rs = pstm.getGeneratedKeys();
//                if (rs.next()) {
//                    int newBookingId = rs.getInt(1);
//
//                    boolean isVehicleAssigned = CrudUtil.execute(
//                            "INSERT INTO vehicle_assign_details VALUES (?,?)",
//                            newBookingId, booking.getVehicleId()
//                    );
//
//                    boolean isVehicleUpdated = CrudUtil.execute(
//                            "UPDATE vehicles SET status = 'rented' WHERE vehicle_id = ?",
//                            booking.getVehicleId()
//                    );
//
//                    if (isVehicleAssigned && isVehicleUpdated) {
//
//                        if (booking.getDriverAssign().equalsIgnoreCase("Yes")) {
//                            boolean isDriverAssigned = CrudUtil.execute(
//                                    "INSERT INTO driver_assign_details VALUES (?,?,?)",
//                                    newBookingId, booking.getVehicleId(), booking.getDriverId()
//                            );
//                            boolean isDriverUpdated = CrudUtil.execute(
//                                    "UPDATE drivers SET status = 'assigned' WHERE driver_id = ?",
//                                    booking.getDriverId()
//                            );
//
//                            if (!isDriverAssigned || !isDriverUpdated) {
//                                connection.rollback();
//                                return false;
//                            }
//                        }
//                        connection.commit();
//                        return true;
//                    }
//                }
//            }
//            connection.rollback();
//            return false;
//        } catch (Exception e) {
//            connection.rollback();
//            throw e;
//        } finally {
//            connection.setAutoCommit(true);
//        }
//    }

    public boolean update(Booking booking) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isBookingUpdated = CrudUtil.execute(
                    "UPDATE bookings SET customer_id=?, booking_date=?, start_date=?, end_date=?, booking_status=?, advance_amount=?, notes=?, driver_assign=? WHERE booking_id=?",
                    booking.getCustomerId(),
                    booking.getBookingDate(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getBookingStatus(),
                    booking.getAdvanceAmount(),
                    booking.getNotes(),
                    booking.getDriverAssign(),
                    booking.getBookingId()
            );

            if (isBookingUpdated) {

                CrudUtil.execute("DELETE FROM vehicle_assign_details WHERE booking_id=?", booking.getBookingId());

                boolean isVehicleAssigned = CrudUtil.execute("INSERT INTO vehicle_assign_details VALUES (?,?)", booking.getBookingId(), booking.getVehicleId());

                CrudUtil.execute("DELETE FROM driver_assign_details WHERE booking_id=?", booking.getBookingId());
                boolean isDriverHandled = true;
                if (booking.getDriverAssign().equalsIgnoreCase("Yes")) {
                    isDriverHandled = CrudUtil.execute("INSERT INTO driver_assign_details VALUES (?,?,?)",
                            booking.getBookingId(), booking.getVehicleId(), booking.getDriverId());
                }

                if (isVehicleAssigned && isDriverHandled) {
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

    public boolean delete(int bookingId, int vehicleId, int driverId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            CrudUtil.execute("DELETE FROM driver_assign_details WHERE booking_id=?", bookingId);
            CrudUtil.execute("DELETE FROM vehicle_assign_details WHERE booking_id=?", bookingId);

            boolean isBookingDeleted = CrudUtil.execute("DELETE FROM bookings WHERE booking_id=?", bookingId);

            if (isBookingDeleted) {
                // Making the vehicle 'available' again
                boolean isVehicleReleased = CrudUtil.execute(
                        "UPDATE vehicles SET status = 'available' WHERE vehicle_id = ?", vehicleId);

                // Making the driver 'available' if available
                boolean isDriverReleased = true;
                if (driverId > 0) {
                    isDriverReleased = CrudUtil.execute(
                            "UPDATE drivers SET status = 'available' WHERE driver_id = ?", driverId);
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

    public Booking search(int id) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM bookings WHERE booking_id=?",
                        id
                );

        if(rs.next()) {
            int bookingId = rs.getInt("booking_id");
            int customerId = rs.getInt("customer_id");
            int vehicleId = rs.getInt("vehicle_id");
            int driverId = rs.getInt("driver_id");
            String bookingDate = rs.getString("booking_date");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");
            String status = rs.getString("booking_status");
            double advance = rs.getDouble("advance_amount");
            String notes = rs.getString("notes");
            String driverAssign = rs.getString("driver_assign");

            return new Booking(bookingId,customerId,vehicleId,driverId, bookingDate, startDate, endDate, status, advance, notes, driverAssign);
        }

        return null;
    }

    public BookingDTO searchBookingByCustomerID (int cusId) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM bookings WHERE customer_id=?",
                        cusId
                );

        if(rs.next()) {
            int bookingId = rs.getInt("booking_id");
            int customerId = rs.getInt("customer_id");
            int vehicleId = rs.getInt("vehicle_id");
            int driverId = rs.getInt("driver_id");
            String bookingDate = rs.getString("booking_date");
            String startDate = rs.getString("start_date");
            String endDate = rs.getString("end_date");
            String status = rs.getString("booking_status");
            double advance = rs.getDouble("advance_amount");
            String notes = rs.getString("notes");
            String driverAssign = rs.getString("driver_assign");

            return new BookingDTO(bookingId,customerId,vehicleId,driverId, bookingDate, startDate, endDate, status, advance, notes, driverAssign);
        }

        return null;
    }

//    public List<Booking> get() throws SQLException {
//        ResultSet rs =
//                CrudUtil.execute(
//                        "SELECT b.*, v.vehicle_id, d.driver_id FROM bookings b " +
//                                "LEFT JOIN vehicle_assign_details v ON b.booking_id = v.booking_id " +
//                                "LEFT JOIN driver_assign_details d ON b.booking_id = d.booking_id " +
//                                "ORDER BY b.booking_id DESC"
//                );
//
//        List<Booking> bookingList = new ArrayList<>();
//
//        while(rs.next()) {
//            Booking booking = new Booking(
//                    rs.getInt("booking_id"),
//                    rs.getInt("customer_id"),
//                    rs.getInt("vehicle_id"),
//                    rs.getInt("driver_id"),
//                    rs.getString("booking_date"),
//                    rs.getString("start_date"),
//                    rs.getString("end_date"),
//                    rs.getString("booking_status"),
//                    rs.getDouble("advance_amount"),
//                    rs.getString("notes"),
//                    rs.getString("driver_assign")
//
//            );
//
//            bookingList.add(booking);
//        }
//
//        return bookingList;
//    }

    public void printBookingReports() throws JRException, SQLException{

        Connection conn = DBConnection.getInstance().getConnection();

        //Step 01
        InputStream reportObject = getClass().getResourceAsStream("/lk/ijse/hcj_car_rentsystem/repots/BookingR.jrxml");

        // step 02
        JasperReport jr = JasperCompileManager.compileReport(reportObject);

        // step 03
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        // step 04
        JasperViewer.viewReport(jp, false);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Booking search(Booking id) throws SQLException {
        return null;
    }

    @Override
    public List<Booking> get() throws SQLException {
        return List.of();
    }
}
