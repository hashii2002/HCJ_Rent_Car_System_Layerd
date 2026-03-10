package lk.ijse.hcj_car_rentsystem.dao.custom.impl;

import lk.ijse.hcj_car_rentsystem.dao.custom.RentalPaymentDAO;
import lk.ijse.hcj_car_rentsystem.db.DBConnection;
import lk.ijse.hcj_car_rentsystem.dto.RentalPaymentDTO;
import lk.ijse.hcj_car_rentsystem.entity.RentalPayment;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalPaymentDAOImpl implements RentalPaymentDAO {

    public boolean save(RentalPayment rentalPayment) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "INSERT INTO payments (booking_id, start_km , end_km, total_amount, daily_rent, amount_charged_per_1km, payment_method, paid_date, advance_amount) VALUES (?,?,?,?,?,?,?,?,?)",

                        rentalPayment.getBookingId(),
                        rentalPayment.getStartKm(),
                        rentalPayment.getEndKm(),
                        rentalPayment.getTotalAmount(),
                        rentalPayment.getDailyRent(),
                        rentalPayment.getAmountChargedPerKm(),
                        rentalPayment.getPaymentMethod(),
                        rentalPayment.getPaidDate(),
                        rentalPayment.getAdvanceAmount()
                );

        return result;
    }

    public boolean update(RentalPayment rentalPayment) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "UPDATE payments SET start_km=? , end_km=? , total_amount=? , daily_rent=?, amount_charged_per_1km=?, payment_method=? , paid_time=?, paid_date=?, advance_amount=? WHERE payment_id=?",

                        rentalPayment.getStartKm(),
                        rentalPayment.getEndKm(),
                        rentalPayment.getTotalAmount(),
                        rentalPayment.getDailyRent(),
                        rentalPayment.getAmountChargedPerKm(),
                        rentalPayment.getPaymentMethod(),
                        rentalPayment.getPaidTime(),
                        rentalPayment.getPaidDate(),
                        rentalPayment.getAdvanceAmount(),
                        rentalPayment.getPaymentId()


                );

        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result =
                CrudUtil.execute(
                        "DELETE FROM payments WHERE payment_id=?",
                        id
                );

        return result;
    }

    public RentalPayment search(int id) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM payments WHERE payment_id=?",
                        id
                );

        if(rs.next()) {
            int paymentId = rs.getInt("payment_id");
            int bookingId = rs.getInt("booking_id");
            String startKm = rs.getString("start_km");
            String endKm = rs.getString("end_km");
            String totalKm = rs.getString("total_km");
            double totalAmount = rs.getDouble("total_amount");
            double dailyRent = rs.getDouble("daily_rent");
            double amountChargedPerKm = rs.getDouble("amount_charged_per_1km");
            String paymentMethod = rs.getString("payment_method");
            String paidTime = rs.getString("paid_time");
            String paidDate = rs.getString("paid_date");
            double advanceAmount = rs.getDouble("advance_amount");

            return new RentalPayment (paymentId,bookingId,startKm ,endKm,totalKm, totalAmount, dailyRent, amountChargedPerKm, paymentMethod, paidTime, paidDate, advanceAmount );
        }

        return null;
    }

    public RentalPaymentDTO searchPaymentByBookingID (int cusId) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM payments WHERE booking_id=?",
                        cusId
                );

        if(rs.next()) {
            int paymentId = rs.getInt("payment_id");
            int bookingId = rs.getInt("booking_id");
            String startKm = rs.getString("start_km");
            String endKm = rs.getString("end_km");
            String totalKm = rs.getString("total_km");
            double totalAmount = rs.getDouble("total_amount");
            double dailyRent = rs.getDouble("daily_rent");
            double amountChargedPerKm = rs.getDouble("amount_charged_per_1km");
            String paymentMethod = rs.getString("payment_method");
            String paidTime = rs.getString("paid_time");
            String paidDate = rs.getString("paid_date");
            double advanceAmount = rs.getDouble("advance_amount");

            return new RentalPaymentDTO(paymentId,bookingId,startKm ,endKm,totalKm, totalAmount, dailyRent, amountChargedPerKm, paymentMethod, paidTime, paidDate, advanceAmount);
        }

        return null;
    }

    public List<RentalPayment> get() throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM payments ORDER BY payment_id DESC"
                );

        List<RentalPayment> paymentList = new ArrayList<>();

        while(rs.next()) {
            RentalPayment rentalPayment = new RentalPayment(
                    rs.getInt("payment_id"),
                    rs.getInt("booking_id"),
                    rs.getString("start_km"),
                    rs.getString("end_km"),
                    rs.getString("total_km"),
                    rs.getDouble("total_amount"),
                    rs.getDouble("daily_rent"),
                    rs.getDouble("amount_charged_per_1km"),
                    rs.getString("payment_method"),
                    rs.getString("paid_time"),
                    rs.getString("paid_date"),
                    rs.getDouble("advance_amount")

            );

            paymentList.add(rentalPayment);
        }

        return paymentList;
    }

    public void printPaymentInvoice(int paymentId) throws JRException, SQLException {

        Connection conn = DBConnection.getInstance().getConnection();

        // Step 01
        InputStream reportObject = getClass().getResourceAsStream("/lk/ijse/hcj_car_rentsystem/repots/invoice.jrxml");

        // Step 02
        JasperReport jr = JasperCompileManager.compileReport(reportObject); // this method thorws JRException

        // Step 03
        Map<String, Object> params = new HashMap<>();
        params.put("PAYMENT_ID", paymentId);

        JasperPrint jp = JasperFillManager.fillReport(jr, params, conn); // fillReport(japerreport, params, connection_obj)

        // Step 04
        JasperViewer.viewReport(jp, false);

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public RentalPayment search(RentalPayment id) throws SQLException {
        return null;
    }
}
