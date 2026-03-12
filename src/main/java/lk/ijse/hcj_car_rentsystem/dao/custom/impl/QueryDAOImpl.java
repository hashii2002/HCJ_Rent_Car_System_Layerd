package lk.ijse.hcj_car_rentsystem.dao.custom.impl;

import lk.ijse.hcj_car_rentsystem.dao.custom.QueryDAO;
import lk.ijse.hcj_car_rentsystem.dto.BookingDTO;
import lk.ijse.hcj_car_rentsystem.dto.CustomDTO;
import lk.ijse.hcj_car_rentsystem.entity.Booking;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<CustomDTO> get() throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT b.*, v.vehicle_id, d.driver_id FROM bookings b " +
                                "LEFT JOIN vehicle_assign_details v ON b.booking_id = v.booking_id " +
                                "LEFT JOIN driver_assign_details d ON b.booking_id = d.booking_id " +
                                "ORDER BY b.booking_id DESC"
                );

        List<CustomDTO> bookingList = new ArrayList<>();

        while(rs.next()) {
            CustomDTO booking = new CustomDTO(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("vehicle_id"),
                    rs.getInt("driver_id"),
                    rs.getString("booking_date"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getString("booking_status"),
                    rs.getDouble("advance_amount"),
                    rs.getString("notes"),
                    rs.getString("driver_assign")

            );

            bookingList.add(booking);
        }

        return bookingList;
    }

    @Override
    public List<BookingDTO> getBookings() throws SQLException {
        return List.of();
    }
}
